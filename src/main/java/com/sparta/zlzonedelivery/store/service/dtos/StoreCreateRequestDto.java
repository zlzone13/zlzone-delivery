package com.sparta.zlzonedelivery.store.service.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record StoreCreateRequestDto(
        //category
        @NotBlank
        String storeName,
        String description,
        String announcement,
        @NotBlank
        String bNo,
        String telephoneNo,
        String deliveryArea,
        String openCloseTime,
        @NotBlank
        String countryInfo,
        List<String> categories
) {

}
