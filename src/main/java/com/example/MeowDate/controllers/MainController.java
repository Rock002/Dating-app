package com.example.MeowDate.controllers;

import com.example.MeowDate.models.User;
import com.example.MeowDate.models.UserProfile;
import com.example.MeowDate.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String mainPage(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        UserProfile userProfile = user.getUserProfile();

        if (userProfile.getCoordinates() == null ||
                userProfile.getDateOfBirth() == null ||
                userProfile.getSex() == '\u0000') {
            return "errorMainPage";
        }

        return "main";
    }
}
