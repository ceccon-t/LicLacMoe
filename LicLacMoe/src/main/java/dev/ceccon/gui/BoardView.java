package dev.ceccon.gui;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {

    private static final String X_ICON = "X";
    private static final String O_ICON = "O";

    private static final Color X_COLOR = new Color(0, 0, 255);
    private static final Color O_COLOR = new Color(255, 0, 0);

    private JButton[][] buttons;

    public BoardView() {
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
    }

    private void handleClick(int row, int col) {
        System.out.println("Pressed (" + row + ", " + col + ")");
        buttons[row][col].setText(X_ICON);
        buttons[row][col].setForeground(X_COLOR);
    }

}
