package dev.ceccon.gui;

import dev.ceccon.tictactoe.Game;
import dev.ceccon.tictactoe.GameStatus;
import dev.ceccon.tictactoe.GameStatusChangedObserver;

import javax.swing.*;
import java.awt.*;

public class InfosView extends JPanel implements GameStatusChangedObserver {

    JLabel label = new JLabel("");

    public InfosView(Game game) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        label.setText("Games played: 0   X wins: 0   O wins: 0");

        add(label);

        game.addGameStatusChangedObserver(this);
    }

    @Override
    public void gameStatusChanged(GameStatus newStatus) {
        updateLabelOnStatusChange(newStatus);
    }

    private void updateLabelOnStatusChange(GameStatus newStatus) {
        switch (newStatus) {
            case PLAYING -> { }
            case X_WON -> label.setText("X Won!");
            case O_WON -> label.setText("O Won!");
            case DRAW -> label.setText("Draw!");
        }
    }
}
