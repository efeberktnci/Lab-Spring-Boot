package com.berk.lab10.service;

import com.berk.lab10.model.User;
// Veritabanındaki kullanıcıyı temsil eder.

import com.berk.lab10.repository.UserRepository;
// Kullanıcıyı veritabanından çekmek için kullanılır.

import org.springframework.security.core.authority.SimpleGrantedAuthority;
// Kullanıcının rolünü Spring Security formatına çevirmek için kullanılır.

import org.springframework.security.core.userdetails.*;
// Spring Security’nin UserDetails yapısını içerir.

import org.springframework.stereotype.Service;
// Bu sınıfın service katmanında olduğunu belirtir.

import java.util.List;

@Service
// Spring Security login sırasında bu servisi kullanır.
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    // Kullanıcı bilgilerini veritabanından almak için kullanılır.

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // Email’e göre kullanıcı veritabanından bulunur
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        // Spring Security’nin anlayacağı UserDetails nesnesi oluşturulur
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),           // Kullanıcı adı (email)
                user.getPassword(),        // Hash’li parola
                List.of(
                        new SimpleGrantedAuthority(user.getRole())
                )
                // Kullanıcının rolü (ROLE_USER / ROLE_ADMIN)
        );
    }
}
