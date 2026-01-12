package com.berk.lab10.service;

import com.berk.lab10.dto.AuthResponse;
import com.berk.lab10.dto.UserRequest;
import com.berk.lab10.dto.UserResponse;
import com.berk.lab10.model.User;
import com.berk.lab10.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail()))
                .toList();
    }

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // şimdilik plain

        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    // ✅ LOGIN / AUTHENTICATE
    public AuthResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return new AuthResponse(false, "Invalid credentials", null, null);
        }

        if (!user.getPassword().equals(password)) {
            return new AuthResponse(false, "Invalid credentials", null, null);
        }

        return new AuthResponse(true, "Login successful", user.getId(), user.getEmail());
    }

}
