package com.sparta.zlzonedelivery.category.service.dto;

import java.util.UUID;

public record StoreListByCategoryResponseDto(
        UUID storeId,
        String storeName
) {

}
