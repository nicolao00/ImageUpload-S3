package com.example.upload.service;

import com.example.upload.config.S3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final S3Config s3Config;
    @Value("${aws.s3.bucket}")
    private String FOLDER_PATH;

    public String uploadImage(MultipartFile file) throws IOException {
        // File Path Fetch
        String uuidImageName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = FOLDER_PATH + uuidImageName;

        // File Upload
        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            throw new RestApiException(ErrorCode.FILE_UPLOAD);
        }

        // Path DB Save
        Object useObject = null;
        switch (imageUseType) {
            case USER -> { useObject = userRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER)); }
            case SHOP -> { useObject = shopRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_SHOP)); }
            case ADVERTISEMENT -> { useObject = advertisementRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_ADVERTISEMENT)); }
        }

        // Image Object find
        Image findImage = null;
        switch (imageUseType) {
            case USER -> { findImage = imageRepository.findByUser((User) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
            case SHOP -> { findImage = imageRepository.findByShop((Shop) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
            case ADVERTISEMENT -> { findImage = imageRepository.findByAdvertisement((Advertisement) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
        }

        if (!findImage.getUuidName().equals("0_default_image.png")) {
            File currentFile = new File(findImage.getPath());
            boolean result = currentFile.delete();
        }

        findImage.updateImage(file.getOriginalFilename(), uuidImageName, filePath, file.getContentType());

        return uuidImageName;
    }
}
