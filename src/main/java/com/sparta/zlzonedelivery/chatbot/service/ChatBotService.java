package com.sparta.zlzonedelivery.chatbot.service;


import com.sparta.zlzonedelivery.chatbot.entity.ChatBot;
import com.sparta.zlzonedelivery.chatbot.repository.ChatBotRepository;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotCreateResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotReadResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotServiceCreateDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotServiceSearchDto;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;

    public ChatBotCreateResponseDto createAnswer(ChatBotServiceCreateDto chatBotServiceCreateDto) {

        ChatBot chatBot = new ChatBot(chatBotServiceCreateDto.query(),
                chatBotServiceCreateDto.response(),
                chatBotServiceCreateDto.user());

        return ChatBotCreateResponseDto.fromEntity(chatBotRepository.save(chatBot));
    }

    public ChatBotReadResponseDto getQueryAndAnswer(UUID chatbotId) {

        return ChatBotReadResponseDto.fromEntity(chatBotRepository.findByIdAndIsPublicIsTrue(chatbotId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUERY_AND_ANSWER_NOT_FOUND)));
    }

    public Page<ChatBotReadResponseDto> getAllQueryAndAnswer(Pageable pageable) {
        return chatBotRepository.findAllByIsPublicIsTrueOrderByUpdatedAt(pageable)
                .map(ChatBotReadResponseDto::fromEntity);
    }

    public void deleteQueryAndAnswer(UUID id) {
        ChatBot chatBot = chatBotRepository.findByIdAndIsPublicIsTrue(id)
                .orElseThrow(() -> new CustomException(ErrorCode.QUERY_AND_ANSWER_NOT_FOUND));

        chatBotRepository.deleteById(chatBot.getId());
    }

    public Page<ChatBotReadResponseDto> searchByUser(ChatBotServiceSearchDto serviceSearchDto) {

        return chatBotRepository.searchAllByUserAndIsPublicIsTrueOrderByUpdatedAt(
                        serviceSearchDto.user(),
                        serviceSearchDto.pageable())
                .map(ChatBotReadResponseDto::fromEntity);
    }

}
