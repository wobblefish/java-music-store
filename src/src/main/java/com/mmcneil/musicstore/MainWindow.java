package src.main.java.com.mmcneil.musicstore;

import src.main.java.com.mmcneil.musicstore.api.DeezerClient;
import src.main.java.com.mmcneil.musicstore.model.Album;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow {

    private JTextArea txtResults;
    private static final Color BG_COLOR = Color.DARK_GRAY;

    public void createWindow() {
        JFrame frame = new JFrame("Music Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        JPanel panel = createMainPanel();
        frame.add(panel);

        frame.setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblMain = new JLabel("Welcome to the Music Store", SwingConstants.CENTER);
        JPanel pnlSearch = createSearchPanel();
        txtResults = new JTextArea(10, 40);
        txtResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResults);
        txtResults.setBackground(BG_COLOR);
        txtResults.setForeground(Color.WHITE);

        panel.add(lblMain, BorderLayout.NORTH);
        panel.add(pnlSearch, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        panel.setBackground(BG_COLOR);
        lblMain.setForeground(Color.WHITE);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(BG_COLOR);
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search");

        panel.add(txtSearch);
        panel.add(btnSearch);

        btnSearch.addActionListener(e -> {
            String term = txtSearch.getText();
            List<Album> results = DeezerClient.getSearchResults(term);
            StringBuilder output = new StringBuilder();
            for (Album a : results) {
                output.append("Title: ").append(a.getTitle()).append("\n")
                        .append("Artist: ").append(a.getArtist()).append("\n")
                        .append("Cover: ").append(a.getCoverUrl()).append("\n\n");
            }
            txtResults.setText(output.toString());
        });

        return panel;
    }
}





