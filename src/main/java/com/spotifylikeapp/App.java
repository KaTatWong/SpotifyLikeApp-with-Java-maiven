package com.spotifylikeapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class App {
    private static List<Song> songs = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadSongs();
        showMenu();
    }

    private static void loadSongs() {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Song>>() {}.getType();
            songs = gson.fromJson(new FileReader("src/main/resources/songs.json"), listType);
            System.out.println(" Loaded " + songs.size() + " songs.");
        } catch (Exception e) {
            System.out.println("⚠ Could not load songs.json: " + e.getMessage());
        }
    }

    private static void showMenu() {
        char choice;
        do {
            System.out.println("\n Spotify-Like App");
            System.out.println("[H] Home");
            System.out.println("[S] Search by Title");
            System.out.println("[L] Library");
            System.out.println("[Q] Quit");
            System.out.print("Select option: ");
            choice = scanner.nextLine().toUpperCase().charAt(0);

            switch (choice) {
                case 'H': showHome(); break;
                case 'S': searchSong(); break;
                case 'L': showLibrary(); break;
                case 'Q': System.out.println(" Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 'Q');
    }

    private static void showHome() {
        System.out.println("\n Home - Featured Songs");
        for (int i = 0; i < songs.size(); i++) {
            System.out.println((i + 1) + ". " + songs.get(i));
        }
    }

    private static void searchSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().toLowerCase();
        for (Song s : songs) {
            if (s.getTitle().toLowerCase().contains(title)) {
                System.out.println("Found: " + s);
                System.out.print("Play this song? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y"))
                    MusicPlayer.play(s.getFilePath());
                return;
            }
        }
        System.out.println(" No match found.");
    }

    private static void showLibrary() {
        System.out.println("\n Library:");
        for (int i = 0; i < songs.size(); i++) {
            System.out.println((i + 1) + ". " + songs.get(i));
        }
        System.out.print("Select song number to play or 0 to go back: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= songs.size()) {
                MusicPlayer.play(songs.get(choice - 1).getFilePath());
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}
