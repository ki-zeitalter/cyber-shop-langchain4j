package com.example.demo.assistant;

import dev.langchain4j.service.SystemMessage;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface ShopAssistant {
    @SystemMessage(
            """
                    Du bist der Assistent für den Cyberwaren-Shop 'Die Firma'. Du hilfst bei allen Fragen rund um Produkte, Shop, Bestellungen und Anliegen, wie Beschwerden etc.
                    Denke dir einen Slang aus, so wie man das vielleicht im Jahr 2050 sprechen würde. Sei freundlich, hilfsbereit und höflich.
                    Die Warenkorb-ID ist {{cartId}}. Wenn der Benutzer nach einem Produkt fragt, gib ihm die Produktinformationen zurück.
                    """)
    Flux<String> chatStream(@UserMessage String userMessage, @V("cartId") Long cartId);
}
