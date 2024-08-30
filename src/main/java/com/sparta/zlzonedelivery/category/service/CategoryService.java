package com.sparta.zlzonedelivery.category.service;

import com.sparta.zlzonedelivery.category.entity.Category;
import com.sparta.zlzonedelivery.category.repository.CategoryRepository;
import com.sparta.zlzonedelivery.category.service.dto.CategoryCreateRequestDto;
import com.sparta.zlzonedelivery.category.service.dto.CategoryListResponseDto;
import com.sparta.zlzonedelivery.category.service.dto.CategorySearchResponseDto;
import com.sparta.zlzonedelivery.category.service.dto.CategorySingleResponseDto;
import com.sparta.zlzonedelivery.category.service.dto.CategoryUpdateRequestDto;
import com.sparta.zlzonedelivery.category.service.dto.StoreListByCategoryResponseDto;
import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.relationship.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
        String categoryName = categoryCreateRequestDto.categoryName();
        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new CustomException(ErrorCode.DUPLICATED_CATEGORY);
        }

        Category category = new Category(categoryName);

        categoryRepository.save(category);
    }

    public Page<CategoryListResponseDto> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        if (categories.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        return categories
                .map(category -> new CategoryListResponseDto(category.getId(), category.getCategoryName()));
    }


    public CategorySingleResponseDto getSingleCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        return CategorySingleResponseDto.fromCategory(category);
    }

    public Page<StoreListByCategoryResponseDto> getStoresByCategory(UUID categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        List<StoreListByCategoryResponseDto> stores = category.getStoreCategoryList().stream()
                .map(StoreCategory::getStore)
                .map(store -> new StoreListByCategoryResponseDto(store.getId(), store.getStoreName()))
                .toList();

        if (stores.isEmpty()) {
            throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        }

        return new PageImpl<>(stores, pageable, stores.size());
    }

    @Transactional
    public void updateCategory(UUID categoryId, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        String categoryName = categoryUpdateRequestDto.categoryName();

        if (category.getCategoryName().equals(categoryName)
                || categoryRepository.existsByCategoryName(categoryName)) {
            throw new CustomException(ErrorCode.DUPLICATED_CATEGORY);
        }

        Category newCategory = category.updateCategory(categoryName);

        categoryRepository.save(newCategory);
    }

    @Transactional
    public void deleteCategory(UUID categoryId, UserDetailsImpl userDetails) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        String username = userDetails.getUsername();

        category.setDeletedBy(username);

        categoryRepository.save(category);
    }

    @Transactional
    public Page<CategorySearchResponseDto> searchCategory(String keyword, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByCategoryNameContaining(keyword, pageable);

        if (categories.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        return categories.map(category -> new CategorySearchResponseDto(category.getCategoryName()));
    }

}
