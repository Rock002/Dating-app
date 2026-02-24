package com.example.MeowDate.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "messages")
public class ChatMessage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "sender_id")
    private Long senderId;

    @NotNull
    @Column(name = "receiver_id")
    private Long receiverId;

    @NotNull
    @Column(name = "sender_name")
    private String senderName;

    @NotNull
    @Size(max = 500)
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "is_read")
    private boolean isRead = false;

    public ChatMessage(Long senderId, Long receiverId, String senderName, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }
}
