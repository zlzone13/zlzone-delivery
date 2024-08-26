package com.sparta.zlzonedelivery.store.service.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StoreReadResponseDto(
        // TODO: location, category 테이블 추가
        UUID storeId,
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
