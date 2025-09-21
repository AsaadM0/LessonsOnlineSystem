package com.example.project;

import com.example.project.model.Lesson;
import com.example.project.repositories.LessonRepository;
import com.example.project.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LessonServiceImplTest {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void testCacheBehavior() {
        Lesson lesson = new Lesson(null, "Title", "Description", LocalDate.now(), "zoomLink", null);
        Lesson saved = lessonService.save(lesson);
        Optional<Lesson> first = lessonService.getLessonById(saved.getId());
        Optional<Lesson> second = lessonService.getLessonById(saved.getId());
        assertTrue(first.isPresent());
        assertTrue(second.isPresent());
    }
}
