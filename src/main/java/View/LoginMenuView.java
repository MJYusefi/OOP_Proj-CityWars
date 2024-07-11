//@@@>>>MJY<<<@@@
package View;

import Controller.AuthController;
import Controller.Database;
import Model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenuView extends Application {
    public static void main(String[] args) throws SQLException {
        Database.InitProgram();
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
    private Label messageLabel;

    @FXML
    private Label messageLabel1;

    @FXML
    private TextField answer;

    private int failedAttempts = 0;
    private static final int MAX_FAILED_ATTEMPTS = 2;
    private static final int LOCKOUT_DURATION = 10;
    private boolean isDelayActive = false;
    private Timeline countdown;
    private int remainingLockoutTime;

    @FXML
    private void initialize() {
        signInButton.setOnAction(event -> {
            try {
                handleSignIn();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        signUpButton.setOnAction(event -> {
            try {
                handleSignUp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        forgotPasswordButton.setOnAction(event -> {
            try {
                handleForgotPassword();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Stage stage;

    @Override
    public  void start(Stage stage) throws IOException {
        this.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/LoginMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleSignIn() throws Exception {
        if (isDelayActive) {
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isAuthenticated = AuthController.signIn(username, password);

        if (isAuthenticated) {
            messageLabel.setText("Login successful!");
            AuthController.LoginUser = Database.getByUsername(username);
            MainMenu menu = new MainMenu();
            menu.player1 = AuthController.LoginUser;
            menu.start(LoginMenuView.stage);
        } else {
            failedAttempts++;
            if (failedAttempts > MAX_FAILED_ATTEMPTS) {
                startLockout();
            } else {
                messageLabel.setText("Invalid credentials. Try again.");
            }
        }
    }

    private void handleSignUp() throws IOException {
        // Handle sign-up logic
        new RegisterMenu().start(stage);
    }

    private void handleForgotPassword() throws Exception {
        // Handle forgot password logic
        if(!usernameField.getText().equals("")) {
            if(Database.ExistThisUsername(usernameField.getText())) {
                Player p = Database.getByUsername(usernameField.getText());
                messageLabel1.setText(p.getQuestion());
                if(answer.getText().equals(p.getAnswer())){
                    MainMenu menu = new MainMenu();
                    menu.player1 = p;
                    AuthController.LoginUser = p;
                    menu.start(stage);
                }
                else if(answer.getText().equals("")) messageLabel.setText("Enter answer");
                else messageLabel.setText("Wrong Answer");
            }
            else messageLabel.setText("Enter valid username");
        }
        else messageLabel.setText("Please enter your username");
    }

    private void startLockout() {
        isDelayActive = true;
        signInButton.setDisable(true);
        failedAttempts = 0;
        remainingLockoutTime = LOCKOUT_DURATION;

        countdown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingLockoutTime--;
            messageLabel.setText("Too many failed attempts. Try again in " + remainingLockoutTime + " seconds.");
            if (remainingLockoutTime <= 0) {
                countdown.stop();
                isDelayActive = false;
                signInButton.setDisable(false);
                messageLabel.setText("");
            }
        }));
        countdown.setCycleCount(LOCKOUT_DURATION);
        countdown.play();
    }
}
