package com.berk.lab10.dto;

public class AuthResponse {

    private boolean success;
    private String message;
    private Integer userId;
    private String email;

    public AuthResponse() {}

    public AuthResponse(boolean success, String message, Integer userId, String email) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.email = email;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
