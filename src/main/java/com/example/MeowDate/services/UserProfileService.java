package com.example.MeowDate.services;

import com.example.MeowDate.models.UserProfile;
import com.example.MeowDate.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void save(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }
}
