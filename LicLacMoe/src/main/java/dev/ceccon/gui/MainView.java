package dev.ceccon.gui;

import dev.ceccon.tictactoe.Game;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        super("LicLacMoe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();

        BoardView boardView = new BoardView(game);
        InfosView infosView = new InfosView(game);


        setSize(400, 400);
        setLayout(new BorderLayout());

        add(boardView, BorderLayout.CENTER);
        add(infosView, BorderLayout.SOUTH);
        setVisible(true);
    }

}
