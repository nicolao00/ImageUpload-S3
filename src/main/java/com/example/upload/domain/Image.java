package com.example.upload.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "originName", nullable = false)
    private String originName;

    @Column(name = "uuidName", nullable = false)
    private String uuidName;

    @Column(name = "type", nullable = false)
    private String type;

    @Builder
    public Image(Long id, String originName, String uuidName, String type) {
        this.id = id;
        this.originName = originName;
        this.uuidName = uuidName;
        this.type = type;
    }
}
