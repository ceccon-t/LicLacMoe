package dev.ceccon.tictactoe;

public class Game {

    private Player currentPlayer = Player.X;
    private Player[][] cells;

    public Game() {
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

    public boolean checkAvailable(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) return false;
        return cells[row][col] == Player.NONE;
    }

    public boolean makeMove(int row, int col) {
        if (!checkAvailable(row, col)) return false;
        cells[row][col] = currentPlayer;
        switchPlayer();
        return true;
    }

    private void switchPlayer() {
        this.currentPlayer = (currentPlayer == Player.X) ? Player.O: Player.X;
    }

}
