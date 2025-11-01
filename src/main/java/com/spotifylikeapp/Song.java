package com.spotifylikeapp;

public class Song {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private String filePath;

    public Song(String title, String artist, int year, String genre, String filePath) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.filePath = filePath;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public String getFilePath() { return filePath; }

    @Override
    public String toString() {
        return String.format("%s - %s (%d) [%s]", title, artist, year, genre);
    }
}
