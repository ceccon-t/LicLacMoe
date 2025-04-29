package dev.ceccon.tictactoe;

public interface CellChangedObserver {

    void cellChanged(int row, int col, Player player);

}
