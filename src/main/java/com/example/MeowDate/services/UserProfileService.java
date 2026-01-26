package com.example.MeowDate.services;

import com.example.MeowDate.models.UserProfile;
import com.example.MeowDate.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

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
