package View;


import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainMenu extends Application {

    public static Stage stage;
    Player player1;    Player player2;

    @Override
    public void start(Stage stage) throws Exception {
        player1=new Player(101,"emadfiroozi", "emad3183", "em", "e@f.com", "yes", "yes", 1, 100, 100);
        player2=new Player(102,"javad", "emad3183", "em", "e@f.com", "yes", "yes", 2, 50, 200);
        MainMenu.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/MainMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void startPreGameMenu(MouseEvent mouseEvent) throws Exception {
        PreGameMenu foo = new PreGameMenu();
        foo.player1=player1;
        foo.player2=player2;
        foo.start(stage);
    }
}