package com.example.project.service;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    void notifyAllStudents(String message);
}
