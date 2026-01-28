package com.example.MeowDate.services;

import com.example.MeowDate.models.ChatMessage;
import com.example.MeowDate.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void save(ChatMessage message) {
        chatMessageRepository.save(message);
    }
}
