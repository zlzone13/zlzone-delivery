package com.sparta.zlzonedelivery.store.service;

import com.sparta.zlzonedelivery.category.entity.Category;
import com.sparta.zlzonedelivery.category.service.CategoryService;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.location.entity.Location;
import com.sparta.zlzonedelivery.location.service.LocationService;
import com.sparta.zlzonedelivery.relationship.StoreCategory;
import com.sparta.zlzonedelivery.store.controller.dtos.StoreServiceCreateDto;
import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.repository.StoreRepository;
import com.sparta.zlzonedelivery.store.service.dtos.StoreReadResponseDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreUpdateRequestDto;
import com.sparta.zlzonedelivery.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    private final CategoryService categoryService;

    private final LocationService locationService;

    @Transactional
    public void createStore(StoreServiceCreateDto serviceCreateDto) {

        Location location = Location.builder()
                .ctprvnCd(serviceCreateDto.requestDto().ctprvnCd())
                .ctpKorNm(serviceCreateDto.requestDto().ctpKorNm())
                .sigCd(serviceCreateDto.requestDto().sigCd())
                .sigKorNm(serviceCreateDto.requestDto().sigKorNm())
                .emdCd(serviceCreateDto.requestDto().emdCd())
                .emdKorNm(serviceCreateDto.requestDto().emdKorNm())
                .liCd(serviceCreateDto.requestDto().liCd())
                .liKorNm(serviceCreateDto.requestDto().liKorNm())
                .build();

        //위치 주소 매핑(같은 컬럼이 존재할 수도 있으니까)
        Location savedLocation = locationService.findByLocationName(location);

        //카테고리 매핑
        List<Category> categories = categoryService.getByCategoryName(serviceCreateDto.requestDto().categories());

        Store store = Store.builder()
                .storeName(serviceCreateDto.requestDto().storeName())
                .description(serviceCreateDto.requestDto().description())
                .announcement(serviceCreateDto.requestDto().announcement())
                .bNo(serviceCreateDto.requestDto().bNo())
                .telephoneNo(serviceCreateDto.requestDto().telephoneNo())
                .deliveryArea(serviceCreateDto.requestDto().deliveryArea())
                .openCloseTime(serviceCreateDto.requestDto().openCloseTime())
                .countryInfo(serviceCreateDto.requestDto().countryInfo())
                .user(serviceCreateDto.user())
                .location(savedLocation)
                .build();

        for (Category category : categories) {
            StoreCategory storeCategory = new StoreCategory(store, category);
            //store에도 넣어준다.
            store.getStoreCategoryList().add(storeCategory);
        }

        storeRepository.save(store);
    }

    public StoreReadResponseDto getStore(UUID storeId) {

        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId).orElseThrow(
                () -> new CustomException(ErrorCode.STORE_NOT_FOUND)
        );

        return StoreReadResponseDto.fromEntity(store);
    }

    public Page<StoreReadResponseDto> getStoreAll(Pageable pageable) {

        return storeRepository.findAllByIsPublicIsTrue(pageable)
                .map(StoreReadResponseDto::fromEntity);
    }

    @Transactional
    public void updateStore(UUID storeId, StoreUpdateRequestDto requestDto) {
        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId).orElseThrow(
                () -> new CustomException(ErrorCode.STORE_NOT_FOUND)
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
    public void deleteStore(UUID storeId, User user) {

        Store store = storeRepository.findByIdAndIsPublicIsTrue(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        store.setDeletedBy(user.getUsername());

        storeRepository.deleteById(store.getId());
    }

    public Store findStoreById(UUID storeId) {
        return storeRepository.findByIdAndIsPublicIsTrue(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }

    public void existStoreById(UUID storeId) {
        if (!storeRepository.existsByIdAndIsPublicIsTrue(storeId)) {
            throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        }
    }

    public Page<StoreReadResponseDto> getStoreByLocationCtpName(Pageable pageable, String ctpName) {
        return storeRepository.findByLocation_CtpKorNmContaining(pageable, ctpName)
                .map(StoreReadResponseDto::fromEntity);
    }

}
