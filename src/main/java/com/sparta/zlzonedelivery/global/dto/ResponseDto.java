package com.sparta.zlzonedelivery.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResponseDto<T> {

    private final int code;

    private final T data;

    private final String message;

    @Builder
    private ResponseDto(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static ResponseDto<Void> ok() {
        return ResponseDto.<Void>builder()
                .code(HttpStatus.OK.value())
                .data(null)
                .message(null)
                .build();
    }

    public static <T> ResponseDto<T> okWithData(T data) {
        return ResponseDto.<T>builder()
                .code(HttpStatus.OK.value())
                .data(data)
                .message(null)
                .build();
    }

    public static <T> ResponseDto<T> okWithDataAndMessage(String message, T data) {
        return ResponseDto.<T>builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

//    public static ResponseDto<Void> error(ErrorCode errorCode) {
//        return ResponseDto.<Void>builder()
//                .code(errorCode.getHttpStatus().value())
//                .message(errorCode.getMessage())
//                .data(null)
//                .build();
//    }

    public static <T> ResponseDto<T> errorWithMessage(HttpStatus httpStatus, String errorMessage) {
        return ResponseDto.<T>builder()
                .code(httpStatus.value())
                .message(errorMessage)
                .data(null)
                .build();
    }

}
