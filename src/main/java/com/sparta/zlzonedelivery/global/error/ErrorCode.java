package com.sparta.zlzonedelivery.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // User
    DUPLICATED_NICKNAME("DUPLICATED_NICKNAME", "이미 사용 중인 Nickname이 있습니다.", 409),
    DUPLICATED_USERNAME("DUPLICATED_USERNAME", "이미 사용 중인 Username 입니다.", 409),
    DUPLICATED_EMAIL("DUPLICATED_EMAIL", "이미 사용 중인 Email 입니다.", 409),
    INVALID_PASSWORD("INVALID_PASSWORD", "패스워드를 형식에 맞춰 입력해주세요.", 400),
    INVALID_NICKNAME("INVALID_NICKNAME", "Nickname을 형식에 맞춰 입력해주세요.", 400),
    INVALID_EMAIL("INVALID_EMAIL", "Email을 형식에 맞춰 입력해주세요.", 400),

    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "로그인에 실패하였습니다.", 401),
    USER_NOT_FOUND("USER_NOT_FOUND", "존재하지 않는 회원입니다.", 404),

    ACCESS_DENIED("ACCESS_DENIED", "접근 권한이 없습니다.", 403),
    INVALID_TOKEN("INVALID_TOKEN", "Token이 유효하지 않습니다.", 401),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", "존재하지 않는 사용자입니다.", 404),
    ADDRESS_NOT_FOUND("ADDRESS_NOT_FOUND", "존재하지 않는 주소입니다.", 404),

    // Order
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "존재하지 않는 주문입니다.", 404),
    ORDER_ALREADY_PROCESSED("ORDER_ALREADY_PROCESSED", "이미 처리된 주문입니다.", 409),

    // Product
    MENU_NOT_FOUND("MENU_NOT_FOUND", "메뉴를 찾을 수 없습니다.", 404),
    INVALID_MENU("INVALID_MENU", "유효하지 않은 메뉴입니다.", 400),
    DUPLICATED_MENU("DUPLICATED_MENU", "메뉴가 이미 존재합니다.", 409),

    // Payment
    PAYMENT_NOT_FOUND("PAYMENT_NOT_FOUND", "결제 내역을 찾을 수 없습니다.", 404),
    PAYMENT_FAILED("PAYMENT_FAILED", "결제가 실패했습니다.", 402),
    PAYMENT_ALREADY_PROCESSED("PAYMENT_ALREADY_PROCESSED", "이미 처리된 결제입니다.", 409),

    // Store
    STORE_NOT_FOUND("STORE_NOT_FOUND", "음식점을 찾을 수 없습니다.", 404),
    INVALID_STORE("INVALID_STORE", "유효하지 않은 음식점입니다.", 400),
    DUPLICATED_STORE("DUPLICATED_STORE", "이미 존재하는 음식점입니다.", 409),

    // Category
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "카테고리를 찾을 수 없습니다.", 404),
    DUPLICATED_CATEGORY("DUPLICATED_CATEGORY", "이미 존재하는 카테고리입니다.", 400),
    INVALID_CATEGORY("INVALID_CATEGORY", "유효하지 않은 카테고리입니다.", 400),

    // Chatbot
    CHATBOT_UNAVAILABLE("CHATBOT_UNAVAILABLE", "일시적으로 Chatbot 서비스를 사용할 수 없습니다.", 503),
    CHATBOT_GENERATION_FAILED("CHATBOT_GENERATION_FAILED", "상품 설명 생성이 실패했습니다.", 500),
    QUERY_AND_ANSWER_NOT_FOUND("QUERY_AND_ANSWER_NOT_FOUND", "질문과 답변을 찾을 수 없습니다.", 404),
    
    //Location
    LOCATION_NOT_FOUND("LOCATION_NOT_FOUND", "주소를 찾을 수 없습니다.", 404),

    //ProductCategory
    PRODUCT_CATEGORY_NOT_FOUND("PRODUCT_CATEGORY_NOT_FOUND", "상품에 대한 카테고리를 찾을 수 없습니다.", 404);

    private final String code;

    private final String message;

    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
