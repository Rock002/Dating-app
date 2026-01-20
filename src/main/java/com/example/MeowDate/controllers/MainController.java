package com.example.MeowDate.controllers;

import com.example.MeowDate.models.*;
import com.example.MeowDate.services.LikeService;
import com.example.MeowDate.services.MatchService;
import com.example.MeowDate.services.PhotoService;
import com.example.MeowDate.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class MainController {

    private final UserService userService;
    private final LikeService likeService;
    private final PhotoService photoService;
    private final MatchService matchService;

    public MainController(UserService userService, LikeService likeService, PhotoService photoService, MatchService matchService) {
        this.userService = userService;
        this.likeService = likeService;
        this.photoService = photoService;
        this.matchService = matchService;
    }

    @GetMapping("/")
    public String mainPage(Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);
        UserProfile userProfile = currentUser.getUserProfile();

//        if (userProfile.getCoordinates() == null ||
//                userProfile.getDateOfBirth() == null ||
//                userProfile.getSex() == '\u0000') {
//            return "errorMainPage";
//        }
        List<User> otherUsers = userService.getOtherUsers(username);

        Map<Long, Long> profileFirstPhotoIds = new HashMap<>();
        for (User user : otherUsers) {
            if (user.getPhotos() != null && !user.getPhotos().isEmpty()) {
                Photo firstPhoto = user.getPhotos().get(0);
                profileFirstPhotoIds.put(user.getId(), firstPhoto.getId());
            }
        }

        model.addAttribute("profiles", otherUsers);
        model.addAttribute("currentUsername", username);
        model.addAttribute("profileFirstPhotoIds", profileFirstPhotoIds);
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
    public String matchesPage(Authentication authentication, Model model) {
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        List<Match> matches = matchService.getUserMatches(currentUser);

        model.addAttribute("matches", matches);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("matchService", matchService);

        return "matches";
    }

    @GetMapping("/chats")
    public String chatsPage() {
        return "chats";
    }
}
