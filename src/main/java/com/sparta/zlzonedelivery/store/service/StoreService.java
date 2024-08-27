package com.sparta.zlzonedelivery.store.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.repository.StoreRepository;
import com.sparta.zlzonedelivery.store.service.dtos.StoreCreateRequestDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreReadResponseDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    //TODO: JWT 설정 시 User 추가
    @Transactional
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
                () -> new IllegalArgumentException("가게를 찾을 수 없습니다.")
        );

        return StoreReadResponseDto.builder()
                .storeId(store.getId())
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

    public Page<StoreReadResponseDto> getStoreAll(Pageable pageable) {

        return storeRepository.findAllByIsPublicIsTrue(pageable).map(store -> StoreReadResponseDto.builder()
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .announcement(store.getAnnouncement())
                .description(store.getDescription())
                .bNo(store.getBNo())
                .telephoneNo(store.getTelephoneNo())
                .deliveryArea(store.getDeliveryArea())
                .openCloseTime(store.getOpenCloseTime())
                .countryInfo(store.getCountryInfo())
                .build());
    }

    @Transactional
    public void updateStore(UUID storeId, StoreUpdateRequestDto requestDto) {
        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId).orElseThrow(
                () -> new IllegalArgumentException("가게를 찾을 수 없습니다.")
        );

        store.updateStore(
                requestDto.description(),
                requestDto.announcement(),
                requestDto.telephoneNo(),
                requestDto.deliveryArea(),
                requestDto.openCloseTime(),
                requestDto.countryInfo()
        );
    }

    @Transactional
    public void deleteStore(UUID storeId) {

        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        storeRepository.deleteById(store.getId());
    }

}
