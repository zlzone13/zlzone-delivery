package com.sparta.zlzonedelivery.address.service;

import com.sparta.zlzonedelivery.address.Address;
import com.sparta.zlzonedelivery.address.repository.AddressRepository;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public void createAddress(Address address, User user) {
        address.allocateUser(user);
        addressRepository.save(address);
    }

    @Transactional
    public void updateAddress(Address request, UUID addressId, User user) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!address.getUser().equals(user)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        address.update(request);
        addressRepository.save(address);
    }

    public List<Address> getAddress(User user) {
        return addressRepository.findAllByUser(user);
    }

    @Transactional
    public void deleteAddress(UUID addressId) {
        addressRepository.deleteById(addressId);
    }

}
