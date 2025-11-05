package com.spotifylikeapp;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private String filePath;
    private boolean isFavorite; // NEW FIELD
    private List<String> comments;

    public Song(String title, String artist, int year, String genre, String filePath, boolean isFavorite) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.filePath = filePath;
        this.isFavorite = isFavorite;
        this.comments = new ArrayList<>();
    }

    // Add a simpler constructor for backward compatibility
    public Song(String title, String artist, int year, String genre, String filePath) {
        this(title, artist, year, genre, filePath, false);
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void toggleFavorite() {
        this.isFavorite = !this.isFavorite;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d) [%s]%s",
                title, artist, year, genre, isFavorite ? " ❤️" : "");
    }
}
