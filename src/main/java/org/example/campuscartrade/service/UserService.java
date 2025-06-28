package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // 获取所有用户
    List<User> getAllUsers();

    // 根据ID获取用户
    Optional<User> getUserById(Long id);

    // 根据邮箱获取用户
    Optional<User> getUserByEmail(String email);

    // 创建用户
    User createUser(User user);

    // 更新用户
    Optional<User> updateUser(Long id, User userDetails);

    // 删除用户
    boolean deleteUser(Long id);

    // 更新用户角色
    Optional<User> updateUserRole(Long id, User.Role role);

    // 验证用户登录
    Optional<User> authenticateUser(String email, String passwordHash);
}

