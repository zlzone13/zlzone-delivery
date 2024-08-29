package com.sparta.zlzonedelivery.chatbot.entity;


import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_chat_bots")
public class ChatBot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Column
    @Getter
    private String query;

    @Column
    @Getter
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ChatBot(String query, String answer, User user) {
        this.query = query;
        this.answer = answer;
        this.user = user;
    }

}
