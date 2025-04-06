package com.example.demo.model.deepchat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeepChatTextRespose {
    private final @NotNull String text;


}
