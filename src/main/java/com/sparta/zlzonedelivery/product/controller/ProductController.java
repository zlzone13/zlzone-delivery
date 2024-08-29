package com.sparta.zlzonedelivery.product.controller;

import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.global.dto.ResponseDto;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.product.service.ProductService;
import com.sparta.zlzonedelivery.product.service.dtos.ProductCreateRequestDto;
import com.sparta.zlzonedelivery.product.service.dtos.ProductReadResponseDto;
import com.sparta.zlzonedelivery.product.service.dtos.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{store_id}/products")
    public ResponseDto<Void> createProduct(@PathVariable("store_id") UUID storeId,
                                           @RequestBody ProductCreateRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        validateUser(userDetails);

        productService.createProduct(storeId, requestDto);

        return ResponseDto.ok();
    }

    @GetMapping("/{store_id}/products/{product_id}")
    public ResponseDto<ProductReadResponseDto> getProduct(@PathVariable("store_id") UUID storeId,
                                                          @PathVariable("product_id") UUID productId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.okWithData(productService.getProductByStoreId(storeId, productId));
    }

    @GetMapping("/{store_id}/products")
    public ResponseDto<Page<ProductReadResponseDto>> getAllProductByStore(@PathVariable("store_id") UUID storeId,
                                                                          @PageableDefault Pageable pageable,
                                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        validateUser(userDetails);

        return ResponseDto.okWithData(productService.getAllProductByStore(storeId, pageable));
    }

    @GetMapping("/products")
    public ResponseDto<Page<ProductReadResponseDto>> getAllProduct(@PageableDefault Pageable pageable,
                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails.getRole().getValue().equals("CUSTOMER") || userDetails.getRole().getValue().equals("OWNER")) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return ResponseDto.okWithData(productService.getAllProduct(pageable));

    }

    @PatchMapping("/{store_id}/products/{product_id}")
    public ResponseDto<Void> updateProduct(@PathVariable("store_id") UUID storeId,
                                           @PathVariable("product_id") UUID productId,
                                           @RequestBody ProductUpdateRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        validateUser(userDetails);

        productService.updateProduct(storeId, productId, requestDto);

        return ResponseDto.ok();
    }

    @DeleteMapping("/{store_id}/products/{product_id}")
    public ResponseDto<Void> deleteProduct(@PathVariable("store_id") UUID storeId,
                                           @PathVariable("product_id") UUID productId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        validateUser(userDetails);

        productService.deleteProduct(storeId, productId);
        return ResponseDto.ok();
    }

    @GetMapping("/{store_id}/products/category/")
    public ResponseDto<Page<ProductReadResponseDto>> searchByCategory(
            @PathVariable("store_id") UUID storeId,
            @RequestParam("category_name") String categoryName,
            @PageableDefault Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseDto.okWithData(productService.searchByCategory(storeId, categoryName, pageable));
    }

    private static void validateUser(UserDetailsImpl userDetails) {
        if (userDetails.getRole().getValue().equals("CUSTOMER")) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }

}
