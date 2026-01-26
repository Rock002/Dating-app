package com.example.MeowDate.controllers;

import com.example.MeowDate.models.Photo;
import com.example.MeowDate.models.User;
import com.example.MeowDate.models.UserProfile;
import com.example.MeowDate.services.PhotoService;
import com.example.MeowDate.services.UserProfileService;
import com.example.MeowDate.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Controller
public class UserController {

    private final UserService userService;
    private final PhotoService photoService;
    private final UserProfileService userProfileService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PhotoService photoService, UserProfileService userProfileService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.photoService = photoService;
        this.userProfileService = userProfileService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());

        var photos = photoService.getPhotosByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("photos", photos);

        return "profile";
    }

    @PostMapping("/postregistration")
    public String postRegistration(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("USER");

        userService.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(user.getUsername());
        userProfile.setSex('x');
        userProfile.setUser(user);

        user.setUserProfile(userProfile);

        userProfileService.save(userProfile);

        return "redirect:/login";
    }

    @GetMapping("/profile/error-password-change")
    public String errorPasswordChange() {
        return "errorPasswordChange";
    }

    @GetMapping("/profile/edit")
    public String profileEdit(Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        model.addAttribute("user", currentUser);
        return "profileEdit";
    }

    @GetMapping("/profile/change")
    public String changeProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);
        model.addAttribute("user", currentUser);

        return "profileChange";
    }

    @GetMapping("/profile/change-password")
    public String changePassword(Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);
        model.addAttribute("user", currentUser);

        return "passwordChangeForm";
    }

    @PostMapping("/profile/post-change")
    public String postChangeProfile(@RequestParam("username") String username,
                                    @RequestParam("email") String email,
                                    Authentication authentication) {
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        user.setUsername(username);
        user.setEmail(email);
        userService.update(user);

        return "redirect:/profile";
    }

    @PostMapping("/profile/post-change-password")
    public String postChangePassword(@RequestParam("oldPassword") String oldPassword,
                                    @RequestParam("newPassword") String newPassword,
                                    Authentication authentication) {
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.update(user);

            return "redirect:/profile";
        } else {
            return "redirect:/profile/error-password-change";
        }

    }

    @PostMapping("/profile/update-info")
    public String postProfileInfoEdit(@RequestParam("firstName") String firstName,
                                  @RequestParam("sex") char sex,
                                  @RequestParam("birthDate") LocalDate birthDate,
                                  @RequestParam("location") String location,
                                  @RequestParam("info") String info,
                                  Authentication authentication
    ) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        UserProfile userProfile = currentUser.getUserProfile();

        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUser(currentUser);
        }

        userProfile.setFirstName(firstName);
        userProfile.setSex(sex);
        userProfile.setBirthDate(birthDate);
        userProfile.setLocation(location);
        userProfile.setInfo(info);

        userProfileService.update(userProfile);
        return "redirect:/profile";
    }
}
