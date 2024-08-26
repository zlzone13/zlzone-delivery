package com.sparta.zlzonedelivery.store.service.dtos;

public record StoreCreateRequestDto(
        //category
        String storeName,
        String description,
        String announcement,
        String bNo,
        String telephoneNo,
        String deliveryArea,
        String openCloseTime,
        String countryInfo
) {

}
