package com.sparta.zlzonedelivery.product.service;

import com.sparta.zlzonedelivery.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

}
