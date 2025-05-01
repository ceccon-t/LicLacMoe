package dev.ceccon.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ceccon.client.dtos.PromptDTO;
import dev.ceccon.client.dtos.ResponseDTO;
import dev.ceccon.client.response.BlockResponse;
import dev.ceccon.config.LLMAPIConfig;
import dev.ceccon.conversation.Chat;

import java.io.BufferedReader;
import java.io.IOException;

public class LLMClient {

    public static final String DEFAULT_BOT_ROLE = "assistant";

    private LLMAPIConfig config;

    public LLMClient(LLMAPIConfig config) {
        this.config = config;
    }

    public BlockResponse getNextAIResponse(Chat chat) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        PromptDTO promptDTO = PromptDTO.build(chat, config);
        promptDTO.setStream(false);
        String body = mapper.writeValueAsString(promptDTO);

        LLMConnection llmConnection = LLMConnection.forUrl(config.getFullUrl());
        llmConnection.send(body);

        String rawResponse = "";

        try (BufferedReader in = llmConnection.getBufferedReader()) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            rawResponse = response.toString();
        }

        llmConnection.close();

        ResponseDTO responseDTO = mapper.readValue(rawResponse, ResponseDTO.class);
        return responseDTO.toBlockResponse();
    }

}