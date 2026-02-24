package com.example.MeowDate.services;

import com.example.MeowDate.models.Photo;
import com.example.MeowDate.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;

    @Transactional
    public void savePhotos(List<Photo> photoList) {
        photoRepository.saveAll(photoList);

    }

    public List<Photo> getPhotosByUserId(Long userId) {
        return photoRepository.findByUserId(userId);
    }

    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }
}
