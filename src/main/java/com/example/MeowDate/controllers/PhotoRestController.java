package com.example.MeowDate.controllers;

import com.example.MeowDate.models.Photo;
import com.example.MeowDate.services.photo.PhotoWithCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class PhotoRestController {
    private final PhotoWithCacheService photoWithCacheService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoRestController.class);

    public PhotoRestController(PhotoWithCacheService photoWithCacheService) {
        this.photoWithCacheService = photoWithCacheService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        Photo photo = photoWithCacheService.getPhotoById(id);

        if (photo.getBytes() == null) {
            LOGGER.info("Фото с id = {} не найдено", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(photo.getBytes());
    }
}
