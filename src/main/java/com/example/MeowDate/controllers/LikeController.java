package com.example.MeowDate.controllers;

import com.example.MeowDate.models.Like;
import com.example.MeowDate.models.User;
import com.example.MeowDate.services.LikeService;
import com.example.MeowDate.services.MatchService;
import com.example.MeowDate.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final MatchService matchService;
    private final static Logger LOGGER = LoggerFactory.getLogger(LikeController.class);

    public LikeController(LikeService likeService, UserService userService, MatchService matchService) {
        this.likeService = likeService;
        this.userService = userService;
        this.matchService = matchService;
    }

    @PostMapping("/like/{id}")
    public String sendLike(@PathVariable Long id, Authentication authentication) {
        String senderUsername = authentication.getName();
        User sender = userService.findByUsername(senderUsername);
        User receiver = userService.findById(id);

        List<Like> likesWithMaybeMutually = likeService.findByReceiver(sender);

        LOGGER.info("Перебор возможных обратных лайков для sender = {}, receiver = {}", sender.getUsername(), receiver.getUsername());

        for (Like like : likesWithMaybeMutually) {
            if (like.getSender().getId().equals(receiver.getId())) {
                LOGGER.info("Взаимный лайк найден -- sender = {}, receiver = {}", sender.getUsername(), receiver.getUsername());
                matchService.createAndSaveMatch(sender, receiver);
                LOGGER.info("Новый мэтч создан -- sender = {}, receiver = {}", sender.getUsername(), receiver.getUsername());
                likeService.delete(like);
                LOGGER.info("Лайк удален -- sender = {}, receiver = {}", like.getSender().getUsername(), like.getReceiver().getUsername());

                return "redirect:/";
            }
        }

        LOGGER.info("Взаимного лайка нет");

        likeService.createAndSaveLike(sender, receiver);
        LOGGER.info("Создан новый лайк -- sender = {}, receiver = {}", sender.getUsername(), receiver.getUsername());

        return "redirect:/";
    }
}
