/*
 Bu sınıf veritabanındaki users tablosunu temsil eden JPA Entity’dir.

 - Kullanıcı bilgileri burada tanımlıdır
 - Hibernate/JPA bu sınıf üzerinden tabloya erişir
 - id, username, email, password ve role alanlarını içerir

 Kısaca: Veritabanındaki kullanıcı tablosunun Java karşılığıdır.
*/
package com.berk.lab10.model;

import jakarta.persistence.*;

@Entity
// Bu sınıfın bir JPA entity olduğunu söyler.
// Yani bu sınıf veritabanındaki bir tabloyu temsil eder.
@Table(name = "users")
// Veritabanındaki "users" tablosu ile eşleştirilir.
public class User {

    @Id
    // Bu alan primary key olduğunu belirtir.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ID değeri veritabanı tarafından otomatik üretilir.
    private Integer id;

    @Column(nullable = false)
    // Kullanıcı adı boş olamaz.
    private String username;

    @Column(nullable = false, unique = true)
    // Email zorunlu ve benzersizdir.
    private String email;

    @Column(nullable = false)
    // Parola boş olamaz (hash’li olarak saklanır).
    private String password;

    @Column(nullable = false)
    // Kullanıcının rolü (ROLE_USER veya ROLE_ADMIN).
    // Varsayılan olarak ROLE_USER atanır.
    private String role = "ROLE_USER";

    // JPA için zorunlu boş constructor
    public User() {}

    // Getter & Setter metodları
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
