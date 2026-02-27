package com.example.MeowDate.services.photo;

import com.example.MeowDate.models.Photo;
import com.example.MeowDate.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoWithCacheService implements PhotoService{
    private final PhotoRepository photoRepository;
    private final RedisTemplate<String, Photo> redisPhotoTemplate;
    private final String REDIS_KEY_PREFIX = "userId:";
    private Long userId;

    @Override
    public void savePhotos(List<Photo> photoList, Long userId) {
        for (int i = 0; i < photoList.size(); i++) {
            redisPhotoTemplate.opsForValue().set(REDIS_KEY_PREFIX + userId + ":" + i, photoList.get(i), Duration.ofMinutes(10));
        }

        photoRepository.saveAll(photoList);
    }

    @Override
    public List<Photo> getPhotosByUserId(Long userId) {
        List<Photo> photoList = new ArrayList<>();

        var firstPhoto = redisPhotoTemplate.opsForValue().get(REDIS_KEY_PREFIX + userId + ":" + 0);
        var secondPhoto = redisPhotoTemplate.opsForValue().get(REDIS_KEY_PREFIX + userId + ":" + 1);
        var thirdPhoto = redisPhotoTemplate.opsForValue().get(REDIS_KEY_PREFIX + userId + ":" + 2);

        if (firstPhoto != null && secondPhoto != null && thirdPhoto != null) {
            log.info("Фото из кэша Redis");

            photoList.add(firstPhoto);
            photoList.add(secondPhoto);
            photoList.add(thirdPhoto);

            return photoList;
        }

        return photoRepository.findByUserId(userId);
    }

    @Override
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }
}
