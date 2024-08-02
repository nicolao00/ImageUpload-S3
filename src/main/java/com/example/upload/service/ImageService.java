package com.example.upload.service;

//import com.example.upload.config.S3Config;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.upload.domain.Image;
import com.example.upload.domain.type.ErrorCode;
import com.example.upload.exception.RestApiException;
import com.example.upload.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Bag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;
    @Value("${spring.image.path}")
    private String FOLDER_PATH;


    @Transactional()
    public String uploadImage(MultipartFile file) throws IOException {
        // File Path Fetch
        String uuidImageName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = FOLDER_PATH + uuidImageName;

        // File Upload
        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
//            throw new RestApiException(ErrorCode.FILE_UPLOAD);
        }

        // Path DB Save
//        Object useObject = null;
//        switch (imageUseType) {
//            case USER -> { useObject = userRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER)); }
//            case SHOP -> { useObject = shopRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_SHOP)); }
//            case ADVERTISEMENT -> { useObject = advertisementRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_ADVERTISEMENT)); }
//        }

        // Image Object find
        Image findImage = null;
//        switch (imageUseType) {
//            case USER -> { findImage = imageRepository.findByUser((User) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
//            case SHOP -> { findImage = imageRepository.findByShop((Shop) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
//            case ADVERTISEMENT -> { findImage = imageRepository.findByAdvertisement((Advertisement) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
//        }


//        if (!findImage.getUuidName().equals("0_default_image.png")) {
//            File currentFile = new File(findImage.getPath());
//            boolean result = currentFile.delete();
//        }
//
//        findImage.updateImage(file.getOriginalFilename(), uuidImageName, filePath, file.getContentType());
        imageRepository.save(
                Image.builder()
                        .originName(file.getOriginalFilename())
                        .uuidName(uuidImageName)
                        .type(file.getContentType())
                        .build()
        );
        return uuidImageName;
    }

    @Transactional()
    public Boolean deleteImage(String uuidName) {

        // 파일 경로
        String filePath = FOLDER_PATH + uuidName;

        // 파일 삭제
        File file = new File(filePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new RestApiException(ErrorCode.NOT_FOUND);
            }
        }

        Image findImage = imageRepository.findByOriginName(uuidName).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));
        imageRepository.delete(findImage);
        return Boolean.TRUE;
    }
}

