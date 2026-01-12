package com.berk.lab10.controller;

import com.berk.lab10.model.User;
import com.berk.lab10.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_REGISTER_SECRET:}")
    private String adminRegisterSecret;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(org.springframework.security.core.Authentication auth) {
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "login";
    }


    @GetMapping("/register")
    public String registerPage(org.springframework.security.core.Authentication auth) {
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "register";
    }


    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String adminSecret
    ) {
        if (userRepository.existsByEmail(email)) {
            return "redirect:/register?error=exists";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        String finalRole = "ROLE_USER";

        if (role != null
                && role.equalsIgnoreCase("ROLE_ADMIN")
                && adminRegisterSecret != null
                && !adminRegisterSecret.isBlank()
                && adminRegisterSecret.equals(adminSecret)) {
            finalRole = "ROLE_ADMIN";
        }

        user.setRole(finalRole);
        userRepository.save(user);

        return "redirect:/login";
    }
}
