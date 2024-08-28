package com.sparta.zlzonedelivery.category.controller;

import com.sparta.zlzonedelivery.category.service.CategoryService;
import com.sparta.zlzonedelivery.category.service.dto.CategoryCreateRequestDto;
import com.sparta.zlzonedelivery.category.service.dto.CategoryListResponseDto;
import com.sparta.zlzonedelivery.category.service.dto.CategorySearchResponseDto;
import com.sparta.zlzonedelivery.category.service.dto.CategorySingleResponseDto;
import com.sparta.zlzonedelivery.category.service.dto.CategoryUpdateRequestDto;
import com.sparta.zlzonedelivery.category.service.dto.StoreListByCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryCreateRequestDto categoryCreateRequestDto/*,
                               @AuthenticationPrincipal UserDetailsImpl userDetails*/) {
        categoryService.createCategory(categoryCreateRequestDto/*, userDetails*/);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryListResponseDto>> getAllCategories(@PageableDefault Pageable pageable) {
        Page<CategoryListResponseDto> categories = categoryService.getAllCategories(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategorySingleResponseDto> getSingleCategory(@PathVariable UUID categoryId) {
        CategorySingleResponseDto categoryDetail = categoryService.getSingleCategory(categoryId);
        return new ResponseEntity<>(categoryDetail, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/stores")
    public ResponseEntity<Page<StoreListByCategoryResponseDto>> getStoresByCategory(
            @PathVariable UUID categoryId,
            @PageableDefault Pageable pageable) {
        Page<StoreListByCategoryResponseDto> stores = categoryService.getStoresByCategory(categoryId, pageable);
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        categoryService.updateCategory(categoryId, categoryUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategorySearchResponseDto>> searchCategory(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable) {
        Page<CategorySearchResponseDto> categories = categoryService.searchCategory(keyword, pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
