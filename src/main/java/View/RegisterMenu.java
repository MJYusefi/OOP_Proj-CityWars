package View;


import Controller.AuthController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;


public class RegisterMenu extends Application {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField emailField;

    @FXML
    private ChoiceBox<String> securityQuestionChoiceBox;

    @FXML
    private TextField securityAnswerField;

    @FXML
    private TextField captchaField;

    @FXML
    private Label captchaLabel;

    @FXML
    private Button generatePasswordButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        generatePasswordButton.setOnAction(event -> generateRandomPassword());
        registerButton.setOnAction(event -> {
            try {
                handleRegister();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        generateCaptcha();
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;
    private static final Random RANDOM = new SecureRandom();

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/RegisterMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void generateRandomPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        passwordField.setText(password.toString());
        messageLabel.setText("Your Password is :" + password.toString());
    }

    private void generateCaptcha() {
        StringBuilder captcha = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            captcha.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        captchaLabel.setText(captcha.toString());
    }

    private void handleRegister() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String securityQuestion = securityQuestionChoiceBox.getValue();
        String securityAnswer = securityAnswerField.getText();
        String captcha = captchaField.getText();

        if (!captcha.equals(captchaLabel.getText())) {
            messageLabel.setText("Invalid CAPTCHA. Try again.");
            generateCaptcha();
            return;
        }

        String message = AuthController.register(username, password, nickname, email, securityQuestion, securityAnswer);
        messageLabel.setText(message);
        if(message.equals("Register successfully")){
            MainMenu menu = new MainMenu();
            menu.start(LoginMenuView.stage);
        }
    }
}
