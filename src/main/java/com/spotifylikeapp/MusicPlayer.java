package com.spotifylikeapp;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MusicPlayer {
    public static void play(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Player player = new Player(fis);
            System.out.println("▶ Now playing: " + filePath);
            player.play();
        } catch (Exception e) {
            System.out.println("⚠ Unable to play file: " + e.getMessage());
        }
    }
}
