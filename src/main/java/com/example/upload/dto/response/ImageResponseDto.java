package com.example.upload.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
public class ImageResponseDto {
    private Long id;
    private String owner;
    private String originName;
    private String uuidName;
    private String type;
    private String url;
    private Timestamp uploadDate;

    @Builder
    public ImageResponseDto(Long id, String owner, String originName, String uuidName, String type, String url, Timestamp uploadDate) {
        this.id = id;
        this.owner = owner;
        this.originName = originName;
        this.uuidName = uuidName;
        this.type = type;
        this.url = url;
        this.uploadDate = uploadDate;
    }
}
