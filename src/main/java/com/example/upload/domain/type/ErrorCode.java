package com.example.upload.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND("404", HttpStatus.NOT_FOUND, "Not Exist"),
    NOT_END_POINT("400", HttpStatus.BAD_REQUEST, "Not Exist End Point Error"),
    SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
