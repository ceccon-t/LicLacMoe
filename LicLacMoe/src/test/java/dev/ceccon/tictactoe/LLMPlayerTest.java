package dev.ceccon.tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LLMPlayerTest {

    @Test
    void getMoveReturnsCell() {
        Cell expected = new Cell(1, 2);

        LLMPlayer player = new LLMPlayer(null);
        Cell actual = player.getNextMove(null);

        assertEquals(expected, actual);
    }

}