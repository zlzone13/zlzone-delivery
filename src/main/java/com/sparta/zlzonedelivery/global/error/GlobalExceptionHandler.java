package com.sparta.zlzonedelivery.global.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RestApiException> duplicatedCategoryException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        RestApiException restApiException = new RestApiException(errorCode.getMessage(), errorCode.getStatus());
        return new ResponseEntity<>(restApiException, HttpStatusCode.valueOf(errorCode.getStatus()));
    }

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ResponseDto<Void>> applicationException(CustomException ex) {
//        ErrorCode errorCode = ex.getErrorCode();
//
//        return ResponseEntity.status(errorCode.getStatus())
//                .body(ResponseDto.error(errorCode));
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
