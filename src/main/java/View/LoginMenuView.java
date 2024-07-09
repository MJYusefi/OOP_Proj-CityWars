//@@@>>>MJY<<<@@@
package View;

import Controller.AuthController;
import Controller.Database;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenuView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private void initialize() {
        signInButton.setOnAction(event -> handleSignIn());
        signUpButton.setOnAction(event -> handleSignUp());
        forgotPasswordButton.setOnAction(event -> handleForgotPassword());
    }

    public static Stage stage;

    @Override
    public  void start(Stage stage) throws IOException {
        MainMenu.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/LoginMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleSignIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Handle sign-in logic
    }

    private void handleSignUp() {
        // Handle sign-up logic
    }

    private void handleForgotPassword() {
        // Handle forgot password logic
    }
}
