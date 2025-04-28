package dev.ceccon.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        LLMPlayer llmPlayer = mock(LLMPlayer.class);
        game = new Game(llmPlayer);
    }

    @Test
    public void testAvailableCellReturnsTrue() {
        assertTrue(game.checkAvailable(0, 0));
        assertTrue(game.checkAvailable(2, 2));
    }

    @Test
    public void testUnavailableCellReturnsFalse() {
        // Pretend a move has been made
        game.makeMove(1, 1, Player.X);
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
        assertTrue(game.makeMove(0, 0, startingPlayer));
        assertFalse(game.checkAvailable(0, 0));  // Cell should now be taken
        assertNotEquals(startingPlayer, game.getCurrentPlayer());  // Player should switch
    }

    @Test
    public void testMakeMoveOnOccupiedCellReturnsFalseAndDoesNotSwitchPlayer() {
        game.makeMove(1, 1, Player.X);  // Valid move
        Player afterFirstMove = game.getCurrentPlayer();
        assertFalse(game.makeMove(1, 1, afterFirstMove));  // Try to overwrite
        assertEquals(afterFirstMove, game.getCurrentPlayer());  // No switch
    }

    @Test
    public void testMakeMoveOutOfBoundsReturnsFalseAndDoesNotSwitchPlayer() {
        Player current = game.getCurrentPlayer();
        assertFalse(game.makeMove(-1, 0, current));
        assertFalse(game.makeMove(0, 3, current));
        assertEquals(current, game.getCurrentPlayer());  // Still same player
    }

    @Test
    public void testGameStatusWhenXWinsHorizontally() {
        Player[][] board = {
                {Player.X, Player.X, Player.X},
                {Player.O, Player.NONE, Player.O},
                {Player.NONE, Player.NONE, Player.NONE}
        };
        game.setCells(board);
        game.evaluateStatus();
        assertEquals(GameStatus.X_WON, game.getStatus());
    }

    @Test
    public void testGameStatusWhenOWinsVertically() {
        Player[][] board = {
                {Player.O, Player.X, Player.NONE},
                {Player.O, Player.X, Player.NONE},
                {Player.O, Player.NONE, Player.X}
        };
        game.setCells(board);
        game.evaluateStatus();
        assertEquals(GameStatus.O_WON, game.getStatus());
    }

    @Test
    public void testGameStatusWhenXWinsDiagonallyTopLeftToBottomRight() {
        Player[][] board = {
                {Player.X, Player.O, Player.NONE},
                {Player.O, Player.X, Player.NONE},
                {Player.NONE, Player.O, Player.X}
        };
        game.setCells(board);
        game.evaluateStatus();
        assertEquals(GameStatus.X_WON, game.getStatus());
    }

    @Test
    public void testGameStatusWhenOWinsDiagonallyTopRightToBottomLeft() {
        Player[][] board = {
                {Player.X, Player.X, Player.O},
                {Player.X, Player.O, Player.NONE},
                {Player.O, Player.NONE, Player.X}
        };
        game.setCells(board);
        game.evaluateStatus();
        assertEquals(GameStatus.O_WON, game.getStatus());
    }

    @Test
    public void testGameStatusWhenDraw() {
        Player[][] board = {
                {Player.X, Player.O, Player.X},
                {Player.X, Player.X, Player.O},
                {Player.O, Player.X, Player.O}
        };
        game.setCells(board);
        game.evaluateStatus();
        assertEquals(GameStatus.DRAW, game.getStatus());
    }

    @Test
    public void testGameStatusWhileStillPlaying() {
        Player[][] board = {
                {Player.X, Player.O, Player.NONE},
                {Player.NONE, Player.X, Player.NONE},
                {Player.O, Player.NONE, Player.NONE}
        };
        game.setCells(board);
        game.evaluateStatus();
        assertEquals(GameStatus.PLAYING, game.getStatus());
    }

}