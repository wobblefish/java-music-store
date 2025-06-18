package com.mmcneil.musicstore;

import com.mmcneil.musicstore.api.DeezerClient;
import com.mmcneil.musicstore.model.Album;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

public class MainWindow {

    private JPanel resultPanel;
    private JFrame frame;
    private static final Color BG_COLOR = Color.DARK_GRAY;

    public void createWindow() {
        frame = new JFrame("Music Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        JPanel panel = createMainPanel();
        frame.add(panel);

        frame.setVisible(true);
    }

    private JPanel createMainPanel() {
        // Top Panel (BoxLayout) - Title/Search
        JLabel lblMain = new JLabel("Welcome to the Music Store");
        lblMain.setForeground(Color.WHITE);
        lblMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel pnlSearch = createSearchPanel();
        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
        pnlTop.setBackground(BG_COLOR);
        pnlTop.add(lblMain);
        pnlTop.add(pnlSearch);

        // Main Panel (BorderLayout)
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(pnlTop, BorderLayout.NORTH);

        // Search Results Panel (FlowLayout) in  scrolling pane
        resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        resultPanel.setBackground(BG_COLOR);
        resultPanel.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBackground(BG_COLOR);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(BG_COLOR);
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search");
        int cardWidth = 160;
        int cardHeight = 200;

        panel.add(txtSearch);
        panel.add(btnSearch);

        btnSearch.addActionListener(e -> {
            resultPanel.removeAll();
            String term = txtSearch.getText();

            if (term.isEmpty()) return;

            List<Album> results = DeezerClient.getSearchResults(term);
            StringBuilder output = new StringBuilder();

            for (Album a : results) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(Color.BLACK);
                card.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                card.setPreferredSize(new Dimension(cardWidth, cardHeight));

                card.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showAlbumDetails(a);
                    }
                });

                try {
                    URL imageUrl = new URL(a.getCoverUrl());
                    ImageIcon icon = new ImageIcon(imageUrl);
                    Image scaledImg = icon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH);
                    JLabel coverLabel = new JLabel(new ImageIcon(scaledImg));
                    card.add(coverLabel, BorderLayout.CENTER);
                } catch (Exception ex) {
                    card.add(new JLabel("No Image"), BorderLayout.CENTER);
                }
                // Info panel for the title and artist
                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.setBackground(Color.BLACK);

                JLabel lblTitle = new JLabel(a.getTitle());
                lblTitle.setForeground(Color.WHITE);
                lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel lblArtist = new JLabel(a.getArtist());
                lblArtist.setForeground(Color.LIGHT_GRAY);
                lblArtist.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Add items to the info label
                infoPanel.add(lblTitle);
                infoPanel.add(Box.createVerticalStrut(4));
                infoPanel.add(lblArtist);


                card.add(infoPanel, BorderLayout.SOUTH);

                resultPanel.add(card); // add the result to the search results pane

            }
            // Calculate how many cards fit per row, including spacing
            int availableWidth = frame.getContentPane().getWidth();
            int cardsPerRow = Math.max(1, availableWidth / cardWidth); // avoid divide by zero
            int numRows = (int) Math.ceil((double)results.size() / cardsPerRow);
            int calculatedHeight = numRows * cardHeight + Math.max(0, (numRows - 1) * 10) + 20; // 10 is vertical gap

            resultPanel.setPreferredSize(new Dimension(availableWidth, calculatedHeight));
            resultPanel.revalidate();
            resultPanel.repaint();
            
        });

        return panel;
    }

    private void showAlbumDetails(Album album) {
        JDialog dialogModal = new JDialog((Frame) null, "Album Details", true);
        dialogModal.setSize(400, 500);
        dialogModal.setLayout(new BorderLayout());

        JPanel pnlModalContent = new JPanel();
        pnlModalContent.setLayout(new BoxLayout(pnlModalContent, BoxLayout.Y_AXIS));
        pnlModalContent.setBackground(BG_COLOR);
        pnlModalContent.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        try {
            URL imageUrl = new URL(album.getCoverUrl());
            ImageIcon icon = new ImageIcon((imageUrl));
            Image scaledImg = icon.getImage().getScaledInstance(300,300, Image.SCALE_SMOOTH);
            JLabel coverLabel = new JLabel(new ImageIcon(scaledImg));
            coverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlModalContent.add(coverLabel);
        } catch (Exception ex) {
            JLabel lblError = new JLabel("No Image Available");
            lblError.setForeground(Color.WHITE);
            lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlModalContent.add(lblError);
        }

        JLabel lblTitle = new JLabel(album.getTitle());
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblArtist = new JLabel(album.getArtist());
        lblArtist.setForeground(Color.LIGHT_GRAY);
        lblArtist.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlModalContent.add(Box.createRigidArea(new Dimension(0,10)));
        pnlModalContent.add(lblTitle);
        pnlModalContent.add(lblArtist);

        dialogModal.add(pnlModalContent, BorderLayout.CENTER);

        JButton btnCloseModal = new JButton("Close");
        btnCloseModal.addActionListener(e -> dialogModal.dispose());
        dialogModal.add(btnCloseModal, BorderLayout.SOUTH);

        dialogModal.setLocationRelativeTo(null);
        dialogModal.setVisible(true);

    }

    // Create a panel that we will hold visual search results in
}
