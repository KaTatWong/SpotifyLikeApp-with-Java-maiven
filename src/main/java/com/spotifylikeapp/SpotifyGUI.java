package com.spotifylikeapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SpotifyGUI extends Application {

    private List<Song> songs = new ArrayList<>();
    private ListView<Song> songListView;
    private TextArea commentsArea;
    private Song selectedSong;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadSongs();

        primaryStage.setTitle("Spotify-Like App");

        // Buttons
        Button btnPlay = new Button("Play");
        Button btnPause = new Button("Pause");
        Button btnStop = new Button("Stop");
        Button btnRewind = new Button("Rewind");
        Button btnForward = new Button("Forward");
        Button btnFavorite = new Button("Toggle Favorite");
        Button btnSaveComment = new Button("Save Comment");

        // Song List
        songListView = new ListView<>();
        songListView.getItems().addAll(songs);
        songListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedSong = newVal;
            if (selectedSong != null) {
                commentsArea.setText(String.join("\n", selectedSong.getComments()));
            }
        });

        // Comments Area
        commentsArea = new TextArea();
        commentsArea.setPromptText("Add your comments here...");

        // Button actions
        btnPlay.setOnAction(e -> {
            if (selectedSong != null)
                MusicPlayer.play(selectedSong.getFilePath());
        });
        btnPause.setOnAction(e -> MusicPlayer.pause());
        btnStop.setOnAction(e -> MusicPlayer.stop());
        btnRewind.setOnAction(e -> {
            if (selectedSong != null)
                MusicPlayer.rewind(5);
        });
        btnForward.setOnAction(e -> {
            if (selectedSong != null)
                MusicPlayer.forward(5);
        });
        btnFavorite.setOnAction(e -> {
            if (selectedSong != null) {
                selectedSong.toggleFavorite();
                songListView.refresh();
                saveSongs();
            }
        });
        btnSaveComment.setOnAction(e -> {
            if (selectedSong != null) {
                selectedSong.addComment(commentsArea.getText());
                saveSongs();
            }
        });

        // Layout
        HBox controls = new HBox(10, btnPlay, btnPause, btnStop, btnRewind, btnForward, btnFavorite);
        VBox rightPane = new VBox(10, new Label("Comments:"), commentsArea, btnSaveComment);
        HBox mainLayout = new HBox(10, songListView, rightPane);
        VBox root = new VBox(10, mainLayout, controls);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadSongs() {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Song>>() {
            }.getType();
            songs = gson.fromJson(new FileReader("src/main/resources/songs.json"), listType);
        } catch (Exception e) {
            System.out.println("⚠ Could not load songs.json: " + e.getMessage());
        }
    }

    private void saveSongs() {
        try (FileWriter writer = new FileWriter("src/main/resources/songs.json")) {
            Gson gson = new Gson();
            gson.toJson(songs, writer);
        } catch (Exception e) {
            System.out.println("⚠ Could not save songs: " + e.getMessage());
        }
    }
}
