package com.example.demo.model.deepchat;

import lombok.Data;

@Data
public class DeepChatRequestBody {
  private DeepChatMessageContent[] messages;
  private String model;
  private Boolean stream;


}
