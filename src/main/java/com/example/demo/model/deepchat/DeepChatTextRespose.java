package com.example.demo.model.deepchat;

public class DeepChatTextRespose {
  private String text;

  public DeepChatTextRespose(String text) {
    if (text != null) {
      this.text = text;
    }
  }

  public String getText() {
    return text;
  }
}
