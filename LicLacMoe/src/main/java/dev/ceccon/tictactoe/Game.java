package dev.ceccon.tictactoe;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private static final String X_ICON = "X";
    private static final String O_ICON = "O";
    private static final String NONE_ICON = " ";

    private LLMPlayer llmPlayer;

    private Player currentPlayer = Player.X;
    private Player[][] cells;
    private GameStatus status = GameStatus.PLAYING;

    private List<GameStatusChangedObserver> gameStatusChangedObservers = new LinkedList<>();
    private List<CellChangedObserver> cellChangedObservers = new LinkedList<>();
    private List<ResetObserver> resetObservers = new LinkedList<>();

    public Game(LLMPlayer llmPlayer) {
        this.llmPlayer = llmPlayer;
        initialize();
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

    public void addResetObserver(ResetObserver observer) {
        resetObservers.add(observer);
    }

    public boolean checkAvailable(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) return false;
        return cells[row][col] == Player.NONE;
    }

    public boolean makeHumanMove(int row, int col) {
        boolean successfulMove = makeMove(row, col, Player.X);

        if (successfulMove && status == GameStatus.PLAYING) {
            Cell llmMove = llmPlayer.getNextMove(this);
            makeMove(llmMove.row(), llmMove.col(), Player.O);
        }

        return successfulMove;
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
            && cells[1][1] == cells[0][2]) {
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

    public void reset() {
        initialize();

        resetObservers.forEach(ResetObserver::gameReset);
    }

    public String getBoardStringified() {
        StringBuilder builder = new StringBuilder();
        builder.append(iconAt(0, 0)).append(" | ").append(iconAt(0, 1)).append(" | ").append(iconAt(0, 2));
        builder.append("\n----------\n");
        builder.append(iconAt(1, 0)).append(" | ").append(iconAt(1, 1)).append(" | ").append(iconAt(1, 2));
        builder.append("\n----------\n");
        builder.append(iconAt(2, 0)).append(" | ").append(iconAt(2, 1)).append(" | ").append(iconAt(2, 2));
        builder.append("\n");

        return builder.toString();
    }

    private void initialize() {
        currentPlayer = Player.X;
        cells = new Player[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = Player.NONE;
            }
        }
        status = GameStatus.PLAYING;
    }

    private String iconAt(int row, int col) {
        return switch (cells[row][col]) {
            case X -> X_ICON;
            case O -> O_ICON;
            case NONE -> NONE_ICON;
        };
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
