package com.example.demo.model.deepchat;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeepChatErrorResponse {
  private final String error;
}
