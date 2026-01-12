md# 🔐 Security of Web Applications – Project

> A comprehensive Spring Boot application implementing **authentication**, **authorization**, and **secure access control** for the Security of Web Applications course.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Authentication & Authorization](#authentication--authorization)
- [Testing Guide](#testing-guide)
- [Lab Status](#lab-status)
- [Security Features](#security-features)
- [Environment Variables](#environment-variables)

---

## 🎯 Overview

This project demonstrates secure web application development practices, focusing on:

- **HTTP fundamentals** and request-response flow
- **Session-based authentication** with Spring Security
- **Role-based access control** (RBAC)
- **Password security** with BCrypt hashing
- **CSRF protection** for form submissions
- **Secure database layer** with Flyway migrations

---

## ✨ Features

### 🔑 Authentication
- ✅ User registration with email validation
- ✅ Secure login with BCrypt password hashing
- ✅ Session-based authentication (no JWT)
- ✅ CSRF protection enabled
- ✅ Session invalidation on logout

### 🛡️ Authorization
- ✅ Role-based access control (USER, ADMIN)
- ✅ Protected endpoints with Spring Security
- ✅ Admin registration requires secret key
- ✅ Forbidden (403) responses for unauthorized access

### 📊 Database
- ✅ SQLite database with Flyway migrations
- ✅ User entity with username, email, password, role
- ✅ Spring Data JPA for database operations

---

## 🛠️ Technologies

| Category | Technology |
|----------|-----------|
| **Language** | Java 22 |
| **Framework** | Spring Boot 3.5.x |
| **Security** | Spring Security (Session-based) |
| **Database** | SQLite + Spring Data JPA |
| **Migrations** | Flyway |
| **Template Engine** | Thymeleaf |
| **Build Tool** | Maven |

---

## 🚀 Getting Started

### Prerequisites

- Java 22 or higher
- Maven 3.6+

### Installation

#### 1. Clone the repository
```bash
git clone 
cd lab10
```

#### 2. Create `.env` file in the project root
```properties
DB_URL=jdbc:sqlite:database.db
DB_DRIVER=org.sqlite.JDBC
HIBERNATE_DIALECT=org.hibernate.community.dialect.SQLiteDialect
ADMIN_REGISTER_SECRET=CHANGE_ME_123
```

#### 3. Run the application
```bash
mvn spring-boot:run
```

Or using Maven wrapper:
```bash
./mvnw spring-boot:run
```

#### 4. Open in browser
```
http://localhost:8080
```

---

## 📁 Project Structure
```
src/main/java/com/berk/lab10
├── config/
│   └── SecurityConfig.java           # Spring Security configuration
├── controller/
│   ├── AuthController.java           # Login/register endpoints
│   ├── HomeController.java           # Public pages
│   └── UserController.java           # User management (ADMIN only)
├── dto/
│   ├── LoginRequest.java             # Login form data
│   ├── AuthResponse.java             # Login response
│   ├── UserRequest.java              # User creation data
│   └── UserResponse.java             # User data (no password)
├── model/
│   └── User.java                     # User entity
├── repository/
│   └── UserRepository.java           # JPA repository
└── service/
    ├── UserService.java              # Business logic
    └── CustomUserDetailsService.java # Spring Security integration

src/main/resources
├── db/migration/
│   ├── V1__create_users.sql         # Initial users table
│   └── V2__add_role_to_users.sql    # Add role column
├── application.properties            # App configuration
└── .env                              # Environment variables (gitignored)
```

---

## 🔐 Authentication & Authorization

### Session-Based Authentication

This project uses **Spring Security's session-based authentication**:

- Sessions stored server-side
- CSRF tokens for form protection
- Secure cookies (`JSESSIONID`)
- No JWT tokens (as per Lab 11 requirements)

### Access Rules

| Path | Access |
|------|--------|
| `/`, `/login`, `/register` | 🌐 Public |
| `/user/**` | 👤 ROLE_USER or ROLE_ADMIN |
| `/admin/**` | 👑 ROLE_ADMIN only |
| All other endpoints | 🔒 Authenticated users |

### User Roles

- **ROLE_USER** (default): Standard user access
- **ROLE_ADMIN**: Administrative privileges

### Admin Registration

To register as admin, provide the following in your request:
```json
{
  "username": "admin",
  "email": "admin@example.com",
  "password": "securePassword123",
  "role": "ADMIN",
  "adminSecret": "CHANGE_ME_123"
}
```

> ⚠️ The `adminSecret` must match the `ADMIN_REGISTER_SECRET` in `.env`

---

## 🧪 Testing Guide

### 1. User Registration

**Request:**
```http
POST http://localhost:8080/register
Content-Type: application/json
```
```json
{
  "username": "john",
  "email": "john@test.com",
  "password": "1234",
  "role": "USER"
}
```

**Expected:** User created with `ROLE_USER`

---

### 2. Admin Registration

**Request:**
```http
POST http://localhost:8080/register
Content-Type: application/json
```
```json
{
  "username": "admin",
  "email": "admin@test.com",
  "password": "admin123",
  "role": "ADMIN",
  "adminSecret": "CHANGE_ME_123"
}
```

**Expected:** User created with `ROLE_ADMIN`

---

### 3. Login

**Request:**
```http
POST http://localhost:8080/auth/login
Content-Type: application/json
```
```json
{
  "email": "john@test.com",
  "password": "1234"
}
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "userId": 1,
  "email": "john@test.com"
}
```

- ✅ Session created (`JSESSIONID` cookie)
- ✅ Success response

---

### 4. Access Protected Endpoint (USER)

**Request:**
```http
GET http://localhost:8080/user/profile
Cookie: JSESSIONID=
```

**Expected:**
- ✅ `200 OK` (if logged in as USER or ADMIN)
- ❌ `401 Unauthorized` (if not logged in)

---

### 5. Access Admin Endpoint

**Request:**
```http
GET http://localhost:8080/admin/users
Cookie: JSESSIONID=
```

**Expected:**
- ✅ `200 OK` (if logged in as ADMIN)
- ❌ `403 Forbidden` (if logged in as USER)
- ❌ `401 Unauthorized` (if not logged in)

---

### 6. Logout

**Request:**
```http
POST http://localhost:8080/logout
Cookie: JSESSIONID=
```

**Expected:** Session invalidated

---

## 📊 Lab Status

| Lab | Status | Description |
|-----|--------|-------------|
| **Lab 10** | ✅ Completed | HTTP basics, layered architecture, database setup |
| **Lab 11 (Session)** | ✅ Completed | Session-based authentication, role-based access |
| **Lab 11 (JWT)** | ❌ Not Implemented | Token-based authentication (REST track) |
| **Lab 12** | 🔄 In Progress | Secure CRUD operations, user-owned data |

---

## 🔒 Security Features

- ✅ **Password Hashing**: BCrypt with strength 10
- ✅ **CSRF Protection**: Enabled for all state-changing operations
- ✅ **Session Management**: Secure session configuration
- ✅ **SQL Injection Prevention**: Parameterized queries via JPA
- ✅ **Role-Based Access**: Spring Security method-level security
- ✅ **Input Validation**: Jakarta Validation annotations

---

## 📝 Environment Variables

Create a `.env` file with:
```properties
DB_URL=jdbc:sqlite:database.db
DB_DRIVER=org.sqlite.JDBC
HIBERNATE_DIALECT=org.hibernate.community.dialect.SQLiteDialect
ADMIN_REGISTER_SECRET=CHANGE_ME_123
```

> ⚠️ **Never commit `.env` to version control!**

---

## 🤝 Contributing

This is a course project. Contributions are not accepted.

---

## 📄 License

This project is developed for educational purposes.

---

## 👨‍💻 Author

**Berk Efendioğlu**  
Security of Web Applications Course  
2025

---

<div align="center">

**Built with ❤️ using Spring Boot**

[⬆ Back to top](#-security-of-web-applications--project)

</div>