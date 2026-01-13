package com.berk.lab10.dto;

/**
 * Bu DTO, UserService katmanına gelen
 * kullanıcı oluşturma taleplerini temsil eder.
 *
 * Not:
 * - Password zorunlu
 * - Role opsiyonel
 */
public class UserRequest {

    private String username;
    private String email;

    // Parola zorunludur, hash işlemi service katmanında yapılır
    private String password;

    // Rol opsiyoneldir (boş gelirse ROLE_USER atanır)
    private String role;

    public UserRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
