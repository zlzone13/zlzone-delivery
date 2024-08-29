package com.sparta.zlzonedelivery.chatbot.service.dtos;

import com.sparta.zlzonedelivery.chatbot.entity.ChatBot;

import java.util.UUID;

public record ChatBotReadResponseDto(
        UUID chatbotId,
        String query,
        String answer
) {

    public static ChatBotReadResponseDto fromEntity(ChatBot chatBot) {
        return new ChatBotReadResponseDto(
                chatBot.getId(),
                chatBot.getQuery(),
                chatBot.getAnswer());
    }

}
