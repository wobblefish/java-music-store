package com.mmcneil.musicstore;

import com.mmcneil.musicstore.api.DeezerClient;
import com.mmcneil.musicstore.model.Album;
import com.mmcneil.musicstore.model.Release;
import com.mmcneil.musicstore.model.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainWindow {

    private JPanel resultPanel;
    private JFrame frame;
    private JTextField txtSearch;
    private static final Color BG_COLOR = Color.DARK_GRAY;
    private List<Track> trackResults = new ArrayList<>();
    private List<Album> albumResults = new ArrayList<>();
    private static final int cardWidth = 160;
    private static final int cardHeight = 200;

    public void createWindow() {
        frame = new JFrame("Music Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        JPanel panel = createMainPanel();
        frame.add(panel);

        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> txtSearch.requestFocusInWindow());

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resultPanel.setPreferredSize(calculateResultPanelHeight(trackResults));
                resultPanel.revalidate();
                resultPanel.repaint();
            }
        });
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
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search");
        JRadioButton rbAlbums = new JRadioButton("Albums", true);
        JRadioButton rbTracks = new JRadioButton("Tracks");
        ButtonGroup group = new ButtonGroup();
        group.add(rbAlbums);
        group.add(rbTracks);
        panel.add(rbAlbums);
        panel.add(rbTracks);
        panel.add(txtSearch);
        panel.add(btnSearch);
        
        // if rbAlbums is selected, enter/search button should call DeezerClient.getAlbumSearchResults
        // if rbTracks is selected / or otherwise, enter/search button should call DeezerClient.getTrackSearchResults

        btnSearch.addActionListener(e -> {
            if (rbAlbums.isSelected()) {
                albumResults = DeezerClient.getAlbumSearchResults(txtSearch.getText());
                renderRelease(new ArrayList<>(albumResults));
            } else {
                trackResults = DeezerClient.getTrackSearchResults(txtSearch.getText());
                renderRelease(new ArrayList<>(trackResults));
            }
        });
        txtSearch.addActionListener(e -> {
            if (rbAlbums.isSelected()) {
                albumResults = DeezerClient.getAlbumSearchResults(txtSearch.getText());
                renderRelease(new ArrayList<>(albumResults));
            } else {
                trackResults = DeezerClient.getTrackSearchResults(txtSearch.getText());
                renderRelease(new ArrayList<>(trackResults));
            }
        });

        return panel;
    }

    private void renderRelease(List<Release> results) {
        resultPanel.removeAll();
        int cardWidth = 160;
        int cardHeight = 200;

        for (Release release : results) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.BLACK);
            card.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            card.setPreferredSize(new Dimension(cardWidth, cardHeight));

            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showReleaseDetails(release);
                }
            });

            try {
                URL imageUrl = new URL(release.getCoverMedium());
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

            JLabel lblTitle = new JLabel(release.getTitle());
            lblTitle.setForeground(Color.WHITE);
            lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblArtist = new JLabel(release.getArtist());
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
        Dimension resultPanelDimensions = calculateResultPanelHeight(trackResults);
        resultPanel.setPreferredSize(resultPanelDimensions);
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    // helper method to calculate the needed height for the resultHandler like above
    private Dimension calculateResultPanelHeight(List<?> results) {
        int availableWidth = frame.getContentPane().getWidth();
        int cardsPerRow = Math.max(1, availableWidth / cardWidth); // avoid divide by zero
        int numRows = (int) Math.ceil((double)results.size() / cardsPerRow);
        int calculatedHeight = numRows * cardHeight + Math.max(0, (numRows - 1) * 10) + 20; // 10 is vertical gap

        return new Dimension(availableWidth, calculatedHeight);
    }

    private void showReleaseDetails(Release release) {
        JDialog dialogModal = new JDialog((Frame) null, "Album Details", true);
        dialogModal.setSize(400, 500);
        dialogModal.setLayout(new BorderLayout());

        JPanel pnlModalContent = new JPanel();
        pnlModalContent.setLayout(new BoxLayout(pnlModalContent, BoxLayout.Y_AXIS));
        pnlModalContent.setBackground(BG_COLOR);
        pnlModalContent.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        try {
            URL imageUrl = new URL(release.getCoverMedium());
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

        JLabel lblTitle = new JLabel(release.getTitle());
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblArtist = new JLabel(release.getArtist());
        lblArtist.setForeground(Color.LIGHT_GRAY);
        lblArtist.setAlignmentX(Component.CENTER_ALIGNMENT);


        //show type
        JLabel lblType = new JLabel("Type: " + release.getType());
        lblType.setForeground(Color.LIGHT_GRAY);
        lblType.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlModalContent.add(lblType);

        // Anything that differs between track and album views
        if (release instanceof Track) {
            Track track = (Track) release;
            // show duration
            JLabel lblDuration = new JLabel("Duration: " + track.getDuration());
            lblDuration.setForeground(Color.LIGHT_GRAY);
            lblDuration.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlModalContent.add(lblDuration);
        } else if (release instanceof Album) {
            Album album = (Album) release;

            // show label
            JLabel lblLabel = new JLabel("Label: " + album.getLabel());
            lblLabel.setForeground(Color.LIGHT_GRAY);
            lblLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlModalContent.add(lblLabel);

            // show tracklist (if available)
            List<Track> tracks = DeezerClient.getAlbumTracks(album.getTracklist());
            if (tracks != null && !tracks.isEmpty()) {
                JLabel lblTracklist = new JLabel("Tracklist:");
                lblTracklist.setForeground(Color.LIGHT_GRAY);
                lblTracklist.setAlignmentX(Component.CENTER_ALIGNMENT);
                pnlModalContent.add(lblTracklist);
                for (Track t : tracks) {
                    JLabel lblTrack = new JLabel(t.getPaddedTrackPosition() + ". " + t.getTitle() + " (" + t.getArtist() + ")");
                    lblTrack.setForeground(Color.LIGHT_GRAY);
                    lblTrack.setAlignmentX(Component.CENTER_ALIGNMENT);
                    pnlModalContent.add(lblTrack);
                }
            } else {
                JLabel lblNoTracks = new JLabel("No tracks available.");
                lblNoTracks.setForeground(Color.LIGHT_GRAY);
                lblNoTracks.setAlignmentX(Component.CENTER_ALIGNMENT);
                pnlModalContent.add(lblNoTracks);
            }
        }

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

    private void showReleaseDetails(Album album) {
        JDialog dialogModal = new JDialog((Frame) null, "Album Details", true);
        dialogModal.setSize(400, 500);
        dialogModal.setLayout(new BorderLayout());

        JPanel pnlModalContent = new JPanel();
        pnlModalContent.setLayout(new BoxLayout(pnlModalContent, BoxLayout.Y_AXIS));
        pnlModalContent.setBackground(BG_COLOR);
        pnlModalContent.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        try {
            URL imageUrl = new URL(album.getCoverMedium());
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
}
