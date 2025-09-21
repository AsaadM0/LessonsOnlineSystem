package com.example.project.controller;
import com.example.project.model.User;
import com.example.project.repositories.UserRepository;
import com.example.project.model.Lesson;
import com.example.project.service.LessonService;
import com.example.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonService lessonService;
    @Autowired
    private NotificationService notificationService;


    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        try {
            if (lesson.getTeacher() != null && lesson.getTeacher().getId() != null) {
                User teacher = userRepository.findById(lesson.getTeacher().getId())
                        .orElseThrow(() -> new RuntimeException("Teacher not found"));
                lesson.setTeacher(teacher);
            } else {
                return ResponseEntity.badRequest().body(null);
            }

            Lesson saved = lessonService.save(lesson);
            notificationService.notifyAllStudents("Zoom Lesson: " + saved.getTitle() + " | " + saved.getZoomLink());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }




    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(lessons);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
