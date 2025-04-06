package com.example.demo.model;

import java.time.LocalDateTime;

public class Message {
    private String content;
    private LocalDateTime timestamp;

    // Konstruktoren
    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getter und Setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 