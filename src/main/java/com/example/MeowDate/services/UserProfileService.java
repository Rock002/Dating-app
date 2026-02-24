package com.example.MeowDate.services;

import com.example.MeowDate.models.UserProfile;
import com.example.MeowDate.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public void save(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void update(UserProfile userProfile) {
        userProfileRepository.update(
                userProfile.getUser(),
                userProfile.getFirstName(),
                userProfile.getBirthDate(),
                userProfile.getSex(),
                userProfile.getLocation(),
                userProfile.getInfo()
        );
    }
}
