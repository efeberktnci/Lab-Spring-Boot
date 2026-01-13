package com.berk.lab10.config;

import org.springframework.context.annotation.Bean;
// Spring’e bu metodun bir Bean ürettiğini söyler.
// Yani bu nesne Spring tarafından yönetilir ve gerektiğinde otomatik kullanılır.

import org.springframework.context.annotation.Configuration;
// Bu sınıfın bir konfigürasyon (ayar) sınıfı olduğunu belirtir.
// Spring uygulama başlarken bu sınıfı okur ve içindeki ayarları uygular.

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// HTTP seviyesinde güvenlik ayarlarını yapmak için kullanılır.
// Hangi URL korunacak, login gerekli mi, CSRF açık mı gibi kurallar buradan belirlenir.

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Şifreleri güvenli şekilde hash’lemek için kullanılır.
// Şifreler düz metin olarak saklanmaz, geri çözülemez şekilde şifrelenir.

import org.springframework.security.crypto.password.PasswordEncoder;
// Şifre encode işlemleri için kullanılan arayüzdür.
// Uygulama BCrypt kullanıyor ama bu interface sayesinde esneklik sağlanır.

import org.springframework.security.web.SecurityFilterChain;
// Spring Security’nin kalbidir.
// Uygulamaya gelen her HTTP isteği bu filtre zincirinden geçer.

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// CSRF token’ını cookie içinde saklamak için kullanılır.
// Form gönderimlerinde sahte istekleri engeller.

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Kullanıcı şifrelerinin güvenli şekilde hash’lenmesini sağlar
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // CSRF koruması açık.
                // Token cookie içinde tutulur ve her form isteğinde kontrol edilir.
                .csrf(csrf -> csrf
                        .csrfTokenRepository(
                                CookieCsrfTokenRepository.withHttpOnlyFalse()
                        )
                )

                // URL bazlı yetkilendirme kuralları
                .authorizeHttpRequests(auth -> auth

                        // Login olmadan erişilebilen sayfalar
                        .requestMatchers("/login", "/register", "/css/**", "/js/**")
                        .permitAll()

                        // Sadece ADMIN rolüne sahip kullanıcılar erişebilir
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // USER veya ADMIN erişebilir
                        .requestMatchers("/user/**")
                        .hasAnyRole("USER", "ADMIN")

                        // Diğer tüm endpoint’ler login gerektirir
                        .anyRequest()
                        .authenticated()
                )

                // Session tabanlı form login
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // Logout işlemi
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                        .permitAll()
                );

        return http.build();
    }
}
