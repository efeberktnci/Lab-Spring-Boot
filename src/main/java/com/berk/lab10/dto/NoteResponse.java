package com.berk.lab10.dto;

public class NoteResponse {
    private Integer id;
    private String title;
    private String content;
    private String createdAt;

    public NoteResponse(Integer id, String title, String content, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCreatedAt() { return createdAt; }
}
