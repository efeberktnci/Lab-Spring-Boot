// 6) src/main/java/com/berk/lab10/dto/AuthResponse.java
package com.berk.lab10.dto;

public class AuthResponse {
    private String message;

    public AuthResponse() {}

    public AuthResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
