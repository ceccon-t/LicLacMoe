package dev.ceccon.tictactoe;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private Player currentPlayer = Player.X;
    private Player[][] cells;
    private GameStatus status = GameStatus.PLAYING;

    private List<GameStatusChangedObserver> gameStatusChangedObservers = new LinkedList<>();
    private List<CellChangedObserver> cellChangedObservers = new LinkedList<>();

    public Game(LLMPlayer llmPlayer) {
        cells = new Player[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = Player.NONE;
            }
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player[][] getCells() {
        return cells;
    }

    public void setCells(Player[][] cells) {
        this.cells = cells;
    }

    public GameStatus getStatus() {
        return status;
    }

    private void setStatus(GameStatus status) {
        this.status = status;
        System.out.println(status);
        gameStatusChangedObservers.stream().forEach(o -> o.gameStatusChanged(status));
    }

    public void addGameStatusChangedObserver(GameStatusChangedObserver observer) {
        gameStatusChangedObservers.add(observer);
    }

    public void addCellChangedObserver(CellChangedObserver observer) {
        cellChangedObservers.add(observer);
    }

    public boolean checkAvailable(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) return false;
        return cells[row][col] == Player.NONE;
    }

    public boolean makeMove(int row, int col, Player player) {
        if (status != GameStatus.PLAYING) return false;
        if (player != currentPlayer) return false;
        if (!checkAvailable(row, col)) return false;

        markCell(row, col, currentPlayer);

        evaluateStatus();
        switchPlayer();
        return true;
    }

    public void evaluateStatus() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (cells[i][0] != Player.NONE
                    && cells[i][0] == cells[i][1]
                    && cells[i][1] == cells[i][2]) {
                playerWon(cells[i][0]);
                return;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (cells[0][j] != Player.NONE
                    && cells[0][j] == cells[1][j]
                    && cells[1][j] == cells[2][j]) {
                playerWon(cells[0][j]);
                return;
            }
        }

        // Check diagonals
        // Top left to right bottom
        if (cells[0][0] != Player.NONE
            && cells[0][0] == cells[1][1]
            && cells[1][1] == cells[2][2]) {
            playerWon(cells[0][0]);
            return;
        }
        // Left bottom to right top
        if (cells[2][0] != Player.NONE
            && cells[2][0] == cells[1][1]
            && cells[1][1] == cells[2][0]) {
            playerWon(cells[2][0]);
            return;
        }

        // Check playing
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j] == Player.NONE) {
                    setStatus(GameStatus.PLAYING);
                    return;
                }
            }
        }

        // Draw
        setStatus(GameStatus.DRAW);

    }

    private void markCell(int row, int col, Player player) {
        cells[row][col] = player;

        cellChangedObservers.stream().forEach(o -> o.cellChanged(row, col, player));
    }

    private void playerWon(Player player) {
        if (player == Player.NONE) return;

        if (player == Player.X) {
            setStatus(GameStatus.X_WON);
        } else {
            setStatus(GameStatus.O_WON);
        }
    }

    private void switchPlayer() {
        this.currentPlayer = (currentPlayer == Player.X) ? Player.O: Player.X;
    }

}
