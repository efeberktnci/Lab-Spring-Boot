package com.berk.lab10.controller;

import org.springframework.web.bind.annotation.*;
// REST endpoint anotasyonlarını içerir.

@RestController
// Bu controller JSON / text döner (HTML değil)
@RequestMapping("/user")
// Tüm endpoint’ler /user ile başlar
public class UserController {

    @GetMapping("/ping")
    // Kullanıcının USER yetkisine sahip olup olmadığını test etmek için
    public String ping() {
        return "USER OK";
    }
}
