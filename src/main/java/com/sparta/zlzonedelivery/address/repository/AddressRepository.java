package com.sparta.zlzonedelivery.address.repository;

import com.sparta.zlzonedelivery.address.Address;
import com.sparta.zlzonedelivery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findAllByUser(User user);

}
