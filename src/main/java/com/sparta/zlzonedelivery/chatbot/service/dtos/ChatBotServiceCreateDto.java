package com.sparta.zlzonedelivery.chatbot.service.dtos;

import com.sparta.zlzonedelivery.user.User;

public record ChatBotServiceCreateDto(
        String query,
        String response,
        User user
) {
}
