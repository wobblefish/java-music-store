package com.mmcneil.musicstore.audio;

import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class Mp3Player {
    private Player player;
    private Thread playbackThread;

    public void play(String url) {
        stop(); // Stop any currently playing audio
        playbackThread = new Thread(() -> {
            try (InputStream is = new BufferedInputStream(new URL(url).openStream())) {
                player = new Player(is);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Mp3Player-PlaybackThread");
        playbackThread.start();
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
    }
}
