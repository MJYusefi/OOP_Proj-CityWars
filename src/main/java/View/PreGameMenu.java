package View;

import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PreGameMenu extends Application {
    Player player1;
    Player player2;
    int betCoin;
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
        System.out.println(player1.xp);
    }

    @FXML
    public TextField bet;

    public void play1char1(MouseEvent mouseEvent) {
        this.character1 = 1;
    }

    public void play1char3(MouseEvent mouseEvent) {
        this.character1 = 3;
    }

    public void play1char2(MouseEvent mouseEvent) {
        this.character1 = 2;
    }

    public void play1char4(MouseEvent mouseEvent) {
        this.character1 = 4;
    }

    public void play2char1(MouseEvent mouseEvent) {
        this.character2 = 1;
    }

    public void play2char3(MouseEvent mouseEvent) {
        this.character2 = 3;
    }

    public void play2char2(MouseEvent mouseEvent) {
        this.character2 = 2;
    }

    public void play2char4(MouseEvent mouseEvent) {
        this.character2 = 4;
    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        betCoin = Integer.parseInt(bet.getText());

        Game foo = new Game();

        foo.player1 = player1;
        foo.player2 = player2;
        foo.bet = betCoin;

//        player1.coin -= betCoin;
//        player2.coin -= betCoin;

        foo.character1 = character1;
        foo.character2 = character2;

        foo.start(MainMenu.stage);
    }
}
