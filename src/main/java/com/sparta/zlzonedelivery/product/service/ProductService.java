package com.sparta.zlzonedelivery.product.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.product.entity.Product;
import com.sparta.zlzonedelivery.product.repository.ProductRepository;
import com.sparta.zlzonedelivery.product.service.dtos.ProductCreateRequestDto;
import com.sparta.zlzonedelivery.product.service.dtos.ProductReadResponseDto;
import com.sparta.zlzonedelivery.product.service.dtos.ProductUpdateRequestDto;
import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.repository.StoreRepository;
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

    private final StoreRepository storeRepository;

    @Transactional
    public void createProduct(ProductCreateRequestDto requestDto) {

        Store store = storeRepository.findByIdAndIsPublicIsTrue(requestDto.storeId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        Product product = Product.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .price(requestDto.price())
                .store(store)
                .build();

        productRepository.save(product);
    }

    public ProductReadResponseDto getProduct(UUID productId) {

        Product product = productRepository.findByIdAndIsPublicIsTrue(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        return ProductReadResponseDto.builder()
                .productId(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .name(product.getName())
                .build();
    }

    public Page<ProductReadResponseDto> getProductAll(Pageable pageable) {

        return productRepository.findAllByIsPublicIsTrue(pageable)
                .map(product -> ProductReadResponseDto.builder()
                        .productId(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build());
    }

    @Transactional
    public void updateProduct(UUID productId, ProductUpdateRequestDto requestDto) {

        Product product = productRepository.findByIdAndIsPublicIsTrue(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        product.updateProduct(requestDto.name(), requestDto.description(),
                requestDto.price(), requestDto.isShown());

    }

    @Transactional
    public void deleteProduct(UUID productId) {

        productRepository.deleteById(productId);
    }

}
