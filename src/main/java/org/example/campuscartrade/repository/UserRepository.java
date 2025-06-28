package org.example.campuscartrade.repository;


import org.example.campuscartrade.pojo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * 用户表 Repository：支持通过邮箱、姓名等字段查询
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findBySchool(String school);

    List<User> findByRole(User.Role role);
}
