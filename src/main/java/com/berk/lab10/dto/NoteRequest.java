package com.berk.lab10.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NoteRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title max 100 chars")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content max 2000 chars")
    private String content;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
