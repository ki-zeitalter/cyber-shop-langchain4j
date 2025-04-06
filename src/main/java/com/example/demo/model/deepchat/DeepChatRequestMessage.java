package com.example.demo.model.deepchat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeepChatRequestMessage {
    private final String role;
    private final String text;


}
