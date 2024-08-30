package com.sparta.zlzonedelivery.chatbot.service.dtos;

import com.sparta.zlzonedelivery.chatbot.entity.ChatBot;

import java.util.UUID;

public record ChatBotCreateResponseDto(
        UUID chatbotId,
        String query,
        String answer
) {

    public static ChatBotCreateResponseDto fromEntity(ChatBot chatBot) {
        return new ChatBotCreateResponseDto(
                chatBot.getId(),
                chatBot.getQuery(),
                chatBot.getAnswer());
    }

}
