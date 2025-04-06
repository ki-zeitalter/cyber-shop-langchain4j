package com.example.demo.model.deepchat;

import lombok.Data;

import java.util.List;

@Data
public class DeepChatMessageContent {
    private DeepChatMessageRole role;
    private String text;
    private String html;
    private List<DeepChatFile>files;
}
