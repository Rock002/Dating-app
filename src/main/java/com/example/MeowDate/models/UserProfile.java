package com.example.MeowDate.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "sex")
    private char sex;

    @Column(name = "coordinates")
    private double[] coordinates;

    @Column(name = "info")
    private String info;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserProfile() {}

    public UserProfile(String firstName, LocalDate dateOfBirth, char sex, @NotNull double[] coordinates, String info, User user) {
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.coordinates = coordinates;
        this.info = info;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public double[] getCoordinates() {
        return coordinates;
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

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
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
