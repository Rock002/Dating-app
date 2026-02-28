package com.example.MeowDate.controllers;

import com.example.MeowDate.models.*;
import com.example.MeowDate.services.LikeService;
import com.example.MeowDate.services.MatchService;
import com.example.MeowDate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final LikeService likeService;
    private final MatchService matchService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String mainPage(Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);
        UserProfile userProfile = currentUser.getUserProfile();

        if (userProfile.getLocation() == null ||
                userProfile.getBirthDate() == null ||
                userProfile.getSex() == '\u0000') {
            LOGGER.info("Пользователь {} не указал информацию о себе для просмотре анкет", username);
            return "errorMainPage";
        }

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

        LOGGER.info("Переход на главную страницу пользователя {}", username);
        return "main";
    }

    @GetMapping("/likes")
    public String likesPage(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<Like> likes = likeService.findByReceiver(user);
        model.addAttribute("likes", likes);

        LOGGER.info("Переход страницу лайков пользователя {}", username);
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

        LOGGER.info("Переход на страницу мэтчей пользователя {}", currentUsername);
        return "matches";
    }

    @GetMapping("/chats")
    public String chatsPage(Authentication authentication) {
        LOGGER.info("Переход на страницу чатов пользователя {}", authentication.getName());
        return "chats";
    }
}
