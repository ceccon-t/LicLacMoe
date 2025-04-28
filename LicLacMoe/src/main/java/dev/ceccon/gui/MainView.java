package dev.ceccon.gui;

import dev.ceccon.config.LLMAPIConfig;
import dev.ceccon.tictactoe.Game;
import dev.ceccon.tictactoe.LLMPlayer;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView(LLMAPIConfig apiConfig) {
        super("LicLacMoe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LLMPlayer llmPlayer = new LLMPlayer(apiConfig);
        Game game = new Game(llmPlayer);

        BoardView boardView = new BoardView(game);
        InfosView infosView = new InfosView(game);

        setSize(400, 400);
        setLayout(new BorderLayout());

        add(boardView, BorderLayout.CENTER);
        add(infosView, BorderLayout.SOUTH);
        setVisible(true);
    }

}
