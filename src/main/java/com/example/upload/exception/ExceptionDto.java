package com.example.upload.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionDto {
    private final String code;
    private final String message;

    public ExceptionDto(ErroCode erroCode) {
        this.code = erroCode.getCode();
        this.message = erroCode.getMessage();
    }

    public ExceptionDto(Exception e) {
        this.code = "500";
        this.message = e.getMessage();
    }
}
