package com.example.MeowDate.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

//    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "roles")
    private String roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Photo> photos = new ArrayList<>(List.of(new Photo()));

    public User() {}

    public String getUsername() {
        return username;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserProfile getUserProfileId() {
        return userProfile;
    }

    public Long getId() {
        return id;
    }

    public String getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserProfileId(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
