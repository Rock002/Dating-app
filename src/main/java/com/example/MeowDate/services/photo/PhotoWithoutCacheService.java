package com.example.MeowDate.services.photo;

import com.example.MeowDate.models.Photo;
import com.example.MeowDate.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoWithoutCacheService implements PhotoService{
    private final PhotoRepository photoRepository;

    @Transactional
    @Override
    public void savePhotos(List<Photo> photoList, Long userId) {
        photoRepository.saveAll(photoList);
    }

    @Override
    public List<Photo> getPhotosByUserId(Long userId) {
        return photoRepository.findByUserId(userId);
    }

    @Override
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }
}
