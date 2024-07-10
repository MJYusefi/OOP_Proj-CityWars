package View;

import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PreGameMenu extends Application {
    public static Player player1;
    public static Player player2;
    static int betCoin;
    int character1;
    int character2;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(PreGameMenu.class.getResource("/FXML/PreGameMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public TextField bet;

    public void play1char1(MouseEvent mouseEvent) {
        character1 = 1;
    }

    public void play1char3(MouseEvent mouseEvent) {
        character1 = 3;
    }

    public void play1char2(MouseEvent mouseEvent) {character1 = 2;
    }

    public void play1char4(MouseEvent mouseEvent) {
        character1 = 4;
    }

    public void play2char1(MouseEvent mouseEvent) {
        character2 = 1;
    }

    public void play2char3(MouseEvent mouseEvent) {
        character2 = 3;
    }

    public void play2char2(MouseEvent mouseEvent) {
        character2 = 2;
    }

    public void play2char4(MouseEvent mouseEvent) {
        character2 = 4;
    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        betCoin = Integer.parseInt(bet.getText());
        Game foo = new Game();
        Game.player1 = player1;
        Game.player2 = player2;
        foo.bet = betCoin;
        player1.coin -= betCoin;
        player2.coin -= betCoin;
        foo.character1 = character1;
        foo.character2 = character2;
        foo.start(MainMenu.stage);
    }
}
