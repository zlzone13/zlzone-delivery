package com.sparta.zlzonedelivery.chatbot.repository;

import com.sparta.zlzonedelivery.chatbot.entity.ChatBot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ChatBotRepository extends JpaRepository<ChatBot, UUID> {

    Optional<ChatBot> findByIdAndIsPublicIsTrue(UUID id);

    Page<ChatBot> findAllByIsPublicIsTrue(Pageable pageable);

}
