package com.berk.lab10.controller;

import com.berk.lab10.dto.AuthResponse;
import com.berk.lab10.dto.LoginRequest;
import com.berk.lab10.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.authenticate(request.getEmail(), request.getPassword());
    }
}
