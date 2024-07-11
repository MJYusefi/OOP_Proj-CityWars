package View;


import Controller.AuthController;
import Controller.Database;
import Controller.MusicPlayer;
import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private Button startGameButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button shopButton;

    @FXML
    private Button profileEditButton;

    @FXML
    private Button exitButton;

    @FXML
    private ImageView settingsButton;

    @FXML
    private Text coinsText;

    @FXML
    private Text hpText;

    @FXML
    private void initialize() {
        coinsText.setText(AuthController.LoginUser.coin + " Coin");
        hpText.setText(AuthController.LoginUser.hp + " HP");
        startGameButton.setOnAction(event -> {
            try {
                handleStartGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        historyButton.setOnAction(event -> {
            try {
                handleHistory();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        shopButton.setOnAction(event -> {
            try {
                handleShop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        profileEditButton.setOnAction(event -> {
            try {
                handleProfileEdit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        exitButton.setOnAction(event -> {
            try {
                handleExit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        settingsButton.setOnMouseClicked(event -> {
            try {
                handleSettings();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Stage stage;
    public static Player player1;
    public static Player player2;

    @Override
    public void start(Stage stage) throws IOException {
        MusicPlayer musicPlayer = MusicPlayer.getInstance("/Music/1.mp3");
        musicPlayer.play();
        MainMenu.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/MainMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleStartGame() throws Exception {
        new LoginMenu2().start(stage);
    }

    private void handleHistory() throws Exception {
        new HistoryMenu().start(stage);
    }

    private void handleShop() throws Exception {
        new ShopMenu().start(stage);
    }

    private void handleProfileEdit() throws Exception {
        new ProfileMenu().start(stage);
    }

    private void handleSettings() throws IOException {
        new SettingsMenuController().start(stage);
    }

    private void handleExit() throws SQLException {
        if(player1 != null)
            Database.SaveEditInDB(player1);
        if(player2 != null)
            Database.SaveEditInDB(player2);
        stage.close();
    }

    public void edit(MouseEvent mouseEvent) throws Exception {
        new EditMenu().start(stage);
    }
}



