package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.repository.UserRepository;
import org.example.campuscartrade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        // 加密密码
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        // 设置默认角色为买家
        if (user.getRole() == null) {
            user.setRole(User.Role.BUYER);
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(userDetails.getName());
            existingUser.setPhone(userDetails.getPhone());
            existingUser.setSchool(userDetails.getSchool());

            // 仅当密码不为空时更新密码
            if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
                existingUser.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
            }

            return userRepository.save(existingUser);
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> updateUserRole(Long id, User.Role role) {
        return userRepository.findById(id).map(user -> {
            user.setRole(role);
            return userRepository.save(user);
        });
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) {
        return userRepository.findByEmail(email).filter(user ->
                passwordEncoder.matches(password, user.getPasswordHash()));
    }
}
