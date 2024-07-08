package View;

import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application {
    Player player1;
    Player player2;
    int character1;
    int character2;
    public int[][] board1 = new int[2][21];
    public int[][] board2 = new int[2][21];
    int remround1;
    int remround2;
    int damage1;
    int damage2;
    int bet;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(Game.class.getResource("/FXML/Game.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
