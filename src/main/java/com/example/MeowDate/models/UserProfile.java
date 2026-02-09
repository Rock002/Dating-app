package com.example.MeowDate.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
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

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getLocation() {
        return location;
    }

    public User getUser() {
        return user;
    }

    public char getSex() {
        return sex;
    }

    public String getInfo() {
        return info;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
