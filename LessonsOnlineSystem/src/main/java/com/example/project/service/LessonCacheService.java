package com.example.project.service;

import com.example.project.cache.LRUCacheAlgo;
import com.example.project.model.Lesson;
import com.example.project.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LessonCacheService {

    private final LRUCacheAlgo<Long, Lesson> cache = new LRUCacheAlgo<>(5);

    @Autowired
    private LessonRepository lessonRepository;

    public Lesson getLesson(Long id) {
        Lesson lesson = cache.get(id);
        if (lesson != null) {
            System.out.println("Cache Hit for Lesson ID: " + id);
            return lesson;
        }
        System.out.println("Cache Miss for Lesson ID: " + id + " - Querying DB");
        lesson = lessonRepository.findById(id).orElse(null);
        if (lesson != null) {
            cache.put(id, lesson);
            System.out.println("Lesson ID " + id + " saved to cache");
        }
        return lesson;
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll(); 
    }

    public Lesson saveLesson(Lesson lesson) {
        Lesson saved = lessonRepository.save(lesson);
        cache.put(saved.getId(), saved);
        return saved;
    }
}
