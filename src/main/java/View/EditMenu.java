package View;

import Controller.AuthController;
import Controller.Database;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class EditMenu extends Application {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nicknameField;

    @FXML
    private javafx.scene.control.Button cancelButton;

    @FXML
    private javafx.scene.control.Button saveButton;

    public static Stage stage;

    @FXML
    public void initialize() {
        usernameField.setText(AuthController.LoginUser.getUsername());
        passwordField.setText(AuthController.LoginUser.getPassword());
        emailField.setText(AuthController.LoginUser.getEmail());
        nicknameField.setText(AuthController.LoginUser.getNickname());

        saveButton.setOnAction(event -> {
            try {
                handleSaveButton();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        cancelButton.setOnAction(event -> {
            try {
                handleCancelButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/EditProfile.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleSaveButton() throws SQLException, IOException {
        // Retrieve the updated information from fields
        AuthController.LoginUser.setUsername(usernameField.getText());
        AuthController.LoginUser.setPassword(passwordField.getText());
        AuthController.LoginUser.setEmail(emailField.getText());
        AuthController.LoginUser.setNickname(nicknameField.getText());

        Database.SaveEditInDB(AuthController.LoginUser);
        new MainMenu().start(stage);

    }

    private void handleCancelButton() throws IOException {
        new MainMenu().start(stage);
    }
}
