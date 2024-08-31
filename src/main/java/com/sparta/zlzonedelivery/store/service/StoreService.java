package com.sparta.zlzonedelivery.store.service;

import com.sparta.zlzonedelivery.category.entity.Category;
import com.sparta.zlzonedelivery.category.service.CategoryService;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.relationship.StoreCategory;
import com.sparta.zlzonedelivery.relationship.service.StoreCategoryService;
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

    private final StoreCategoryService storeCategoryService;

    @Transactional
    public void createStore(StoreServiceCreateDto serviceCreateDto) {

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

}
