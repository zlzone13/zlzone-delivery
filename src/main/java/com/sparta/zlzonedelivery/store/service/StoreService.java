package com.sparta.zlzonedelivery.store.service;

import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.repository.StoreRepository;
import com.sparta.zlzonedelivery.store.service.dtos.StoreCreateRequestDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;

    //TODO: JWT 설정 시 User 추가
    public void createStore(StoreCreateRequestDto requestDto) {

        Store store = Store.builder()
                .storeName(requestDto.storeName())
                .description(requestDto.description())
                .announcement(requestDto.announcement())
                .bNo(requestDto.bNo())
                .telephoneNo(requestDto.telephoneNo())
                .deliveryArea(requestDto.deliveryArea())
                .openCloseTime(requestDto.openCloseTime())
                .countryInfo(requestDto.countryInfo())
                .build();

        storeRepository.save(store);
    }

    public StoreReadResponseDto getStore(UUID storeId) {

        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId).orElseThrow(
                ()-> new IllegalArgumentException("가게를 찾을 수 없습니다.")
        );

        return StoreReadResponseDto.builder()
                .storeName(store.getStoreName())
                .announcement(store.getAnnouncement())
                .description(store.getDescription())
                .bNo(store.getBNo())
                .telephoneNo(store.getTelephoneNo())
                .deliveryArea(store.getDeliveryArea())
                .openCloseTime(store.getOpenCloseTime())
                .countryInfo(store.getCountryInfo())
                .build();
    }



}
