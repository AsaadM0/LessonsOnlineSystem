package com.example.project.service;

import com.example.project.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface LessonService {
    Lesson save(Lesson lesson);
    List<Lesson> getAllLessons();
    Optional<Lesson> getLessonById(Long id);
}
