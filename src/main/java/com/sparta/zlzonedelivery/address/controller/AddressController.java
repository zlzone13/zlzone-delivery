package com.sparta.zlzonedelivery.address.controller;

import com.sparta.zlzonedelivery.address.Address;
import com.sparta.zlzonedelivery.address.controller.dto.AddressCreateRequestDto;
import com.sparta.zlzonedelivery.address.controller.dto.AddressGetResponseDto;
import com.sparta.zlzonedelivery.address.service.AddressService;
import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public void createAddress(@RequestBody Address address, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        addressService.createAddress(address, userDetails.getUser());
    }

    @PatchMapping("/{addressId}")
    public void updateAddress(@RequestBody AddressCreateRequestDto request,
                              @PathVariable UUID addressId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Address address = request.toEntity();
        addressService.updateAddress(address, addressId, userDetails.getUser());
    }

    @GetMapping
    public List<AddressGetResponseDto> getAddress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Address> addresses = addressService.getAddress(userDetails.getUser());
        return addresses.stream()
                .map(AddressGetResponseDto::new)
                .toList();
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
    }

}
