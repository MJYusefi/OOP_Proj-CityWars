package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


public class SettingsMenuController extends Application {

    @FXML
    private Slider volumeSlider;

    @FXML
    private ComboBox<String> themeComboBox;

    private AudioClip audioClip;

    @FXML
    public void initialize() {
        if(volumeSlider != null) {
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (audioClip != null) {
                    audioClip.setVolume(newValue.doubleValue() / 100);
                }
            });
        }
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
                    setUserAgentStylesheet(getClass().getResource("dark-theme.css").toExternalForm());
                    break;
                case "Light":
                    setUserAgentStylesheet(getClass().getResource("light-theme.css").toExternalForm());
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
        Stage stage = (Stage) volumeSlider.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String musicFilePath = selectedFile.toURI().toString();
            if (audioClip != null) {
                audioClip.stop();
            }
            audioClip = new AudioClip(musicFilePath);
            audioClip.setVolume(volumeSlider.getValue() / 100);
            audioClip.play();
        }
    }

    public void setAudioClip(AudioClip audioClip) {
        this.audioClip = audioClip;
        if (audioClip != null) {
            volumeSlider.setValue(audioClip.getVolume() * 100);
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
