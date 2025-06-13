package com.mmcneil.musicstore;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the store. It's pretty bare around here for now.");
        createWindow();
    }

    public static void createWindow() {
        JFrame frame = new JFrame("Music Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        JPanel panel = createMainPanel();
        frame.add(panel);

        frame.setVisible(true);

    }

    private static JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblMain = new JLabel("Welcome to the Music Store", SwingConstants.CENTER);
        JPanel pnlSearch = createSearchPanel();
        panel.add(lblMain, BorderLayout.NORTH);
        panel.add(pnlSearch, BorderLayout.CENTER);

        panel.setBackground(Color.DARK_GRAY);
        lblMain.setForeground(Color.WHITE);


        return panel;
    }

    private static JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.DARK_GRAY);
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search");

        panel.add(txtSearch);
        panel.add(btnSearch);

        btnSearch.addActionListener(e -> {
            String term = txtSearch.getText();
            System.out.println("Searching for: " + term);
        });

        return panel;
    }

}
