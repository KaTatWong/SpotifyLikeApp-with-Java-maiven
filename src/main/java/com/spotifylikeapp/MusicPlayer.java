package com.spotifylikeapp;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class MusicPlayer {

    private static Player player;
    private static Thread playerThread;
    private static AtomicBoolean isPaused = new AtomicBoolean(false);
    private static String currentFile;

    // Play a file
    public static void play(String filePath) {

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("⚠ File does not exist: " + filePath);
            return;
        }

        currentFile = filePath;

        playerThread = new Thread(() -> {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                player = new Player(bis);
                System.out.println("▶ Now playing: " + file.getName());
                player.play();
            } catch (Exception e) {
                System.out.println("⚠ Unable to play file: " + e.getMessage());
            }
        });

        isPaused.set(false);
        playerThread.start();
    }

    // Pause simulation
    public static void pause() {
        if (playerThread != null && !isPaused.get()) {
            isPaused.set(true);
            player.close(); // Stops current playback
            System.out.println("⏸ Paused (will resume from start when resumed)");
        } else {
            System.out.println("⚠ Nothing is playing or already paused.");
        }
    }

    // Resume simulation (restarts the file)
    public static void resume() {
        if (isPaused.get() && currentFile != null) {
            System.out.println("▶ Resuming: " + new File(currentFile).getName());
            play(currentFile); // Restart the song from beginning
        } else {
            System.out.println("⚠ Nothing to resume.");
        }
    }

    // Stop playback
    public static void stop() {
        if (player != null) {
            player.close();
            player = null;
        }
        if (playerThread != null) {
            playerThread.interrupt();
            playerThread = null;
        }
        isPaused.set(false);
        System.out.println("⏹ Stopped");
    }

    // Rewind simulation: restart the song
    public static void rewind(int seconds) {
        if (currentFile != null) {
            System.out.println("⏪ Rewind " + seconds + " seconds (restarting song)");
            play(currentFile);
        }
    }

    // Forward simulation: restart the song
    public static void forward(int seconds) {
        if (currentFile != null) {
            System.out.println("⏩ Forward " + seconds + " seconds (restarting song)");
            play(currentFile);
        }
    }
}
