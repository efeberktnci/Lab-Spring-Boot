package com.berk.lab10.dto;

/**
 * Bu DTO, yeni bir kullanıcı oluşturulurken
 * client tarafından gönderilen verileri temsil eder.
 *
 * Amaç:
 * - HTTP request body’yi doğrudan Entity’ye bağlamamak
 * - Güvenlik ve katman ayrımı sağlamak
 */
public class CreateUserRequest {

    // Kullanıcının görünen adı
    private String username;

    // Kullanıcının email adresi
    private String email;

    // Düz metin parola (hash işlemi service katmanında yapılır)
    private String password;

    // Rol bilgisi opsiyoneldir (USER / ADMIN)
    private String role;

    // Framework’ler için boş constructor
    public CreateUserRequest() {}

    // Getter & Setter’lar
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
