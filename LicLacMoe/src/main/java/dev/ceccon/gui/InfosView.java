package dev.ceccon.gui;

import dev.ceccon.tictactoe.Game;
import dev.ceccon.tictactoe.GameStatus;
import dev.ceccon.tictactoe.GameStatusChangedObserver;
import dev.ceccon.tictactoe.ResetObserver;

import javax.swing.*;
import java.awt.*;

public class InfosView extends JPanel implements GameStatusChangedObserver, ResetObserver {

    private static final String PLAYING_LABEL = "Playing";

    JLabel label = new JLabel("");

    public InfosView(Game game) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        label.setText(PLAYING_LABEL);

        add(label);

        game.addGameStatusChangedObserver(this);
        game.addResetObserver(this);
    }

    @Override
    public void gameStatusChanged(GameStatus newStatus) {
        updateLabelOnStatusChange(newStatus);
    }

    @Override
    public void gameReset() {
        updateLabelOnGameReset();
    }

    private void updateLabelOnStatusChange(GameStatus newStatus) {
        switch (newStatus) {
            case PLAYING -> { }
            case X_WON -> label.setText("X Won!");
            case O_WON -> label.setText("O Won!");
            case DRAW -> label.setText("Draw!");
        }
    }

    private void updateLabelOnGameReset() {
        label.setText(PLAYING_LABEL);
    }

}
