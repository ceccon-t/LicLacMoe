package dev.ceccon.gui;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        super("LicLacMoe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoardView boardView = new BoardView();


        setSize(400, 400);
        setLayout(new BorderLayout());

        add(boardView, BorderLayout.CENTER);
        setVisible(true);
    }

}
