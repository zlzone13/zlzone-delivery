package com.sparta.zlzonedelivery.store.controller.dtos;

import com.sparta.zlzonedelivery.store.service.dtos.StoreCreateRequestDto;
import com.sparta.zlzonedelivery.user.User;

public record StoreServiceCreateDto(
        StoreCreateRequestDto requestDto,
        User user

) {

}
