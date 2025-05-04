package dev.ceccon.gui;

import dev.ceccon.tictactoe.CellChangedObserver;
import dev.ceccon.tictactoe.Game;
import dev.ceccon.tictactoe.Player;
import dev.ceccon.tictactoe.ResetObserver;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel implements CellChangedObserver, ResetObserver {

    private static final String X_ICON = "X";
    private static final String O_ICON = "O";
    private static final String NONE_ICON = "";

    private static final Color X_COLOR = new Color(0, 0, 255);
    private static final Color O_COLOR = new Color(255, 0, 0);
    private static final Color NONE_COLOR = new Color(255, 255, 255);

    private Game game;
    private JButton[][] buttons;

    public BoardView(Game game) {
        this.game = game;
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 48));
                button.setFocusPainted(false);
                int finalRow = row;
                int finalCol = col;
                button.addActionListener((e) -> {
                    handleClick(finalRow, finalCol);
                });
                buttons[row][col] = button;
                add(button);
            }
        }

        game.addCellChangedObserver(this);
        game.addResetObserver(this);
    }

    private void handleClick(int row, int col) {
        if (!game.checkAvailable(row, col)) return;

        game.makeHumanMove(row, col);
    }

    private String playerIcon(Player player) {
        return switch (player) {
            case X -> X_ICON;
            case O -> O_ICON;
            case NONE -> NONE_ICON;
        };
    }

    private Color playerColor(Player player) {
        return switch (player) {
            case X -> X_COLOR;
            case O -> O_COLOR;
            case NONE -> NONE_COLOR;
        };
    }

    @Override
    public void cellChanged(int row, int col, Player player) {
        buttons[row][col].setText(playerIcon(player));
        buttons[row][col].setForeground(playerColor(player));

        System.out.println("Move player " + player + " (" + row + ", " + col + ")");
    }

    @Override
    public void gameReset() {
        resetCells();
    }

    private void resetCells() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(playerIcon(Player.NONE));
            }
        }
    }
}
