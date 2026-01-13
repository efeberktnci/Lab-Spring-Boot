package com.berk.lab10;
// Projenin ana paketi.
// Spring Boot, component taramasına bu paketten başlar.

import org.springframework.boot.SpringApplication;
// Spring Boot uygulamasını başlatmak için kullanılan sınıf.

import org.springframework.boot.autoconfigure.SpringBootApplication;
// Bu annotation Spring Boot'un otomatik konfigürasyon yapmasını sağlar.
// Aynı anda @Configuration, @EnableAutoConfiguration ve @ComponentScan içerir.

@SpringBootApplication
// Bu sınıfın Spring Boot uygulamasının başlangıç noktası olduğunu belirtir.
public class Lab10Application {

	public static void main(String[] args) {
		// Java programının çalışmaya başladığı ana metod.
		SpringApplication.run(Lab10Application.class, args);
		// Spring context'i başlatır.
		// Embedded Tomcat server ayağa kalkar (localhost:8080).
		// Controller, Service, Repository bean'leri yüklenir.
	}
}
