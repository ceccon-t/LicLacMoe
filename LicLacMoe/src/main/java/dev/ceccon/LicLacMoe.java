package dev.ceccon;

import dev.ceccon.gui.MainView;

import javax.swing.*;

public class LicLacMoe {

    public static void main( String[] args ) {
        System.out.println("Starting LicLacMoe...");

        SwingUtilities.invokeLater(() -> {
            new MainView();
        });
    }

}
