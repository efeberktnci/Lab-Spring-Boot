package com.berk.lab10.controller;

import org.springframework.security.core.Authentication;
// Giriş yapan kullanıcının bilgilerini almak için kullanılır.

import org.springframework.security.core.GrantedAuthority;
// Kullanıcının rollerini kontrol etmek için kullanılır.

import org.springframework.stereotype.Controller;
// MVC controller (HTML döner).

import org.springframework.ui.Model;
// View tarafına veri göndermek için kullanılır.

import org.springframework.web.bind.annotation.GetMapping;
// GET isteklerini karşılamak için kullanılır.

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication auth, Model model) {

        // Kullanıcının admin olup olmadığı kontrol edilir
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));

        // Login olan kullanıcının email bilgisi view’a gönderilir
        model.addAttribute("email", auth != null ? auth.getName() : "");

        // Admin olup olmadığı bilgisi view’a gönderilir
        model.addAttribute("isAdmin", isAdmin);

        return "home";
    }
}
