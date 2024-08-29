package com.sparta.zlzonedelivery.chatbot.service;


import com.sparta.zlzonedelivery.chatbot.entity.ChatBot;
import com.sparta.zlzonedelivery.chatbot.repository.ChatBotRepository;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotReadResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotCreateResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotServiceCreateDto;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;

    private final UserService userService;

    public ChatBotCreateResponseDto createAnswer(ChatBotServiceCreateDto chatBotServiceCreateDto) {

        if (!userService.verifyUser(chatBotServiceCreateDto.user().getUsername(),
                chatBotServiceCreateDto.user().getPassword())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        ChatBot chatBot = new ChatBot(chatBotServiceCreateDto.query(),
                chatBotServiceCreateDto.response(), chatBotServiceCreateDto.user());

        return ChatBotCreateResponseDto.fromEntity(chatBotRepository.save(chatBot));
    }

    public ChatBotReadResponseDto getQueryAndAnswer(UUID chatbotId) {

        return  ChatBotReadResponseDto.fromEntity(chatBotRepository.findByIdAndIsPublicIsTrue(chatbotId)
                .orElseThrow(()->new CustomException(ErrorCode.QUERY_AND_ANSWER_NOT_FOUND)));
    }

}
