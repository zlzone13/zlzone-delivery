package com.sparta.zlzonedelivery.chatbot.service.dtos;

import com.sparta.zlzonedelivery.user.User;
import org.springframework.data.domain.Pageable;

public record ChatBotServiceSearchDto(
        Pageable pageable,
        User user
) {

}
