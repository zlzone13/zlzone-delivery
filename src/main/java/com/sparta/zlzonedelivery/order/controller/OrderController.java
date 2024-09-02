package com.sparta.zlzonedelivery.order.controller;

import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.order.service.OrderService;
import com.sparta.zlzonedelivery.order.service.dto.OrderCreateOfflineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderCreateOnlineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderDetailResponseDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderSearchResponseDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderUpdateOfflineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderUpdateOnlineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrdersByCustomerResponseDto;
import com.sparta.zlzonedelivery.order.service.dto.OrdersByOwnerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/stores/{storeId}/orders/online")
    public ResponseEntity<Void> createOrderOnline(@PathVariable UUID storeId,
                                                  @RequestBody OrderCreateOnlineRequestDto requestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.createOrderOnline(storeId, requestDto, userDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/stores/{storeId}/orders/offline")
    public ResponseEntity<Void> createOrderOffline(@PathVariable UUID storeId,
                                                   @RequestBody OrderCreateOfflineRequestDto requestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.createOrderOffline(storeId, requestDto, userDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    @Secured({"CUSTOMER", "MASTER", "MANAGER"})
    public ResponseEntity<Page<OrdersByCustomerResponseDto>> getOrdersByCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                 @PageableDefault Pageable pageable) {
        Page<OrdersByCustomerResponseDto> orders = orderService.getOrdersByCustomer(userDetails, pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/stores/{storeId}/orders")
    @Secured({"OWNER", "MASTER", "MANAGER"})
    public ResponseEntity<Page<OrdersByOwnerResponseDto>> getOrdersByOwner(@PathVariable UUID storeId,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                           @PageableDefault Pageable pageable) {
        Page<OrdersByOwnerResponseDto> orders = orderService.getOrdersByOwner(storeId, userDetails, pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrder(@PathVariable UUID orderId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderDetailResponseDto orderDetail = orderService.getOrder(orderId, userDetails);
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}/online")
    public ResponseEntity<Void> updateOrderOnline(@PathVariable UUID orderId,
                                                  @RequestBody OrderUpdateOnlineRequestDto requestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateOrderOnline(orderId, requestDto, userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}/offline")
    public ResponseEntity<Void> updateOrderOffline(@PathVariable UUID orderId,
                                                   @RequestBody OrderUpdateOfflineRequestDto requestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateOrderOffline(orderId, requestDto, userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.cancelOrder(orderId, userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}")
    @Secured({"MASTER", "MANAGER"})
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteOrder(orderId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/orders/search")
    public ResponseEntity<Page<OrderSearchResponseDto>> searchOrder(@RequestParam String keyword,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PageableDefault Pageable pageable) {
        Page<OrderSearchResponseDto> orders = orderService.searchOrder(keyword, userDetails, pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
