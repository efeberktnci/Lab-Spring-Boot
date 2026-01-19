/*
 Bu controller login ve register (kayıt) işlemlerini yönetir.
 - Login sayfasını gösterir
 - Register sayfasını gösterir
 - Yeni kullanıcı kaydı oluşturur
 - Admin kaydı için secret key kontrolü yapar
 - Şifreleri hash’leyerek veritabanına kaydeder
 - Strong password policy uygular

 Kısaca: Kullanıcı sisteme buradan girer veya kayıt olur.
*/

package com.berk.lab10.controller;

import com.berk.lab10.model.User;
import com.berk.lab10.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_REGISTER_SECRET:}")
    private String adminRegisterSecret;

    // ✅ Strong password policy:
    // - min 8
    // - at least 1 uppercase, 1 lowercase, 1 digit, 1 special char
    private static final Pattern STRONG_PASSWORD = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$"
    );

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
        // Aynı email varsa kayıt engellenir
        if (userRepository.existsByEmail(email)) {
            return "redirect:/register?error=exists";
        }

        // ✅ Password policy check
        if (!STRONG_PASSWORD.matcher(password).matches()) {
            // register.html tarafında göstermek için param geçiyoruz
            return "redirect:/register?error=weak";
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

        return "redirect:/login";
    }
}
