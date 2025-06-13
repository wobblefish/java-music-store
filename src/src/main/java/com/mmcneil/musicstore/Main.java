package src.main.java.com.mmcneil.musicstore;

import kong.unirest.Unirest;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final Color BG_COLOR = Color.DARK_GRAY;

    public static void main(String[] args) {
        System.out.println("Welcome to the store. It's pretty bare around here for now.");
        createWindow();
        System.out.println(Unirest.get("https://api.deezer.com/search?q=dinosaur").asJson().getBody().toPrettyString());
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

        panel.setBackground(BG_COLOR);
        lblMain.setForeground(Color.WHITE);


        return panel;
    }

    private static JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(BG_COLOR);
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
