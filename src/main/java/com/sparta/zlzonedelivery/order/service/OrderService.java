package com.sparta.zlzonedelivery.order.service;

import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.order.entity.OrderStatus;
import com.sparta.zlzonedelivery.order.entity.OrderType;
import com.sparta.zlzonedelivery.order.repository.OrderRepository;
import com.sparta.zlzonedelivery.order.service.dto.OrderCreateOfflineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderCreateOnlineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderDetailResponseDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderItemDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderSearchResponseDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderUpdateOfflineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderUpdateOnlineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrdersByCustomerResponseDto;
import com.sparta.zlzonedelivery.order.service.dto.OrdersByOwnerResponseDto;
import com.sparta.zlzonedelivery.product.entity.Product;
import com.sparta.zlzonedelivery.product.repository.ProductRepository;
import com.sparta.zlzonedelivery.relationship.OrderProduct;
import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.store.repository.StoreRepository;
import com.sparta.zlzonedelivery.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final StoreRepository storeRepository;

//    private final AddressRepository addressRepository;

//    private final PaymentRepository paymentRepository;

    @Transactional
    public void createOrderOnline(UUID storeId, OrderCreateOnlineRequestDto requestDto, UserDetailsImpl userDetails) {
        Store store = checkStore(storeId);

        int totalAmount = calculateTotalAmount(requestDto.items());

//        Address address = addressRepository.findById(requestDto.addressId())
//                .orElseThrow(() -> new Custom~~
//
//        Payment payment = paymentRepository.findById(requestDto.paymentId())
//                .orElseThrow(() -> new Custom~~

        Order order = Order.builder()
                .user(userDetails.getUser())
//                .address(address)
//                .payment(payment)
                .store(store)
                .orderType(OrderType.ONLINE)
                .toRiderRequest(requestDto.toRiderRequest())
                .toOwnerRequest(requestDto.toOwnerRequest())
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.PENDING)
                .build();

        addProductsToOrder(order, requestDto.items());

        orderRepository.save(order);
    }

    @Transactional
    public void createOrderOffline(UUID storeId, OrderCreateOfflineRequestDto requestDto, UserDetailsImpl userDetails) {
        Store store = checkStore(storeId);

        int totalAmount = calculateTotalAmount(requestDto.items());

//        Payment payment = paymentRepository.findById(requestDto.paymentId())
//                .orElseThrow(() -> new Custom~~

        Order order = Order.builder()
                .user(userDetails.getUser())
//                .payment(payment)
                .store(store)
                .orderType(OrderType.OFFLINE)
                .toOwnerRequest(requestDto.toOwnerRequest())
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.PENDING)
                .build();

        addProductsToOrder(order, requestDto.items());

        orderRepository.save(order);
    }

    public Page<OrdersByCustomerResponseDto> getOrdersByCustomer(UserDetailsImpl userDetails, Pageable pageable) {
        Long userId = userDetails.getUserId();
        Page<Order> orders = orderRepository.findAllByUserId(userId, pageable);

        return orders.map(OrdersByCustomerResponseDto::fromEntity);
    }

    public Page<OrdersByOwnerResponseDto> getOrdersByOwner(UUID storeId, UserDetailsImpl userDetails, Pageable pageable) {
        Store store = checkStore(storeId);
        Long userId = userDetails.getUserId();

        if (!store.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        }

        Page<Order> orders = orderRepository.findAllByStoreId(storeId, pageable);

        return orders.map(OrdersByOwnerResponseDto::fromEntity);
    }

    public OrderDetailResponseDto getOrder(UUID orderId, UserDetailsImpl userDetails) {
        Order order = checkOrder(orderId);
        Store store = checkStore(order.getStore().getId());
        Long userId = userDetails.getUserId();

        if ((Objects.equals(order.getUser().getId(), userId))
                || store.getUser().getId().equals(userId)
                || (userDetails.getRole() == UserRole.MASTER || userDetails.getRole() == UserRole.MANAGER)) {
            return OrderDetailResponseDto.fromEntity(order);
        }

        throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
    }

    @Transactional
    public void updateOrderOnline(UUID orderId, OrderUpdateOnlineRequestDto requestDto, UserDetailsImpl userDetails) {
        Order order = checkOrder(orderId);
        Store store = checkStore(order.getStore().getId());
        Long userId = userDetails.getUserId();

        checkOrderStatus(order.getOrderStatus());

        if ((Objects.equals(order.getUser().getId(), userId))
                || store.getUser().getId().equals(userId)
                || (userDetails.getRole() == UserRole.MASTER || userDetails.getRole() == UserRole.MANAGER)) {

            order.updateOrderOnline(requestDto);

            orderRepository.save(order);
        } else {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

    }

    @Transactional
    public void updateOrderOffline(UUID orderId, OrderUpdateOfflineRequestDto requestDto, UserDetailsImpl userDetails) {
        Order order = checkOrder(orderId);
        Store store = checkStore(order.getStore().getId());
        Long userId = userDetails.getUserId();

        checkOrderStatus(order.getOrderStatus());

        if (store.getUser().getId().equals(userId)
                || (userDetails.getRole() == UserRole.MASTER || userDetails.getRole() == UserRole.MANAGER)) {

            order.updateOrderOffline(requestDto);

            orderRepository.save(order);

        }

        throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
    }

    @Transactional
    public void cancelOrder(UUID orderId, UserDetailsImpl userDetails) {
        Order order = checkOrder(orderId);
        Store store = checkStore(order.getStore().getId());
        Long userId = userDetails.getUserId();

        if (Objects.equals(order.getUser().getId(), userId)
                || store.getUser().getId().equals(userId)
                || userDetails.getRole() == UserRole.MASTER
                || userDetails.getRole() == UserRole.MANAGER) {

            order.cancelOrder(OrderStatus.CANCELED);

            orderRepository.save(order);
        } else {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

    }

    @Transactional
    public void deleteOrder(UUID orderId, UserDetailsImpl userDetails) {
        Order order = checkOrder(orderId);

        String username = userDetails.getUsername();

        order.setDeletedBy(username);

        orderRepository.save(order);
    }

    public Page<OrderSearchResponseDto> searchOrder(String keyword, UserDetailsImpl userDetails, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByUserIdAndStoreStoreNameContaining(userDetails.getUserId(), keyword, pageable);

        if (orders.isEmpty()) {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        return orders.map(OrderSearchResponseDto::fromEntity);

    }


    private Order checkOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    private Store checkStore(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }

    private Product checkProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
    }

    private void checkOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != OrderStatus.PENDING) {
            throw new CustomException(ErrorCode.ORDER_ALREADY_PROCESSED);
        }
    }

    private void addProductsToOrder(Order order, List<OrderItemDto> items) {
        for (OrderItemDto itemDto : items) {
            addProductToOrder(order, itemDto);
        }
    }

    private void addProductToOrder(Order order, OrderItemDto itemDto) {
        Product product = productRepository.findById(itemDto.productId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .quantity(itemDto.quantity())
                .build();

        order.addOrderProduct(orderProduct);
    }

    private int calculateTotalAmount(List<OrderItemDto> items) {
        int total = 0;
        for (OrderItemDto item : items) {
            Product product = checkProduct(item.productId());
            total += product.getPrice() * item.quantity();
        }
        return total;
    }

}
