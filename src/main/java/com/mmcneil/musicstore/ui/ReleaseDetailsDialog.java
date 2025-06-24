package com.mmcneil.musicstore.ui;

import com.mmcneil.musicstore.MainWindow;
import com.mmcneil.musicstore.api.DeezerClient;
import com.mmcneil.musicstore.audio.Mp3Player;
import com.mmcneil.musicstore.model.Album;
import com.mmcneil.musicstore.model.Release;
import com.mmcneil.musicstore.model.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class ReleaseDetailsDialog extends JDialog {
    public ReleaseDetailsDialog(Frame owner, Release release, Mp3Player mp3Player) {
        super(owner, "Album Details", true);
        
        final int[] currentTrackIndex = {0}; // start at first track
        final boolean[] isPlaying = {false};
        final boolean[] isPaused = {false};
        
        this.setTitle("Album Details");
        this.setModal(true);

        JPanel pnlModalContent = new JPanel();
        pnlModalContent.setLayout(new BoxLayout(pnlModalContent, BoxLayout.Y_AXIS));
        pnlModalContent.setBackground(MainWindow.BG_COLOR);
        pnlModalContent.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Create a vertically stacked player panel
        JPanel pnlPlayer = new JPanel();
        pnlPlayer.setLayout(new BoxLayout(pnlPlayer, BoxLayout.Y_AXIS));
        pnlPlayer.setBackground(MainWindow.BG_COLOR);
        List<Track> currentTracks;

        JLabel lblNowPlaying = new JLabel("Now Playing:");
        lblNowPlaying.setForeground(Color.WHITE);
        lblNowPlaying.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTrackInfo = new JLabel("");
        lblTrackInfo.setForeground(Color.LIGHT_GRAY);
        lblTrackInfo.setPreferredSize(new Dimension(lblTrackInfo.getMaximumSize().width, 22));
        lblTrackInfo.setMinimumSize(new Dimension(0, 22));
        lblTrackInfo.setMaximumSize(new Dimension(Short.MAX_VALUE, 22));
        lblTrackInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTrackInfo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlControls = new JPanel();
        pnlControls.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        pnlControls.setBackground(MainWindow.BG_COLOR);

        JButton btnPrev = new JButton("\u23EE"); // Previous
        JButton btnPlay = new JButton("\u25B6"); // Play (toggle to \u23F8 for pause)
        JButton btnNext = new JButton("\u23ED"); // Next
        JButton btnStop = new JButton("\u23F9"); // Stop

        pnlControls.add(btnPrev);
        pnlControls.add(btnPlay);
        pnlControls.add(btnNext);
        pnlControls.add(btnStop);

        pnlPlayer.add(lblNowPlaying);
        pnlPlayer.add(lblTrackInfo);
        pnlPlayer.add(Box.createVerticalStrut(5));
        pnlPlayer.add(pnlControls);

        try {
            URL imageUrl = new URL(release.getCoverXl());
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImg = icon.getImage().getScaledInstance(500,500, Image.SCALE_SMOOTH);
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
        pnlModalContent.add(lblTitle);
        pnlModalContent.add(lblArtist);
        pnlModalContent.add(Box.createRigidArea(new Dimension(0,10)));
        pnlModalContent.add(lblType);

        // Anything that differs between track and album views
        if (release instanceof Track) {
            // Specific handling for tracks
            Track track = (Track) release;
            // show duration
            JLabel lblDuration = new JLabel("Duration: " + track.getDuration());
            lblDuration.setForeground(Color.LIGHT_GRAY);
            lblDuration.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlModalContent.add(lblDuration);
        } else if (release instanceof Album) {
            // Specific handling for albums
            Album album = (Album) release;

            // show label
            Album fullAlbum = DeezerClient.getAlbumDetails(album.getId());
            String label = fullAlbum.getLabel();
            JLabel lblLabel = new JLabel("Label: " + label);
            lblLabel.setForeground(Color.LIGHT_GRAY);
            lblLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlModalContent.add(lblLabel);

            // show tracklist (if available)
            currentTracks = DeezerClient.getAlbumTracks(album.getTracklist());
            btnPlay.addActionListener(e -> {
                Track trackToPlay = currentTracks.get(currentTrackIndex[0]);
                if (!isPlaying[0] && !isPaused[0]) {
                    // Start from first track or last stopped
                    mp3Player.play(trackToPlay.getPreview());
                    lblTrackInfo.setText(trackToPlay.getArtist() + " - " + trackToPlay.getTitle());
                    isPlaying[0] = true;
                    isPaused[0] = false;
                    btnPlay.setText("\u23F8"); // Pause icon
                } else if (isPlaying[0]) {
                    mp3Player.stop();
                    isPlaying[0] = false;
                    isPaused[0] = true;
                    btnPlay.setText("\u25B6"); // Play icon
                } else if (isPaused[0]) {
                    mp3Player.play(trackToPlay.getPreview());
                    lblTrackInfo.setText(trackToPlay.getArtist() + " - " + trackToPlay.getTitle());
                    isPlaying[0] = true;
                    isPaused[0] = false;
                    btnPlay.setText("\u23F8"); // Pause icon
                }
            });
            btnStop.addActionListener(e -> {
                mp3Player.stop();
                isPlaying[0] = false;
                isPaused[0] = false;
                btnPlay.setText("\u25B6"); // Play icon
                lblTrackInfo.setText("");
            });

            btnPrev.addActionListener(e -> {
                if (currentTracks.isEmpty()) return;
                currentTrackIndex[0] = (currentTrackIndex[0] - 1 + currentTracks.size()) % currentTracks.size();
                Track prevTrack = currentTracks.get(currentTrackIndex[0]);
                lblTrackInfo.setText(prevTrack.getArtist() + " - " + prevTrack.getTitle());
                if (isPlaying[0]) {
                    mp3Player.stop();
                    mp3Player.play(prevTrack.getPreview());
                }
            });

            btnNext.addActionListener(e -> {
                if (currentTracks.isEmpty()) return;
                currentTrackIndex[0] = (currentTrackIndex[0] + 1) % currentTracks.size();
                Track nextTrack = currentTracks.get(currentTrackIndex[0]);
                lblTrackInfo.setText(nextTrack.getArtist() + " - " + nextTrack.getTitle());
                if (isPlaying[0]) {
                    mp3Player.stop();
                    mp3Player.play(nextTrack.getPreview());
                }
            });
            if (currentTracks != null && !currentTracks.isEmpty()) {
                pnlModalContent.add(Box.createVerticalStrut(12));
                JLabel lblTracklist = new JLabel("Tracklist:");
                lblTracklist.setForeground(Color.LIGHT_GRAY);
                lblTracklist.setAlignmentX(Component.CENTER_ALIGNMENT);
                pnlModalContent.add(lblTracklist);

                boolean hasMultipleDisks = currentTracks.stream()
                        .map(Track::getDiskNumber)
                        .distinct()
                        .count() > 1;

                int lastDisk = -1;
                for (Track t : currentTracks) {
                    int disk = t.getDiskNumber();
                    if (hasMultipleDisks && disk != lastDisk) {
                        if (lastDisk != -1) {
                            pnlModalContent.add(Box.createVerticalStrut(10));
                        }
                        JLabel diskHeader = new JLabel("Disk " + disk + ":");
                        diskHeader.setForeground(Color.WHITE);
                        diskHeader.setFont(diskHeader.getFont().deriveFont(Font.BOLD, 14f));
                        diskHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
                        pnlModalContent.add(diskHeader);
                        pnlModalContent.add(Box.createVerticalStrut(4));
                        lastDisk = disk;
                    }

                    JLabel lblTrack = new JLabel(t.getPaddedTrackPosition() + ". " + t.getTitle() + " (" + t.getArtist() + ")");
                    lblTrack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    lblTrack.setForeground(Color.LIGHT_GRAY);
                    lblTrack.setAlignmentX(Component.CENTER_ALIGNMENT);
                    String previewUrl = t.getPreview();
                    lblTrack.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                Desktop.getDesktop().browse(new URI(previewUrl));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    pnlModalContent.add(lblTrack);
                }
            } else {
                JLabel lblNoTracks = new JLabel("No tracks available.");
                lblNoTracks.setForeground(Color.LIGHT_GRAY);
                lblNoTracks.setAlignmentX(Component.CENTER_ALIGNMENT);
                pnlModalContent.add(lblNoTracks);
            }

        }

        JScrollPane scrollPane = new JScrollPane(pnlModalContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null); // Optional: removes the default border for cleaner look
        this.add(scrollPane, BorderLayout.CENTER);

        JButton btnCloseModal = new JButton("Close");
        btnCloseModal.addActionListener(e -> {
            mp3Player.stop();
            this.dispose();
        });
        this.add(btnCloseModal, BorderLayout.SOUTH);
        this.add(pnlPlayer, BorderLayout.SOUTH);

        // Stop playback when the dialog is closed (via X or programmatically)
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                mp3Player.stop();
            }
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                mp3Player.stop();
            }
        });
        // Responsive sizing
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.width * 0.8);
        int maxHeight = (int) (screenSize.height * 0.8);
        Dimension preferred = this.getPreferredSize();
        int width = Math.min(preferred.width, maxWidth);
        int height = Math.min(preferred.height, maxHeight);
        this.setSize(width, height);

        this.setLocationRelativeTo(null);
    }
}