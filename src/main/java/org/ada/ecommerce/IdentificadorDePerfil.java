package org.ada.ecommerce;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;

public class IdentificadorDePerfil {

    public static void main(String[] args) {
        var promptSistema = """
                Identifique o perfil de compra de cada cliente.
                
                A resposta deve ser:
                
                Cliente - descreva o perfil do cliente em três palavras
                """;

        var clientes = carregarClientesDoArquivo();

        var request = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(
                        new ChatMessage(
                                ChatMessageRole.SYSTEM.value(),
                                promptSistema),
                        new ChatMessage(
                                ChatMessageRole.SYSTEM.value(),
                                clientes)))
                .build();

        var chave = System.getenv("OPENAI_API_KEY");
        var service = new OpenAiService(chave, Duration.ofSeconds(30));

        System.out.println(
                service
                        .createChatCompletion(request)
                        .getChoices().get(0).getMessage().getContent());
    }

    private static String carregarClientesDoArquivo() {
        try {
            var path = Path.of(ClassLoader
                    .getSystemResource("lista_de_compras_10_clientes.csv")
                    .toURI());
            return Files.readAllLines(path).toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo!", e);
        }
    }

}