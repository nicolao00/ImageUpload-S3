package com.example.upload.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "originName", nullable = false)
    private String originName;

    @Column(name = "uuidName", nullable = false)
    private String uuidName;

    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "path", nullable = false)
    private String url;
    @Column(name = "upload_date")
    private Timestamp uploadDate;

    @Builder
    public Image(Long id, String owner, String originName, String uuidName, String type, String url) {
        this.id = id;
        this.owner = owner;
        this.originName = originName;
        this.uuidName = uuidName;
        this.type = type;
        this.url = url;
        this.uploadDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
