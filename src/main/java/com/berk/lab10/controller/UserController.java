/*
 Bu controller USER rolüne sahip kullanıcılar için örnek bir REST endpoint içerir.

 - /user/ping endpoint’i ile USER yetkisi test edilir
 - Login olmayan kullanıcılar bu endpoint’e erişemez

 Kısaca: USER rolünün çalıştığını göstermek için basit bir test controller’ıdır.
*/
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
