package com.berk.lab10.service;

import com.berk.lab10.dto.UserRequest;
import com.berk.lab10.dto.UserResponse;
import com.berk.lab10.model.User;
import com.berk.lab10.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole()))
                .toList();
    }

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // ✅ Lab 11: password HASH'li saklanmalı
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ Lab 11: role sadece ROLE_USER / ROLE_ADMIN olmalı
        String role = normalizeRole(request.getRole());
        user.setRole(role);

        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole());
    }

    // 🔒 Güvenli rol normalize
    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) return "ROLE_USER";

        String r = role.trim().toUpperCase();

        // Eğer USER / ADMIN gelirse ROLE_ ekle
        if (r.equals("USER")) return "ROLE_USER";
        if (r.equals("ADMIN")) return "ROLE_ADMIN";

        // ROLE_USER / ROLE_ADMIN gelirse kabul et
        if (r.equals("ROLE_USER")) return "ROLE_USER";
        if (r.equals("ROLE_ADMIN")) return "ROLE_ADMIN";

        // başka her şey -> default
        return "ROLE_USER";
    }

    /**
     * ❌ Lab 11'de login işini Spring Security yaptığı için
     * bu method'a ihtiyacın yok.
     * Eğer başka controller kullanıyorsa, tamamen sil veya çağrılmasın.
     */
    @Deprecated
    public void authenticateDoNotUse() {
        // Spring Security formLogin kullanılıyor.
    }
}
