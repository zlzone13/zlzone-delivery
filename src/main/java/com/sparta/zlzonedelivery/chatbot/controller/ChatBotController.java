package com.sparta.zlzonedelivery.chatbot.controller;

import com.sparta.zlzonedelivery.chatbot.service.ChatBotService;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotCreateRequestDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotCreateResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotReadResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotServiceCreateDto;
import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/chat-bots")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

    private final OpenAiChatModel openAiChatModel;

    @PostMapping
    public ChatBotCreateResponseDto createAnswer(@RequestBody ChatBotCreateRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {

        validateUser(userDetails);

        String response = openAiChatModel.call(requestDto.query());
        ChatBotServiceCreateDto chatBotServiceCreateDto = new ChatBotServiceCreateDto(requestDto.query(), response, userDetails.getUser());

        return chatBotService.createAnswer(chatBotServiceCreateDto);
    }

    @GetMapping("/{chat-bot_id}")
    public ChatBotReadResponseDto getQueryAndAnswer(@PathVariable("chat-bot_id") UUID uuid,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        validateUser(userDetails);
        return chatBotService.getQueryAndAnswer(uuid);
    }

    private static void validateUser(UserDetailsImpl userDetails) {
        UserRole role = userDetails.getRole();

        if (role.getValue().equals("CUSTOMER")) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }


}
