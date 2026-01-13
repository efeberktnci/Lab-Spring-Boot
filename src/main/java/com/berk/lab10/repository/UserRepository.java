package com.berk.lab10.repository;

import com.berk.lab10.model.User;
// Repository, User entity’si üzerinde çalışır.

import org.springframework.data.jpa.repository.JpaRepository;
// JPA’nın sunduğu hazır CRUD metodlarını sağlar.

import java.util.Optional;
// Null kontrolünü daha güvenli yapmak için kullanılır.

public interface UserRepository extends JpaRepository<User, Integer> {
    // User entity’si ve primary key tipi Integer olarak belirtilir.

    // Verilen email veritabanında var mı diye kontrol eder
    boolean existsByEmail(String email);

    // Email’e göre kullanıcıyı bulur
    // Bulunamazsa Optional boş döner
    Optional<User> findByEmail(String email);
}
