/**
 * Bu DTO, kullanıcı bilgilerini client’a dönerken kullanılır.
 *
 * Önemli:
 * - Password burada YOK
 * - Güvenlik için hassas alanlar response’a eklenmez
 */
package com.berk.lab10.dto;

public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String role;

    public UserResponse(Integer id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}

