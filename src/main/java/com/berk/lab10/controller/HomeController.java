// src/main/java/com/berk/lab10/controller/HomeController.java
package com.berk.lab10.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication auth, Model model) {
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));

        model.addAttribute("email", auth != null ? auth.getName() : "");
        model.addAttribute("isAdmin", isAdmin);

        return "home";
    }
}
