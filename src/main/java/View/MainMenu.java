package View;


import Controller.AuthController;
import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
    private void initialize() {
        startGameButton.setOnAction(event -> {
            try {
                handleStartGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        historyButton.setOnAction(event -> handleHistory());
        shopButton.setOnAction(event -> handleShop());
        profileEditButton.setOnAction(event -> {
            try {
                handleProfileEdit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        exitButton.setOnAction(event -> handleExit());
        settingsButton.setOnMouseClicked(event -> handleSettings());
    }

    public static Stage stage;
    public static Player player;

    @Override
    public void start(Stage stage) throws IOException {
        MainMenu.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/MainMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleStartGame() throws Exception {
        // Handle start game logic
        System.out.println("Start Game button clicked");
        PreGameMenu foo = new PreGameMenu();

        foo.player1= AuthController.LoginUser;
//        foo.player2=;
        foo.start(stage);
    }

    private void handleHistory() {
        // Handle history logic
        System.out.println("History button clicked");
    }

    private void handleShop() {
        // Handle shop logic
        System.out.println("Shop button clicked");
    }

    private void handleProfileEdit() throws Exception {
        // Handle profile edit logic
        new ProfileMenu().start(stage);
    }

    private void handleSettings() {
        // Handle settings logic
        System.out.println("Settings button clicked");
    }

    private void handleExit() {
        // Handle exit logic
        System.out.println("Exit button clicked");
        stage.close();
    }

}



