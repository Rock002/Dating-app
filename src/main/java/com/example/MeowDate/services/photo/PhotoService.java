package com.example.MeowDate.services.photo;

import com.example.MeowDate.models.Photo;

import java.util.List;

public interface PhotoService {
    public void savePhotos(List<Photo> photoList, Long userId);

    public List<Photo> getPhotosByUserId(Long userId);

    public Photo getPhotoById(Long id);

}
