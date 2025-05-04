package dev.ceccon.gui;

import dev.ceccon.tictactoe.Game;

import javax.swing.*;
import java.awt.*;

public class ControlsView extends JPanel {

    public ControlsView(Game game) {
        setLayout(new BorderLayout());

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> {
            game.reset();
            System.out.println("Resetting...");
        });

        add(btnReset, BorderLayout.CENTER);
    }

}
