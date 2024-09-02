package com.sparta.zlzonedelivery.address.controller.dto;

import com.sparta.zlzonedelivery.address.Address;

public record AddressCreateRequestDto(
        String zipcode,
        String address,
        String detailAddress
) {

    public Address toEntity() {
        return new Address(zipcode, address, detailAddress);
    }

}
