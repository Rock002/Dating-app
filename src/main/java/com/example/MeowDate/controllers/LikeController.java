package com.example.MeowDate.controllers;

import com.example.MeowDate.models.Like;
import com.example.MeowDate.models.Match;
import com.example.MeowDate.models.User;
import com.example.MeowDate.services.LikeService;
import com.example.MeowDate.services.MatchService;
import com.example.MeowDate.services.UserService;
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

        // TODO: добавить удаление лайков при создании мэтча с обеих сторон

        List<Like> likesWithMaybeMutually = likeService.findByReceiver(sender);

        for (Like like : likesWithMaybeMutually) {
            if (like.getSender().getId().equals(receiver.getId())) {
                matchService.createAndSaveMatch(sender, receiver);
                likeService.delete(like);

                return "redirect:/";
            }
        }

        likeService.createAndSaveLike(sender, receiver);

        return "redirect:/";
    }
}
