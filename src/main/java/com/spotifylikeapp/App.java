package com.spotifylikeapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
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
            Type listType = new TypeToken<ArrayList<Song>>() {
            }.getType();
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
            System.out.println("[F] Favorites"); // NEW MENU OPTION
            System.out.println("[Q] Quit");
            System.out.print("Select option: ");
            choice = scanner.nextLine().toUpperCase().charAt(0);

            switch (choice) {
                case 'H':
                    showHome();
                    break;
                case 'S':
                    searchSong();
                    break;
                case 'L':
                    showLibrary();
                    break;
                case 'F':
                    showFavorites();
                    break; // NEW
                case 'Q':
                    System.out.println(" Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 'Q');
    }

    private static void showHome() {
        System.out.println("\n Home - Featured Songs");
        for (int i = 0; i < songs.size(); i++) {
            System.out.println((i + 1) + ". " + songs.get(i));
        }
    }

    private static void showFavorites() {
        System.out.println("\n Favorites ❤️:");
        List<Song> favorites = new ArrayList<>();
        for (Song s : songs) {
            if (s.isFavorite())
                favorites.add(s);
        }

        if (favorites.isEmpty()) {
            System.out.println("No favorite songs yet.");
            return;
        }

        for (int i = 0; i < favorites.size(); i++) {
            System.out.println((i + 1) + ". " + favorites.get(i));
        }

        System.out.print("Select a song number to play or 0 to go back: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= favorites.size()) {
                Song selected = favorites.get(choice - 1);
                playSongMenu(selected);
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private static void playSongMenu(Song song) {
        System.out.println("▶ Now playing: " + song);
        MusicPlayer.play(song.getFilePath());

        char option;
        do {
            System.out.println(
                    "\n[ P ] Pause | [ R ] Resume |  [ W ] Rewind | [ F ] Forward | [ H ] Heart/Favorite | [ S ] Stop | [ Q ] Back");
            System.out.print("Enter option: ");
            option = scanner.nextLine().toUpperCase().charAt(0);

            switch (option) {
                case 'P':
                    MusicPlayer.pause();
                    break;
                case 'R':
                    MusicPlayer.resume();
                    break;
                case 'S':
                    MusicPlayer.stop();
                    break;
                case 'W':
                    MusicPlayer.rewind(5);
                    break;
                case 'F':
                    MusicPlayer.forward(5);
                    break;
                case 'H':
                    song.toggleFavorite();
                    System.out.println(song.isFavorite() ? "💖 Added to Favorites" : "💔 Removed from Favorites");
                    saveSongs();
                    break;
                case 'Q':
                    MusicPlayer.stop();
                    System.out.println("Returning to menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 'Q');
    }

    private static void saveSongs() {
        try (FileWriter writer = new FileWriter("src/main/resources/songs.json")) {
            Gson gson = new Gson();
            gson.toJson(songs, writer);
        } catch (Exception e) {
            System.out.println("⚠ Could not save songs: " + e.getMessage());
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
                    playSongMenu(s);
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
                Song selected = songs.get(choice - 1);
                playSongMenu(selected);
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}
