package com.example.project.repositories;

import com.example.project.model.Notification;
import com.example.project.model.Notification.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);



}
