package com.sparta.zlzonedelivery.category.service;

import com.sparta.zlzonedelivery.category.dto.CategoryCreateRequestDto;
import com.sparta.zlzonedelivery.category.repository.CategoryRepository;
import com.sparta.zlzonedelivery.category.entity.Category;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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

}
