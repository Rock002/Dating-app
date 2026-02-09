package com.example.MeowDate.controllers;

import com.example.MeowDate.models.ChatMessage;
import com.example.MeowDate.models.User;
import com.example.MeowDate.services.ChatMessageService;
import com.example.MeowDate.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WebSocketChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChatController.class);
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public WebSocketChatController(UserService userService, SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload @Valid ChatMessage message, Authentication authentication) {

        LOGGER.info("=== НАЧАЛО ОБРАБОТКИ СООБЩЕНИЯ ===");
        LOGGER.info("Message: {}", message);
        LOGGER.info("Аутентификация: {}", authentication);

        try {
            User sender;

            if (authentication != null && authentication.isAuthenticated()) {
                LOGGER.info("Аутентификация найдена: {}", authentication.getName());
                sender = userService.findByUsername(authentication.getName());
            } else {
                LOGGER.info("Аутентификация отсутствует");

                if (message.getSenderId() != null) {
                    sender = userService.findById(message.getSenderId());

                    if (sender != null) {
                        LOGGER.info("Пользователь найден по ID");
                    } else {
                        LOGGER.info("Пользователь не найден по ID");
                        return;
                    }
                } else  {
                    LOGGER.info("senderId пустой");
                    return;
                }
            }

            if (sender == null) {
                LOGGER.info("Отправитель не найден");
                return;
            }

            message.setSenderID(sender.getId());
            message.setSenderName(sender.getUsername());

            if (message.getTimestamp() == null) {
                message.setTimestamp(LocalDateTime.now());
            }

            String receiverChannel = "/queue/messages." + message.getReceiverId();
            String senderChannel = "/queue/messages." + message.getSenderId();

            // Отправляем получателю
            messagingTemplate.convertAndSend(receiverChannel, message);

            // Отправляем отправителю
            messagingTemplate.convertAndSend(senderChannel, message);

            chatMessageService.save(message);
        } catch (Exception e) {
            LOGGER.error("Ошибка при отправке сообщения: ", e);
        }
        LOGGER.info("=== КОНЕЦ ОБРАБОТКИ СООБЩЕНИЯ ===");
    }

    @GetMapping("/chat/{userId}")
    public String simpleChatPage(@PathVariable Long userId, Authentication authentication, Model model) {
        User currentUser = userService.findByUsername(authentication.getName());
        User otherUser = userService.findById(userId);

        List<ChatMessage> chatHistory = chatMessageService.getChatHistory(currentUser.getId(), otherUser.getId());

        model.addAttribute("currentUserId", currentUser.getId());
        model.addAttribute("currentUsername", currentUser.getUsername());
        model.addAttribute("otherUserId", otherUser.getId());
        model.addAttribute("otherUsername", otherUser.getUsername());
        model.addAttribute("chatHistory", chatHistory);

        return "chat";
    }
}
