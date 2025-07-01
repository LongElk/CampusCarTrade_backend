package org.example.campuscartrade.controller;

import org.example.campuscartrade.Properties.JwtProperties;
import org.example.campuscartrade.Utill.JwtUtil;
import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProperties jwtProperties;
    // 用户注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        if (userService.getByEmail(email).isPresent()) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 409); // 冲突
            error.put("message", "该邮箱已被注册");
            return ResponseEntity.status(409).body(error);
        }
        String name = (String) payload.get("name");
        String phone = (String) payload.get("phone");
        String password = (String) payload.get("password");
        String school = (String) payload.get("school");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSchool(school);
        user.setRole(User.Role.USER); // 设置默认角色
        user.setPasswordHash(passwordEncoder.encode(password)); // 加密密码

        User saved = userService.register(user);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "注册成功");
        res.put("data", Map.of(
                "userId", saved.getId(),
                "email", saved.getEmail()
        ));
        return ResponseEntity.ok(res);
    }


    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        Optional<User> userOpt = userService.login(email, password);

        Map<String, Object> res = new HashMap<>();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            res.put("code", 200);
            res.put("message", "登录成功");
            // 登录成功后生成 jwt 令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("userRole",user.getRole());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);
            res.put("data", Map.of(
                  "token", token,
                    "user", Map.of(
                            "id", user.getId(),
                            "email", user.getEmail(),
                            "name", user.getName(),
                            "role", user.getRole(),
                            "school", user.getSchool()
                    )
            ));
            return ResponseEntity.ok(res);
        } else {
            res.put("code", 401);
            res.put("message", "账号或密码错误");
            return ResponseEntity.status(401).body(res);
        }
    }

    // 获取用户信息（模拟，无鉴权）
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
        Optional<User> userOpt = userService.getById(id);
        Map<String, Object> res = new HashMap<>();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            res.put("code", 200);
            res.put("message", "获取成功");
            res.put("data", user);
            return ResponseEntity.ok(res);
        } else {
            res.put("code", 404);
            res.put("message", "用户不存在");
            return ResponseEntity.status(404).body(res);
        }
    }
}

