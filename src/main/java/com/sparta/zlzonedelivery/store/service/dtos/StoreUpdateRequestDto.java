package com.sparta.zlzonedelivery.store.service.dtos;

public record StoreUpdateRequestDto(
        String description,
        //category
        String announcement,
        String telephoneNo,
        String deliveryArea,
        String openCloseTime,
        String countryInfo
) {

}
