package com.sparta.zlzonedelivery.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException {

    private String errorMessage;

    private int statusCode;

}
