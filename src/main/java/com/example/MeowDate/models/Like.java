package com.example.MeowDate.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "dateOfLike")
    private LocalDate dateOfLike;

    public Like() {}

    public Like(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.dateOfLike = LocalDate.now();
    }
}
