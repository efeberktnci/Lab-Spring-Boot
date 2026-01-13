// 6) src/main/java/com/berk/lab10/dto/AuthResponse.java
package com.berk.lab10.dto;

/**
 * Bu sınıf authentication (login / register) işlemlerinden sonra
 * kullanıcıya döndürülecek cevabı temsil eder.
 *
 * Amaç:
 * - Controller → Service → Response zincirini temiz tutmak
 * - String mesajı doğrudan controller’dan dönmek yerine
 *   standart bir response yapısı kullanmak
 */
public class AuthResponse {

    // Kullanıcıya gösterilecek mesaj (ör: "Login successful", "Invalid credentials")
    private String message;

    // Boş constructor
    // Spring ve Jackson gibi framework’ler nesne oluştururken buna ihtiyaç duyar
    public AuthResponse() {}

    // Mesajı direkt set etmek için kullanılan constructor
    public AuthResponse(String message) {
        this.message = message;
    }

    // Getter
    public String getMessage() { return message; }

    // Setter
    public void setMessage(String message) { this.message = message; }
}
