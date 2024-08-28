package com.sparta.zlzonedelivery.store.service.dtos;

import com.sparta.zlzonedelivery.store.entity.Store;
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

    public static StoreReadResponseDto fromEntity(Store store) {
        return StoreReadResponseDto.builder()
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .announcement(store.getAnnouncement())
                .bNo(store.getBNo())
                .telephoneNo(store.getTelephoneNo())
                .deliveryArea(store.getDeliveryArea())
                .openCloseTime(store.getOpenCloseTime())
                .countryInfo(store.getCountryInfo())
                .build();
    }

}
