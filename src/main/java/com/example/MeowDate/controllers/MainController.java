package com.example.MeowDate.controllers;

import com.example.MeowDate.models.Like;
import com.example.MeowDate.models.User;
import com.example.MeowDate.models.UserProfile;
import com.example.MeowDate.services.LikeService;
import com.example.MeowDate.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final LikeService likeService;

    public MainController(UserService userService, LikeService likeService) {
        this.userService = userService;
        this.likeService = likeService;
    }

    @GetMapping("/")
    public String mainPage(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        UserProfile userProfile = user.getUserProfile();

//        if (userProfile.getCoordinates() == null ||
//                userProfile.getDateOfBirth() == null ||
//                userProfile.getSex() == '\u0000') {
//            return "errorMainPage";
//        }

        return "main";
    }

    @GetMapping("/likes")
    public String likesPage(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<Like> likes = likeService.findByReceiver(user);
        model.addAttribute("likes", likes);

        return "likes";
    }

    @GetMapping("/matches")
    public String matchesPage() {
        return "matches";
    }

    @GetMapping("/chats")
    public String chatsPage() {
        return "chats";
    }
}
