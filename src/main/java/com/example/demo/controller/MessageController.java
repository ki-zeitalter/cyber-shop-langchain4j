package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.deepchat.DeepChatMessageContent;
import com.example.demo.model.deepchat.DeepChatMessageRole;
import com.example.demo.model.deepchat.DeepChatRequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Message;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class MessageController {

    @PostMapping("/chat-stream")
    public Flux<DeepChatMessageContent> chatStreamFlux(@RequestBody DeepChatRequestBody requestBody) {
        String placeholder = "Hallo lieber Kunde... Sie sprechen mit uns außerhalb der Öffnungszeiten. Wir haben von zwölf bis Mittag für Sie auf!";
        return Flux.create(sink -> {
            sink.next(DeepChatMessageContent.builder()
                    .text(placeholder)
                    .role(DeepChatMessageRole.ai)
                    .build());

            sink.complete();
        });
    }
} 
