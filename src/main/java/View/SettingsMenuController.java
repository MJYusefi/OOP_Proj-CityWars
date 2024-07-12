package View;

import Controller.MusicPlayer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;


public class SettingsMenuController extends Application {

    public static String sound = "/Music/1.mp3";
    @FXML
    private Slider volumeSlider;

    @FXML
    private ComboBox<String> themeComboBox;

    @FXML
    public void initialize() {
        MusicPlayer mediaPlayer = MusicPlayer.getInstance("");
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);

        volumeSlider.setValue(mediaPlayer.getVolume() * 100); // Convert to 0-100 range
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue() / 100); // Convert back to 0-1 range
        });
    }

    @FXML
    public void applySettings() {
        // Apply the selected theme
        String selectedTheme = themeComboBox.getValue();
        if (selectedTheme != null) {
            switch (selectedTheme) {
                case "Default":
                    setUserAgentStylesheet(null);
                    break;
                case "Dark":
                    setUserAgentStylesheet(getClass().getResource("/CSS/dark-theme.css").toExternalForm());
                    break;
                case "Light":
                    setUserAgentStylesheet(getClass().getResource("/CSS/light-theme.css").toExternalForm());
                    break;
            }
        }
    }

    @FXML
    public void goBack() throws IOException {
        new MainMenu().start(stage);
    }

    @FXML
    public void changeMusic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Music File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String musicFilePath = selectedFile.getAbsolutePath();
            MusicPlayer.setNewMusic(musicFilePath);
        }
    }


    public static Stage stage;
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/setting.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

}
