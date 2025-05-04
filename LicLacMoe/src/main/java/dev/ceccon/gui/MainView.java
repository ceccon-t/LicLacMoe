package dev.ceccon.gui;

import dev.ceccon.client.LLMClient;
import dev.ceccon.tictactoe.Game;
import dev.ceccon.tictactoe.LLMPlayer;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView(LLMClient llmClient) {
        super("LicLacMoe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LLMPlayer llmPlayer = new LLMPlayer(llmClient);
        Game game = new Game(llmPlayer);

        BoardView boardView = new BoardView(game);
        InfosView infosView = new InfosView(game);
        ControlsView controlsView = new ControlsView(game);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(controlsView);
        bottomPanel.add(infosView);

        setSize(400, 400);
        setLayout(new BorderLayout());

        add(boardView, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

}
