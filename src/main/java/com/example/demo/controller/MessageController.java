package com.example.demo.controller;

import com.example.demo.model.deepchat.DeepChatMessageContent;
import com.example.demo.model.deepchat.DeepChatMessageRole;
import com.example.demo.model.deepchat.DeepChatRequestBody;
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


    @PostMapping("/chat-stream")
    public Flux<DeepChatMessageContent> chatStreamFlux(@RequestBody DeepChatRequestBody requestBody) {
        var currentPrompt = requestBody.getMessages()[0].getText();

        String response = "Leider erreichst du uns außerhalb unserer Öffnungszeiten. Wir sind von 12 bis Mittag für dich da. " +
                "Wir freuen uns auf deinen Besuch!";

        return Flux.create(sink -> {
            sink.next(DeepChatMessageContent.builder().role(DeepChatMessageRole.ai).text(response).build());
            sink.complete();
        });
    }
} 
