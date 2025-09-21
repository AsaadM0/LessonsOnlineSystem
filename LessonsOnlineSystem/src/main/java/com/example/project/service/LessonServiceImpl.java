package com.example.project.service;

import com.example.project.cache.CacheAlgo;
import com.example.project.cache.LRUCacheAlgo;
import com.example.project.model.Lesson;
import com.example.project.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    private final CacheAlgo<Long, Lesson> lessonCache = new LRUCacheAlgo<>(10);

    @Override
    public Lesson save(Lesson lesson) {
        Lesson saved = lessonRepository.save(lesson);
        lessonCache.put(saved.getId(), saved);
        return saved;
    }
    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Optional<Lesson> getLessonById(Long id) {
        if (lessonCache.containsKey(id)) {
            System.out.println("Cache hit for lesson " + id);
            return Optional.of(lessonCache.get(id));
        }
        System.out.println("Cache miss, fetching from DB");
        Optional<Lesson> lesson = lessonRepository.findById(id);
        lesson.ifPresent(l -> lessonCache.put(id, l));
        return lesson;
    }


}
