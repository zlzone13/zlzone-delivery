package com.sparta.zlzonedelivery.global.error;

import com.sparta.zlzonedelivery.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RestApiException> duplicatedCategoryException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        RestApiException restApiException = new RestApiException(errorCode.getMessage(), errorCode.getStatus());
        return new ResponseEntity<>(restApiException, HttpStatus.valueOf(errorCode.getStatus()));
    }

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ResponseDto<Void>> applicationException(CustomException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//
//        return ResponseEntity.status(errorCode.getStatus())
//                .body(ResponseDto.error(errorCode));
//    }

}
