package com.sparta.zlzonedelivery.store.service.dtos;

import java.util.UUID;

public record StoreUpdateRequestDto(
        UUID storeId,
        String description,
        //category
        String announcement,
        String telephoneNo,
        String deliveryArea,
        String openCloseTime,
        String countryInfo
) {

}
