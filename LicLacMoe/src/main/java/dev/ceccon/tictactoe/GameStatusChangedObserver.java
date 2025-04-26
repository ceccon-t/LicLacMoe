package dev.ceccon.tictactoe;

public interface GameStatusChangedObserver {

    void gameStatusChanged(GameStatus newStatus);

}
