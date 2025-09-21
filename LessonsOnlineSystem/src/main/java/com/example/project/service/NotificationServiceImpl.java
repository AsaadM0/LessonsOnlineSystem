package com.example.project.service;

import com.example.project.model.Notification;
import com.example.project.model.User;
import com.example.project.repositories.NotificationRepository;
import com.example.project.repositories.UserRepository;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void notifyAllStudents(String message) {
        List<User> students = userRepository.findByRole(User.Role.STUDENT);

        for (User student : students) {
            Notification notif = new Notification();
            notif.setUser(student);
            notif.setMessage(message);
            notif.setTimestamp(LocalDateTime.now());
            notif.setTargetRole(Notification.Role.STUDENT);
            notificationRepository.save(notif);
        }
    }

}

