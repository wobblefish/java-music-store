package src.main.java.com.mmcneil.musicstore;

import src.main.java.com.mmcneil.musicstore.api.DeezerClient;
import src.main.java.com.mmcneil.musicstore.model.Album;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

public class MainWindow {

    private JPanel resultPanel;
    private JScrollPane scrollPane;
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
        resultPanel = new JPanel(new GridLayout(0, 4,10,10));
        scrollPane = new JScrollPane(resultPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        resultPanel.setBackground(BG_COLOR);
        resultPanel.setForeground(Color.WHITE);

        panel.add(lblMain, BorderLayout.NORTH);
        panel.add(pnlSearch, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
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
            resultPanel.removeAll();
            String term = txtSearch.getText();

            if (term.isEmpty()) return;

            List<Album> results = DeezerClient.getSearchResults(term);
            StringBuilder output = new StringBuilder();

            for (Album a : results) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(Color.BLACK);
                card.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

                card.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showAlbumDetails(a);
                    }
                });

                try {
                    URL imageUrl = new URL(a.getCoverUrl());
                    ImageIcon icon = new ImageIcon(imageUrl);
                    Image scaledImg = icon.getImage().getScaledInstance(180,180, Image.SCALE_SMOOTH);
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



                infoPanel.add(lblTitle, BorderLayout.SOUTH);
                infoPanel.add(lblArtist, BorderLayout.SOUTH);
                card.add(infoPanel, BorderLayout.SOUTH);

                resultPanel.add(card); // add the result to the search results pane

            }
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





