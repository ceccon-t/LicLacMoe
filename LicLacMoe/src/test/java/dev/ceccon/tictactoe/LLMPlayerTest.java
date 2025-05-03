package dev.ceccon.tictactoe;

import dev.ceccon.client.LLMClient;
import dev.ceccon.client.response.BlockResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LLMPlayerTest {

    @Test
    void getMoveReturnsCell() throws IOException {
        Cell expected = new Cell(1, 2);

        LLMClient mockedLlmClient = mock(LLMClient.class);
        Game mockedGame = mock(Game.class);
        BlockResponse mockedResponse = mock(BlockResponse.class);

        when(mockedResponse.getContent()).thenReturn("{\"row\":1,\"col\":2}");
        when(mockedLlmClient.getNextAIResponse(any())).thenReturn(mockedResponse);
        when(mockedGame.checkAvailable(1, 2)).thenReturn(true);

        LLMPlayer player = new LLMPlayer(mockedLlmClient);
        Cell actual = player.getNextMove(mockedGame);

        assertEquals(expected, actual);
    }

}