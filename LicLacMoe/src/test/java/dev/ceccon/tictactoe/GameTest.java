package dev.ceccon.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testAvailableCellReturnsTrue() {
        assertTrue(game.checkAvailable(0, 0));
        assertTrue(game.checkAvailable(2, 2));
    }

    @Test
    public void testUnavailableCellReturnsFalse() {
        // Pretend a move has been made
        game.makeMove(1, 1);  // Assuming this method exists or will exist
        assertFalse(game.checkAvailable(1, 1));
    }

    @Test
    public void testOutOfBoundsReturnsFalse() {
        assertFalse(game.checkAvailable(-1, 0));
        assertFalse(game.checkAvailable(0, -1));
        assertFalse(game.checkAvailable(3, 0));
        assertFalse(game.checkAvailable(0, 3));
    }

    @Test
    public void testAllCellsInitiallyAvailable() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertTrue(game.checkAvailable(row, col));
            }
        }
    }

    @Test
    public void testMakeValidMoveReturnsTrueAndSwitchesPlayer() {
        Player startingPlayer = game.getCurrentPlayer();
        assertTrue(game.makeMove(0, 0));
        assertFalse(game.checkAvailable(0, 0));  // Cell should now be taken
        assertNotEquals(startingPlayer, game.getCurrentPlayer());  // Player should switch
    }

    @Test
    public void testMakeMoveOnOccupiedCellReturnsFalseAndDoesNotSwitchPlayer() {
        game.makeMove(1, 1);  // Valid move
        Player afterFirstMove = game.getCurrentPlayer();
        assertFalse(game.makeMove(1, 1));  // Try to overwrite
        assertEquals(afterFirstMove, game.getCurrentPlayer());  // No switch
    }

    @Test
    public void testMakeMoveOutOfBoundsReturnsFalseAndDoesNotSwitchPlayer() {
        Player current = game.getCurrentPlayer();
        assertFalse(game.makeMove(-1, 0));
        assertFalse(game.makeMove(0, 3));
        assertEquals(current, game.getCurrentPlayer());  // Still same player
    }
}