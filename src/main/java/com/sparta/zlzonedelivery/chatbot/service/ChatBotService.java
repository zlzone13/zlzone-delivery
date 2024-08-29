package com.sparta.zlzonedelivery.chatbot.service;


import com.sparta.zlzonedelivery.chatbot.repository.ChatBotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;

}
