package com.example.upload.exception;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private final Boolean success;
    private final T data;
    private ExceptionDto error;

    public ResponseDto(@Nullable T data) {
        this.success = true;
        this.data = data;
    }
}
