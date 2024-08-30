package com.sparta.zlzonedelivery.product.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.product.entity.Product;
import com.sparta.zlzonedelivery.product.entity.ProductCategory;
import com.sparta.zlzonedelivery.product.repository.ProductCategoryRepository;
import com.sparta.zlzonedelivery.product.repository.ProductRepository;
import com.sparta.zlzonedelivery.product.service.dtos.ProductCreateRequestDto;
import com.sparta.zlzonedelivery.product.service.dtos.ProductReadResponseDto;
import com.sparta.zlzonedelivery.product.service.dtos.ProductUpdateRequestDto;
import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final StoreService storeService;

    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    public void createProduct(UUID storeId, ProductCreateRequestDto requestDto) {

        Store store = storeService.findStoreById(storeId);

        ProductCategory productCategory = productCategoryRepository.findByProductCategoryNameAndIsPublicIsTrue(requestDto.productCategory())
                .orElseGet(() -> productCategoryRepository.save(new ProductCategory(requestDto.productCategory())));

        Product product = Product.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .price(requestDto.price())
                .store(store)
                .productCategory(productCategory)
                .build();

        productRepository.save(product);
    }

    public ProductReadResponseDto getProductByStoreId(UUID storeId, UUID productId) {

        Product product = productRepository.findByIdAndIsPublicIsTrue(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        if (!storeId.equals(product.getStore().getId())) {
            throw new CustomException(ErrorCode.INVALID_STORE);
        }

        return ProductReadResponseDto.fromEntity(product);
    }

    public Page<ProductReadResponseDto> getAllProductByStore(UUID storeId, Pageable pageable) {

        Store store = storeService.findStoreById(storeId);

        return productRepository.findAllByStoreAndIsPublicIsTrueOrderByPrice(store, pageable)
                .map(ProductReadResponseDto::fromEntity);
    }

    public Page<ProductReadResponseDto> getAllProduct(Pageable pageable) {

        return productRepository.findAllByIsPublicIsTrueOrderByPrice(pageable)
                .map(ProductReadResponseDto::fromEntity);

    }

    @Transactional
    public void updateProduct(UUID storeId, UUID productId, ProductUpdateRequestDto requestDto) {

        Product product = productRepository.findByIdAndIsPublicIsTrue(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        if (!storeId.equals(product.getStore().getId())) {
            throw new CustomException(ErrorCode.INVALID_STORE);
        }

        product.updateProduct(requestDto.name(), requestDto.description(),
                requestDto.price(), requestDto.isShown());

    }

    @Transactional
    public void deleteProduct(UUID storeId, UUID productId) {

        Product product = productRepository.findByIdAndIsPublicIsTrue(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        if (!storeId.equals(product.getStore().getId())) {
            throw new CustomException(ErrorCode.INVALID_STORE);
        }

        productRepository.deleteById(product.getId());
    }

    public Page<ProductReadResponseDto> searchByCategory(UUID storeId, String categoryName, Pageable pageable) {

        storeService.existStoreById(storeId);

        ProductCategory productCategory = productCategoryRepository.findByProductCategoryNameAndIsPublicIsTrue(categoryName)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

        return productRepository.searchByProductCategoryAndIsPublicIsTrueOrderByPrice(productCategory, pageable)
                .map(ProductReadResponseDto::fromEntity);
    }

}
