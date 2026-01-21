package com.example.MeowDate.services;

import com.example.MeowDate.models.Match;
import com.example.MeowDate.models.User;
import com.example.MeowDate.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void save(Match match) {
        matchRepository.save(match);
    }

    public void createAndSaveMatch(User user1, User user2) {
        if (!isMatch(user1, user2)) {
            Match newMatch = new Match(user1, user2);
            matchRepository.save(newMatch);
        }
    }

    @Transactional
    public List<Match> getUserMatches(User user) {
        return matchRepository.findAllByUser(user);
    }

    @Transactional
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public boolean isMatch(User user1, User user2) {
        return !matchRepository.findByUsers(user1, user2).isEmpty();
    }

    public User getOtherUserInMatch(Match match, User currentUser) {
        if (match.getUser1().getId().equals(currentUser.getId())) {
            return match.getUser2();
        }

        return match.getUser1();
    }
}
