package com.example.MeowDate.services;

import com.example.MeowDate.models.ChatMessage;
import com.example.MeowDate.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void save(ChatMessage message) {
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> getChatHistory(Long user1Id, Long user2Id) {
        return chatMessageRepository.findChatBetweenUsers(user1Id, user2Id);
    }
}
