package org.ada.ecommerce;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Arrays;

public class CategorizadorDeProdutos {

    public static void main(String[] args) {

        var user = "Escova de dentes";

        var system =
        """
        Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado
    
        Escolha uma categoria dentro a lista abaixo:
            1. Higiene pessoal
            2. Eletronicos
            3. Esportes
            4. Outros
        """;

        var service = new OpenAiService("API-KEY", Duration.ofSeconds(30));

        var completionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-4")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
                ))
                .n(2)// numero de respostas que a api retorna
                .build();
        service
                .createChatCompletion(completionRequest)
                .getChoices()
                .forEach(c -> {
                    System.out.print(c.getMessage().getContent());
                    System.out.println("\n------------------------\n");
                });
    }
}