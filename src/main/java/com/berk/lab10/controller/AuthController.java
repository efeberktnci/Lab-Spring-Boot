package com.berk.lab10.controller;

import com.berk.lab10.model.User;
// Kullanıcı entity’si, DB’ye kaydedilecek veri modelidir.

import com.berk.lab10.repository.UserRepository;
// Kullanıcıyı veritabanına kaydetmek ve kontrol etmek için kullanılır.

import org.springframework.beans.factory.annotation.Value;
// application.properties veya .env içindeki değerleri okumak için kullanılır.

import org.springframework.security.crypto.password.PasswordEncoder;
// Şifrelerin hash’lenmesi için kullanılır.

import org.springframework.stereotype.Controller;
// MVC controller (HTML sayfaları döner).

import org.springframework.web.bind.annotation.*;
// Request mapping anotasyonlarını içerir.

@Controller
public class AuthController {

    private final UserRepository userRepository;
    // Register sırasında kullanıcıyı DB’ye kaydetmek için kullanılır.

    private final PasswordEncoder passwordEncoder;
    // Şifreleri güvenli şekilde hash’lemek için kullanılır.

    @Value("${ADMIN_REGISTER_SECRET:}")
    // Admin kayıt olmak için gereken gizli anahtar.
    // .env veya application.properties içinden okunur.
    private String adminRegisterSecret;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(org.springframework.security.core.Authentication auth) {
        // Eğer kullanıcı zaten login olmuşsa tekrar login sayfasına gitmesin
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(org.springframework.security.core.Authentication auth) {
        // Login olmuş kullanıcı register sayfasına erişemez
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
        // Aynı email varsa kayıt engellenir
        if (userRepository.existsByEmail(email)) {
            return "redirect:/register?error=exists";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        // Şifre hash’lenerek kaydedilir
        user.setPassword(passwordEncoder.encode(password));

        // Varsayılan rol USER
        String finalRole = "ROLE_USER";

        // Admin rolü sadece doğru secret girilirse verilir
        if (role != null
                && role.equalsIgnoreCase("ROLE_ADMIN")
                && adminRegisterSecret != null
                && !adminRegisterSecret.isBlank()
                && adminRegisterSecret.equals(adminSecret)) {
            finalRole = "ROLE_ADMIN";
        }

        user.setRole(finalRole);
        userRepository.save(user);

        // Kayıttan sonra login sayfasına yönlendirilir
        return "redirect:/login";
    }
}
