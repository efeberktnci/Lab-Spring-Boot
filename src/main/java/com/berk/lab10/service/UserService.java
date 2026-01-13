package com.berk.lab10.service;

import com.berk.lab10.dto.UserRequest;
// Kullanıcı oluşturma isteğini temsil eder.

import com.berk.lab10.dto.UserResponse;
// Kullanıcı bilgilerini güvenli şekilde döndürmek için kullanılır.

import com.berk.lab10.model.User;
// Veritabanı entity’si.

import com.berk.lab10.repository.UserRepository;
// Veritabanı işlemleri için kullanılır.

import org.springframework.security.crypto.password.PasswordEncoder;
// Parolayı hash’lemek için kullanılır.

import org.springframework.stereotype.Service;
// Bu sınıfın business logic içerdiğini belirtir.

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    // Kullanıcıyı DB’ye kaydetmek ve okumak için

    private final PasswordEncoder passwordEncoder;
    // Parolayı güvenli şekilde encode etmek için

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Tüm kullanıcıları listelemek için kullanılır
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                // Entity → DTO dönüşümü
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRole()
                ))
                .toList();
    }

    // Yeni kullanıcı oluşturma işlemi
    public UserResponse createUser(UserRequest request) {

        // Aynı email varsa kullanıcı oluşturulmaz
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Parola hash’lenerek kaydedilir
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Rol güvenli şekilde belirlenir
        String role = normalizeRole(request.getRole());
        user.setRole(role);

        User saved = userRepository.save(user);

        // Response DTO döndürülür (password yok)
        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole()
        );
    }

    // Role bilgisini güvenli hale getiren yardımcı method
    private String normalizeRole(String role) {

        if (role == null || role.isBlank()) {
            return "ROLE_USER";
        }

        String r = role.trim().toUpperCase();

        if (r.equals("USER")) return "ROLE_USER";
        if (r.equals("ADMIN")) return "ROLE_ADMIN";

        if (r.equals("ROLE_USER")) return "ROLE_USER";
        if (r.equals("ROLE_ADMIN")) return "ROLE_ADMIN";

        // Beklenmeyen her durumda default rol
        return "ROLE_USER";
    }

    // Kullanılmayan eski login mantığı için bırakılmış boş method
    @Deprecated
    public void authenticateDoNotUse() {
        // Login işlemi Spring Security tarafından yapılır
    }
}
