/*
 Bu controller sadece ADMIN yetkisine sahip kullanıcılar için olan sayfaları yönetir.

 - Admin ping endpoint’i ile admin erişimi test edilir
 - Tüm kullanıcıların listelendiği admin sayfasını döner
 - @PreAuthorize ile method seviyesinde yetki kontrolü yapılır

 Kısaca: Admin’e özel işlemler burada toplanır.
*/


package com.berk.lab10.controller;

import com.berk.lab10.service.UserService;
// Admin sayfasında kullanıcı listesini çekmek için servis katmanı kullanılır.

import org.springframework.security.access.prepost.PreAuthorize;
// Method seviyesinde yetkilendirme yapmak için kullanılır.
// Bu anotasyon, method çalışmadan önce rol kontrolü yapar.

import org.springframework.stereotype.Controller;
// MVC controller olduğunu belirtir (HTML döner).

import org.springframework.ui.Model;
// View (HTML) tarafına veri taşımak için kullanılır.

import org.springframework.web.bind.annotation.GetMapping;
// HTTP GET isteklerini karşılamak için kullanılır.

@Controller
public class AdminController {

    private final UserService userService;
    // Admin işlemleri için kullanıcı verilerine ihtiyaç olduğu için servis kullanılır.

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Bu method sadece ROLE_ADMIN olan kullanıcılar tarafından çalıştırılabilir.
    @GetMapping("/admin/ping")
    public String adminPing() {
        // Admin erişiminin çalıştığını göstermek için basit bir sayfa döner.
        return "admin-ping";
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Kullanıcı listesini sadece admin görebilir.
    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        // Tüm kullanıcılar servisten çekilir.
        model.addAttribute("users", userService.getAllUsers());
        // Veriler admin-users.html sayfasına gönderilir.
        return "admin-users";
    }
}
