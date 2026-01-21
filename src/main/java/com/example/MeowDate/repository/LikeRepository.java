package com.example.MeowDate.repository;

import com.example.MeowDate.models.Like;
import com.example.MeowDate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT e FROM Like e WHERE e.receiver = :receiver")
    List<Optional<Like>> findByReceiver(@Param("receiver") User receiver);

    @Query("SELECT e FROM Like e WHERE e.sender = :sender AND e.receiver = :receiver")
    Like findLikeBySenderAndReceiver(@Param("sender") User sender, @Param("receiver") User receiver);
}
