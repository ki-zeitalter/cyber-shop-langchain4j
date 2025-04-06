package com.example.demo.controller;

import com.example.demo.assistant.ShopAssistant;
import com.example.demo.model.deepchat.DeepChatMessageContent;
import com.example.demo.model.deepchat.DeepChatMessageRole;
import com.example.demo.model.deepchat.DeepChatRequestBody;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final ShopAssistant assistant;

    @PostMapping("/chat-stream")
    public Flux<DeepChatMessageContent> chatStreamFlux(@RequestBody DeepChatRequestBody requestBody) {
        var currentPrompt = requestBody.getMessages()[0].getText();

        return this.assistant.chatStream(currentPrompt, requestBody.getCartId())
                .map(message ->
                        DeepChatMessageContent.builder()
                                .text(message)
                                .role(DeepChatMessageRole.ai)
                                .build());
    }
} 
