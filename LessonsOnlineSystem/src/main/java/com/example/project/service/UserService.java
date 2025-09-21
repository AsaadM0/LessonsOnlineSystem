package com.example.project.service;

import com.example.project.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerUser(User user);
    User authenticateUser(String email, String password);
}
