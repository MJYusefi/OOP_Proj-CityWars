package View;

import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class PreGameMenu extends Application {
    public static Player player1;
    public static Player player2;
    static int character1;
    static int character2;
    static int betCoin;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(PreGameMenu.class.getResource("/FXML/PreGameMenu.fxml")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public TextField bet;

    public void play1char1() {
        character1 = 1;
    }

    public void play1char3() {
        character1 = 3;
    }

    public void play1char2() {
        character1 = 2;
    }

    public void play1char4() {
        character1 = 4;
    }

    public void play2char1() {
        character2 = 1;
    }

    public void play2char3() {
        character2 = 3;
    }

    public void play2char2() {
        character2 = 2;
    }

    public void play2char4() {
        character2 = 4;
    }

    public void startGame() throws Exception {
        betCoin = Integer.parseInt(bet.getText());
        player1.coin -= betCoin;
        player2.coin -= betCoin;
        Game foo = new Game();
        Game.player1 = player1;
        Game.player2 = player2;
        Game.character1 = character1;
        Game.character2 = character2;
        Game.bet = betCoin;
        foo.start(MainMenu.stage);
    }
}