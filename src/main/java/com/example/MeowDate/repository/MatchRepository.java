package com.example.MeowDate.repository;

import com.example.MeowDate.models.Match;
import com.example.MeowDate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m WHERE m.user1 = :user OR m.user2 = :user")
    List<Match> findAllByUser(User user);

    @Query("SELECT m FROM Match m WHERE (m.user1 = :user1 AND m.user2 = :user2) OR (m.user1 = :user2 AND m.user2 = :user1)")
    List<Match> findByUsers(@Param("user1") User user1, @Param("user2") User user2);
}
