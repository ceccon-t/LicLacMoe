package dev.ceccon.tictactoe;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ceccon.client.LLMClient;
import dev.ceccon.client.response.BlockResponse;
import dev.ceccon.conversation.Chat;

import java.io.IOException;

public class LLMPlayer {

    private final String SYSTEM_PROMPT = """
            You are playing a tic-tac-toe match. You are player O.
            You will receive a representation of the current state of the match, based on which you must make a move.
            You must answer in a JSON format, as an object with properties 'row' and 'col', representing the column and row of your move, respectively.
            Row and column both start from 0, so your options for each are 0, 1 or 2.
            Your answer will be part of an automated pipeline that will direct it straight into a JSON converter, so it is ABSOLUTELY ESSENTIAL that you answer only with the JSON representation of your move, as anything other than that (even comments or explanations) will break the pipeline.
            Use only ' or \" as quote characters.
            Try your best to win the game!
        """;

    private LLMClient llmClient;
    private ObjectMapper mapper = new ObjectMapper();

    public LLMPlayer(LLMClient llmClient) {
        this.llmClient = llmClient;
    }

    public Cell getNextMove(Game game) {
        Cell aiMove = new Cell(0, 0);

        boolean validMove = false;
        do {
            String aiResponse;
            try {
                String currentState = game.getBoardStringified();

                Chat chat = new Chat();
                chat.addMessage("system", SYSTEM_PROMPT);
                chat.addMessage("user", "\n" + currentState + "\n");

                BlockResponse blockResponse = llmClient.getNextAIResponse(chat);
                aiResponse = removeThinkingSection(blockResponse.getContent());
                aiMove = mapper.readValue(aiResponse, Cell.class);
                if (game.checkAvailable(aiMove.row(), aiMove.col())) {
                    validMove = true;
                } else {
                    System.out.println("Invalid move returned from AI: " + aiMove);
                }
            } catch (IOException e) {
                System.out.println("Error while trying to get AI Response: " + e.getMessage());
            }

        } while (game.getCurrentPlayer() == Player.O && !validMove);


        return aiMove;
    }

    private String removeThinkingSection(String fullResponse) {
        System.out.println("Full response:\n" + fullResponse);
        String trimmedResponse = fullResponse.split("</think>")[1].trim();
        System.out.println("==========");
        System.out.println("Trimmed response:\n" + trimmedResponse);
        return trimmedResponse;
    }

}
