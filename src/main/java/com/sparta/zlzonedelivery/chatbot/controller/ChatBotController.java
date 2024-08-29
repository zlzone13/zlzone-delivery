package com.sparta.zlzonedelivery.chatbot.controller;

import com.sparta.zlzonedelivery.chatbot.service.ChatBotService;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotCreateRequestDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotCreateResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotReadResponseDto;
import com.sparta.zlzonedelivery.chatbot.service.dtos.ChatBotServiceCreateDto;
import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.global.dto.ResponseDto;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseDto<ChatBotCreateResponseDto> createAnswer(@RequestBody ChatBotCreateRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        validateUser(userDetails);

        String response = openAiChatModel.call(requestDto.query());
        ChatBotServiceCreateDto chatBotServiceCreateDto = new ChatBotServiceCreateDto(requestDto.query(), response, userDetails.getUser());

        return ResponseDto.okWithData(chatBotService.createAnswer(chatBotServiceCreateDto));
    }

    @GetMapping("/{chat-bot_id}")
    public ResponseDto<ChatBotReadResponseDto> getQueryAndAnswer(@PathVariable("chat-bot_id") UUID uuid,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        validateUser(userDetails);
        return ResponseDto.okWithData(chatBotService.getQueryAndAnswer(uuid));
    }

    @GetMapping
    public ResponseDto<Page<ChatBotReadResponseDto>> getAllQueryAndAnswer(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault Pageable pageable) {
        UserRole role = userDetails.getRole();

        if (role.getValue().equals("CUSTOMER") || role.getValue().equals("OWNER")) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return ResponseDto.okWithData(chatBotService.getAllQueryAndAnswer(pageable));

    }

    @DeleteMapping("/{chat-bot_id}")
    public ResponseDto<Void> deleteQueryAndAnswer(@PathVariable("chat-bot_id") UUID id,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        validateUser(userDetails);

        chatBotService.deleteQueryAndAnswer(id);

        return ResponseDto.ok();
    }

    private static void validateUser(UserDetailsImpl userDetails) {
        UserRole role = userDetails.getRole();

        if (role.getValue().equals("CUSTOMER")) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }

}
