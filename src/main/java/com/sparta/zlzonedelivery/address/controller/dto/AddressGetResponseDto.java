package com.sparta.zlzonedelivery.address.controller.dto;

import com.sparta.zlzonedelivery.address.Address;

import java.util.UUID;

public record AddressGetResponseDto(
        UUID id,
        String zipcode,
        String address,
        String detailAddress
) {

    public AddressGetResponseDto(Address address) {
        this(
                address.getId(),
                address.getZipcode(),
                address.getAddress(),
                address.getDetailAddress()
        );
    }

}
