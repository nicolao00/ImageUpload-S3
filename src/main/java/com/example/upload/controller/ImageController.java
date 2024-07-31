package com.example.upload.controller;

import com.example.upload.exception.ExceptionDto;
import com.example.upload.exception.ResponseDto;
import com.example.upload.service.ImageService;
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
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
        @RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

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

    @PostMapping("/user")
    public ResponseDto<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("uuid_name", imageService.uploadImage(file));
        return new ResponseDto(map);
    }

    @DeleteMapping("/user/{originName}")
    public ResponseDto<Boolean> deleteImage(@PathVariable String originName) throws IOException {
        return new ResponseDto<Boolean>(imageService.deleteImage(originName));
    }
}
