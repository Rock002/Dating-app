package com.example.MeowDate.services;

import com.example.MeowDate.models.Like;
import com.example.MeowDate.models.User;
import com.example.MeowDate.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public List<Like> findByReceiver(User receiver) {
        return likeRepository.findByReceiver(receiver);
    }
}
