// 5) src/main/java/com/berk/lab10/controller/UserController.java
package com.berk.lab10.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/ping")
    public String ping() {
        return "USER OK";
    }
}
