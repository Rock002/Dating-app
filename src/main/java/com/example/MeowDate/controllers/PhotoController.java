package com.example.MeowDate.controllers;

import com.example.MeowDate.models.Photo;
import com.example.MeowDate.models.User;
import com.example.MeowDate.services.UserService;
import com.example.MeowDate.services.photo.PhotoWithCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoWithCacheService photoWithCacheService;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoController.class);

    public PhotoController(PhotoWithCacheService photoWithCacheService, UserService userService) {
        this.photoWithCacheService = photoWithCacheService;
        this.userService = userService;
    }

    @GetMapping("/download")
    public String downloadPhotosPage() {
        return "downloadPhotos";
    }

    @PostMapping("/postDownload")
    public String downloadPhotos(@RequestParam("firstPhoto") MultipartFile firstFile,
                                 @RequestParam("secondPhoto") MultipartFile secondFile,
                                 @RequestParam("thirdPhoto") MultipartFile thirdFile,
                                 Authentication authentication
                                 ) throws IOException {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        LOGGER.info("Пользователь {} найден", user.getUsername());

        Photo firstPhoto = new Photo(
                firstFile.getOriginalFilename(),
                true,
                firstFile.getBytes()
        );
        Photo secondPhoto = new Photo(
                secondFile.getOriginalFilename(),
                false,
                secondFile.getBytes()
        );
        Photo thirdPhoto = new Photo(
                thirdFile.getOriginalFilename(),
                false,
                thirdFile.getBytes()
        );

        firstPhoto.setUser(user);
        secondPhoto.setUser(user);
        thirdPhoto.setUser(user);

        List<Photo> photoList = new ArrayList<>();
        photoList.add(firstPhoto);
        photoList.add(secondPhoto);
        photoList.add(thirdPhoto);

        user.setPhotos(photoList);

        photoWithCacheService.savePhotos(photoList, user.getId());
        LOGGER.info("Фото сохранены в базе для пользователя {}", user.getUsername());

        return "redirect:/profile";
    }
}
