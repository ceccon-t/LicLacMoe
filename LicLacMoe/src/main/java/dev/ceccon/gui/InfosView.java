package dev.ceccon.gui;

import javax.swing.*;
import java.awt.*;

public class InfosView extends JPanel {

    JLabel label = new JLabel("");

    public InfosView() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        label.setText("Games played: 0   X wins: 0   O wins: 0");

        add(label);
    }
}
