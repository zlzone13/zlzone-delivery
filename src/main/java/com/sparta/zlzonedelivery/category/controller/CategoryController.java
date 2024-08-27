package com.sparta.zlzonedelivery.category.controller;

import com.sparta.zlzonedelivery.category.dto.CategoryCreateRequestDto;
import com.sparta.zlzonedelivery.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
