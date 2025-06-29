package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> login(String email, String password);
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
}

