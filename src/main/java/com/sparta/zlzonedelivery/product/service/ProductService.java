package com.sparta.zlzonedelivery.product.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.product.entity.Product;
import com.sparta.zlzonedelivery.product.repository.ProductRepository;
import com.sparta.zlzonedelivery.product.service.dtos.ProductCreateRequestDto;
import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
