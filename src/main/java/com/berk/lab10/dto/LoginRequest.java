// 8) src/main/java/com/berk/lab10/dto/LoginRequest.java
package com.berk.lab10.dto;

/**
 * Bu DTO login isteği sırasında client tarafından
 * gönderilen bilgileri temsil eder.
 *
 * Amaç:
 * - Email ve password bilgisini tek bir nesne içinde toplamak
 * - Controller metodunu sade tutmak
 */
public class LoginRequest {

    // Kullanıcının login olurken girdiği email
    private String email;

    // Kullanıcının login olurken girdiği parola
    private String password;

    // Boş constructor (framework gereksinimi)
    public LoginRequest() {}

    // Getter & Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
