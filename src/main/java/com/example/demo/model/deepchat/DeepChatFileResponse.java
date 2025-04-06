package com.example.demo.model.deepchat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DeepChatFileResponse {
    private final @NotNull List<DeepChatFile> files;
}
