package com.example.MeowDate.services;

import com.example.MeowDate.models.Like;
import com.example.MeowDate.models.User;
import com.example.MeowDate.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    public List<Like> findByReceiver(User receiver) {
        List<Optional<Like>> likesWithOptional = likeRepository.findByReceiver(receiver);
        List<Like> likes = likesWithOptional.stream()
                .map(like -> like.orElse(null))
                .collect(Collectors.toList());
        return likes;

    }

    public void save(Like like) {
        likeRepository.save(like);
    }

    public boolean isLikeExist(Like like) {
        List<Like> likes = findByReceiver(like.getReceiver());

        for (Like currentLike : likes) {
            if (like.getSender().getId().equals(currentLike.getSender().getId())) {
                return true;
            }
        }

        return false;
    }

    public void createAndSaveLike(User sender, User receiver) {
        Like like = new Like(
                sender,
                receiver
        );

        if (!isLikeExist(like)) {
            likeRepository.save(like);
        }
    }

    public void delete(Like like) {
        likeRepository.delete(like);
    }

//    @Transactional
//    public void deleteBothLike(User user1, User user2) {}
}
