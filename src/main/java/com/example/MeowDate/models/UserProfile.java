package com.example.MeowDate.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "sex")
    private char sex;

    @Column(name = "location")
    private String location;

    @Size(max = 500)
    @Column(name = "info")
    private String info;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserProfile() {}

    public UserProfile(String firstName, LocalDate birthDate, char sex, String location, String info, User user) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.location = location;
        this.info = info;
        this.user = user;
    }
}
