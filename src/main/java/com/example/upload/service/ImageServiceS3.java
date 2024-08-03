package com.example.upload.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.upload.domain.Image;
import com.example.upload.domain.type.ErrorCode;
import com.example.upload.dto.response.ImageResponseDto;
import com.example.upload.exception.RestApiException;
import com.example.upload.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("image")
public class ImageServiceS3 {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.cloudfront.domain}")
    private String cloudfront;
    private final AmazonS3Client amazonS3;
    private final ImageRepository imageRepository;

    /* 1. 파일 업로드
    putObject(버킷명, 파일명, 파일데이터, 메타데이터)로 S3에 객체 등록 이걸 쓰는 이유: https://galid1.tistory.com/591
    */
    @Transactional()
    public List<String> uploadImage(List<MultipartFile> fileList, String user) throws IOException {
        List<Image> imageList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();

        for (MultipartFile file : fileList) {
            String uuidImageName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 메타데이터 생성
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(file.getInputStream().available());
            // putObject(버킷명, 파일명, 파일데이터, 메타데이터)로 S3에 객체 등록
            amazonS3.putObject(bucket, uuidImageName, file.getInputStream(), objMeta);
            // 등록된 객체의 url
            String url = "https://" + cloudfront + "/image/" + uuidImageName;

            // DB에 저장
            imageList.add(
                    Image.builder()
                            .owner(user)
                            .originName(file.getOriginalFilename())
                            .uuidName(uuidImageName)
                            .type(file.getContentType())
                            .url(url)
                            .build()
            );

            urlList.add(url);
        }
        imageRepository.saveAll(imageList);
        return urlList;
    }

    @Transactional
    public List<ImageResponseDto> getImageList(String user) throws IOException {
        List<Image> userImages = imageRepository.findAllByOwner(user).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));
        List<ImageResponseDto> imageResponseDtos = new ArrayList<>();

        for (Image image : userImages) {
            imageResponseDtos.add(
                    ImageResponseDto.builder()
                            .id(image.getId())
                            .owner(image.getOwner())
                            .originName(image.getOriginName())
                            .uuidName(image.getUuidName())
                            .type(image.getType())
                            .url(image.getUrl())
                            .uploadDate(image.getUploadDate())
                            .build()
            );
        }
        return imageResponseDtos;
    }

    /* 3. 파일 삭제 */
    @Transactional()
    public Boolean deleteImage (String uuidName) throws IOException {
        Image findImage = imageRepository.findByUuidName(uuidName).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));
        imageRepository.delete(findImage);
        try {
            // deleteObject(버킷명, 키값)으로 객체 삭제
            amazonS3.deleteObject(bucket, uuidName);
        } catch (AmazonServiceException e) {
            log.error(e.toString());
        }
        return Boolean.TRUE;
    }
}
