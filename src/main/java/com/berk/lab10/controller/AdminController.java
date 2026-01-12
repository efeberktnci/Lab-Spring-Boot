// src/main/java/com/berk/lab10/controller/AdminController.java
package com.berk.lab10.controller;

import com.berk.lab10.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/ping")
    public String adminPing() {
        return "admin-ping";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-users";
    }
}
