package Controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {

    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;

    private MusicPlayer(String musicFilePath) {
        Media media = new Media(getClass().getResource(musicFilePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        // Set the player to repeat the music
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    public static MusicPlayer getInstance(String musicFilePath) {
        if (instance == null) {
            instance = new MusicPlayer(musicFilePath);
        }
        return instance;
    }

    public void play() {
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public double getVolume() {
        return mediaPlayer.getVolume();
    }

    public void setMute(boolean mute) {
        mediaPlayer.setMute(mute);
    }

    public static void setNewMusic(String musicFilePath) {
        if (instance == null) getInstance(musicFilePath);
        else {
            if (instance != null && instance.mediaPlayer != null) {
                instance.mediaPlayer.stop();
            }
            Media media = new Media(new File(musicFilePath).toURI().toString());
            instance.mediaPlayer = new MediaPlayer(media);
            instance.mediaPlayer.setOnEndOfMedia(() -> instance.mediaPlayer.seek(Duration.ZERO));
            instance.mediaPlayer.play();
        }
    }
}