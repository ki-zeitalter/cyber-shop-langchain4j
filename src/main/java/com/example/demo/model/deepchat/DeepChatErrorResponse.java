package com.example.demo.model.deepchat;

public class DeepChatErrorResponse {
  private String error;

  public DeepChatErrorResponse(String error) {
    this.error = error;
  }

  public String getError() {
    return this.error;
  }
}
