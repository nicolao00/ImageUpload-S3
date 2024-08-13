package com.example.upload.controller;

import com.example.upload.dto.response.ImageResponseDto;
import com.example.upload.exception.ExceptionDto;
import com.example.upload.exception.ResponseDto;
import com.example.upload.service.ImageService;
import com.example.upload.service.ImageServiceS3;
import com.sun.nio.sctp.IllegalUnbindException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final ImageServiceS3 imageServiceS3;

//    @GetMapping("/image")
//    public ResponseEntity<?> downloadImage(@RequestParam("uuid") String fileName) throws IOException {
//        byte[] imageData = imageService.downloadImage(fileName);
//
//        // 임시 체크
//        if (imageData.length == 0)
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ResponseDto.builder()
//                            .success(false)
//                            .data(null)
//                            .error(new ExceptionDto(new IllegalUnbindException())).build());
//        else
//            return ResponseEntity.status(HttpStatus.OK)
//                    .contentType(MediaType.valueOf("image/png"))
//                    .body(imageData);
//    }

     //로컬 저장소에 사진 저장하는 로직
    @PostMapping("/user")
    public ResponseDto<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("user") String userName) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("uuid_name", imageService.uploadImage(file, userName));
        return new ResponseDto(map);
    }

    @GetMapping("/user/{userName}")
    public ResponseDto<?> getImages(@PathVariable String userName) throws IOException {
        Map<String, List<ImageResponseDto>> map = new HashMap<>();
        map.put("path", imageService.getImageList(userName));
        return new ResponseDto<>(map);
    }

    @DeleteMapping("/user/{originName}")
    public ResponseDto<Boolean> deleteImage(@PathVariable String originName) throws IOException {
        return new ResponseDto<Boolean>(imageService.deleteImage(originName));
    }

//    // S3에 사진 저장하는 로직
//    @PostMapping("/user")
//    public ResponseDto<?> uploadImage(@RequestParam("image") List<MultipartFile> fileList, @RequestParam("user") String userName) throws IOException {
//        Map<String, List<String>> map = new HashMap<>();
//        map.put("uuid_name", imageServiceS3.uploadImage(fileList, userName));
//        return new ResponseDto(map);
//    }
//
//    @GetMapping("/user/{userName}")
//    public ResponseDto<?> getImages(@PathVariable String userName) throws IOException {
//        Map<String, List<ImageResponseDto>> map = new HashMap<>();
//        map.put("path", imageServiceS3.getImageList(userName));
//        return new ResponseDto<>(map);
//    }
//
//    @DeleteMapping("/user/{uuidName}")
//    public ResponseDto<Boolean> deleteImage(@PathVariable String uuidName) throws IOException {
//        return new ResponseDto<Boolean>(imageServiceS3.deleteImage(uuidName));
//    }
}
