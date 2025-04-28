package dev.ceccon.tictactoe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ceccon.config.LLMAPIConfig;

public class LLMPlayer {

    private LLMAPIConfig apiConfig;

    public LLMPlayer(LLMAPIConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public Cell getNextMove(Player[][] cells) {
        ObjectMapper mapper = new ObjectMapper();
        String response = "{\"col\": 2, \"row\": 1}";
        Cell cell = new Cell(0, 0);
        try {
            cell = mapper.readValue(response, Cell.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return cell;
    }

}
