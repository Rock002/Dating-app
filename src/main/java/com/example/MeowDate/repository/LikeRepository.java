package com.example.MeowDate.repository;

import com.example.MeowDate.models.Like;
import com.example.MeowDate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByReceiver(User receiver);
}
