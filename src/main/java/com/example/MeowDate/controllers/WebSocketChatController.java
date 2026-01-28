package com.example.MeowDate.controllers;

import com.example.MeowDate.models.ChatMessage;
import com.example.MeowDate.models.User;
import com.example.MeowDate.services.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebSocketChatController {
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketChatController(UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.test")
    @SendToUser("/queue/messages")
    public ChatMessage testMessage(@Payload ChatMessage message) {
        return message;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage message, Authentication authentication) {
        User sender = userService.findByUsername(authentication.getName());

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId().toString(),
                "/queue/messages",
                message
        );

        messagingTemplate.convertAndSendToUser(
                sender.getId().toString(),
                "/queue/messages",
                message
        );
    }

    @GetMapping("/chat/{userId}")
    public String simpleChatPage(@PathVariable Long userId, Authentication authentication, Model model) {
        User currentUser = userService.findByUsername(authentication.getName());
        User otherUser = userService.findById(userId);

        model.addAttribute("currentUserId", currentUser.getId());
        model.addAttribute("currentUsername", currentUser.getUsername());
        model.addAttribute("otherUserId", otherUser.getId());
        model.addAttribute("otherUsername", otherUser.getUsername());

        return "chat";
    }

}
