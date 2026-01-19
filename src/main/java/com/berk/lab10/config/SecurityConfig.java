/*
 Bu sınıf uygulamanın güvenlik ayarlarını içerir.

 - Hangi URL’lerin login gerektirdiğini belirler
 - USER / ADMIN rol yetkilerini tanımlar
 - Login ve logout işlemlerini Spring Security ile yönetir
 - CSRF korumasını aktif eder
 - Şifrelerin BCrypt ile hash’lenmesini sağlar
 - Yetkisiz erişimde redirect YAPMAZ, direkt 401 / 403 döner
 - @PreAuthorize gibi method-level güvenliği aktif eder

 Kısaca: Uygulamanın kapısı, kilidi ve anahtarı buradadır.
*/

package com.berk.lab10.config;

import org.springframework.context.annotation.Bean;
// Spring’e bu metodun bir Bean ürettiğini söyler.
// Yani bu nesne Spring tarafından yönetilir ve gerektiğinde otomatik kullanılır.

import org.springframework.context.annotation.Configuration;
// Bu sınıfın bir konfigürasyon (ayar) sınıfı olduğunu belirtir.
// Spring uygulama başlarken bu sınıfı okur ve içindeki ayarları uygular.

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// @PreAuthorize / method-level security için gerekli

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// HTTP seviyesinde güvenlik ayarlarını yapmak için kullanılır.
// Hangi URL korunacak, login gerekli mi, CSRF açık mı gibi kurallar buradan belirlenir.

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Şifreleri güvenli şekilde hash’lemek için kullanılır.

import org.springframework.security.crypto.password.PasswordEncoder;
// Şifre encode işlemleri için kullanılan arayüzdür.

import org.springframework.security.web.SecurityFilterChain;
// Spring Security’nin kalbidir.
// Uygulamaya gelen her HTTP isteği bu filtre zincirinden geçer.

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// CSRF token’ını cookie içinde saklamak için kullanılır.
// Form gönderimlerinde sahte istekleri engeller.

import org.springframework.security.web.authentication.HttpStatusEntryPoint;
// Login yoksa redirect yerine doğrudan HTTP status dönmek için

import org.springframework.http.HttpStatus;
// 401 / 403 gibi HTTP kodları

@EnableMethodSecurity
// @PreAuthorize("hasRole('ADMIN')") gibi anotasyonların çalışmasını sağlar

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
                // CSRF koruması açık
                // Token cookie içinde tutulur ve her form isteğinde kontrol edilir
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

                // ❗ ÖNEMLİ KISIM
                // Default davranış: yetkisiz kullanıcı login sayfasına redirect edilir
                // İstenen davranış: redirect YOK, ekranda 401 / 403 göster
                .exceptionHandling(ex -> ex

                        // Login YOK → 401 Unauthorized
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )

                        // Login VAR ama yetki YOK → 403 Forbidden
                        .accessDeniedHandler((request, response, ex2) ->
                                response.sendError(HttpStatus.FORBIDDEN.value())
                        )
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
