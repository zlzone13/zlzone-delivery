package com.sparta.zlzonedelivery.store.service;

import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.repository.StoreRepository;
import com.sparta.zlzonedelivery.store.service.dtos.StoreCreateRequestDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreReadResponseDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Optional.ofNullable;

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

    @Transactional(readOnly = true)
    public StoreReadResponseDto getStore(UUID storeId) {

        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId).orElseThrow(
                () -> new IllegalArgumentException("가게를 찾을 수 없습니다.")
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

    @Transactional(readOnly = true)
    public Page<StoreReadResponseDto> getStoreAll(int page, int size) {

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 하며, 크기는 1 이상이어야 합니다.");
        }

        Pageable pageable = PageRequest.of(page, size);

        return storeRepository.findAllByIsPublicIsTrue(pageable).map(store -> StoreReadResponseDto.builder()
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

    public void updateStore(UUID storeId, StoreUpdateRequestDto requestDto) {
        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId).orElseThrow(
                () -> new IllegalArgumentException("가게를 찾을 수 없습니다.")
        );

        ofNullable(requestDto.description()).ifPresent(store::updateDescription);
        ofNullable(requestDto.announcement()).ifPresent(store::updateAnnouncement);
        ofNullable(requestDto.telephoneNo()).ifPresent(store::updateTelephoneNo);
        ofNullable(requestDto.openCloseTime()).ifPresent(store::updateOpenCloseTime);
        ofNullable(requestDto.deliveryArea()).ifPresent(store::updateDeliveryArea);
        ofNullable(requestDto.countryInfo()).ifPresent(store::updateCountryInfo);
    }

    public void deleteStore(UUID uuid) {
        storeRepository.deleteById(uuid);
    }

}
