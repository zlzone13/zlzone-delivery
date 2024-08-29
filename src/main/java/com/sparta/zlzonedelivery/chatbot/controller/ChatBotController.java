package com.sparta.zlzonedelivery.chatbot.controller;

import com.sparta.zlzonedelivery.chatbot.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chat-bots")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

}
