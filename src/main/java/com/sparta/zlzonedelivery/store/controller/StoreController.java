package com.sparta.zlzonedelivery.store.controller;


import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.global.dto.ResponseDto;
import com.sparta.zlzonedelivery.store.controller.dtos.StoreServiceCreateDto;
import com.sparta.zlzonedelivery.store.service.StoreService;
import com.sparta.zlzonedelivery.store.service.dtos.StoreCreateRequestDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreReadResponseDto;
import com.sparta.zlzonedelivery.store.service.dtos.StoreUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @Secured({"OWNER", "MASTER", "MANAGER"})
    public ResponseDto<Void> createStore(@RequestBody StoreCreateRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        StoreServiceCreateDto serviceCreateDto = new StoreServiceCreateDto(requestDto, userDetails.getUser());

        storeService.createStore(serviceCreateDto);
        return ResponseDto.ok();
    }

    @GetMapping("/{store_id}")
    public ResponseDto<StoreReadResponseDto> getStore(@PathVariable("store_id") UUID storeId) {
        return ResponseDto.okWithData(storeService.getStore(storeId));
    }

    @GetMapping
    @Secured({"MASTER", "MANAGER"})
    public ResponseDto<Page<StoreReadResponseDto>> getAllStore(@PageableDefault Pageable pageable) {
        return ResponseDto.okWithData(storeService.getStoreAll(pageable));
    }


    @PatchMapping("/{store_id}")
    @Secured({"OWNER", "MASTER", "MANAGER"})
    public ResponseDto<Void> updateStore(@PathVariable("store_id") UUID storeId,
                                         @RequestBody StoreUpdateRequestDto requestDto) {
        storeService.updateStore(storeId, requestDto);
        return ResponseDto.ok();
    }

    @DeleteMapping("/{store_id}")
    @Secured({"MASTER", "MANAGER"})
    public ResponseDto<Void> deleteStore(@PathVariable("store_id") UUID storeId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.deleteStore(storeId, userDetails.getUser());
        return ResponseDto.ok();
    }


}
