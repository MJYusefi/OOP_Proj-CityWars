package View;

import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class WinLoseMenu extends Application {
    String jdbcUrl = "jdbc:mysql://localhost:3306/citywars";
    String user = "root";
    String pass = "Mohammad6900";
    public static Player winner;
    public static Player loser;
    public static Player main;
    public static int characterWin;
    public static int characterLose;
    @FXML
    public ImageView prof1;
    @FXML
    public ImageView prof2;
    int winDam;
    int loseDam;
    static int bet;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(WinLoseMenu.class.getResource("/FXML/WinLoseMenu.fxml")));

        if (loser.hp<0) loser.hp=0;
        if (winner.hp<0) winner.hp=0;
        String win="";
        String lose="";
        winner.coin+=2*bet;
        winner.xp=winDam*2-loseDam;
        loser.xp=loseDam*2-winDam;
        if (winner.xp>=winner.level*100){
            winner.xp-=winner.level*100;
            winner.level+=1;
            win+=(winner.getNickname()+" LEVEL UP!\n");
        }
        if (loser.xp>=loser.level*100){
            loser.xp-=loser.level*100;
            loser.level+=1;
            lose+=(loser.getNickname()+" LEVEL UP!\n");
        }
        winner.hp=winner.level*100;
        loser.hp=loser.level*100;
        upPlayer();
        upHistory();
        win+=(STR."*****>>> Winner: \{winner.getNickname()} <<<*****\nXP: \{winDam * 2 - loseDam}\nCoins: +\{bet}");
        lose+=(STR."+++++<<< Loser: \{loser.getNickname()} >>>+++++\nXP: \{loseDam * 2 - winDam}\nCoins: -\{bet}");

        Label winnerText=new Label();
        winnerText.setLayoutX(450);
        winnerText.setLayoutY(30);
        winnerText.setTextAlignment(TextAlignment.CENTER);
        winnerText.setFont(new Font(24));
        Label loserText=new Label();
        loserText.setLayoutX(450);
        loserText.setLayoutY(280);
        loserText.setTextAlignment(TextAlignment.CENTER);
        loserText.setFont(new Font(24));
        winnerText.setText(win);
        loserText.setText(lose);
        pane.getChildren().add(winnerText);
        pane.getChildren().add(loserText);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

    }
    public void initialize(){
        Image prof11 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char1.png")).toExternalForm());
        Image prof22 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char2.png")).toExternalForm());
        Image prof33 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char3.png")).toExternalForm());
        Image prof44 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char4.png")).toExternalForm());
        switch (characterWin){
            case 1:
                prof1.setImage(prof11);
                break;
            case 2:
                prof1.setImage(prof22);
                break;
            case 3:
                prof1.setImage(prof33);
                break;
            case 4:
                prof1.setImage(prof44);
                break;
        }
        switch (characterLose){
            case 1:
                prof2.setImage(prof11);
                break;
            case 2:
                prof2.setImage(prof22);
                break;
            case 3:
                prof2.setImage(prof33);
                break;
            case 4:
                prof2.setImage(prof44);
                break;
        }
    }

    public void back() throws Exception {
        MainMenu foo=new MainMenu();
        int[][] board11 = new int[2][21];
        int[][] board22 = new int[2][21];
        Game.board1 =board11;
        Game.board2 =board22;
        MainMenu.player1 =main;
        foo.start(MainMenu.stage);
    }

    public void upHistory() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrl, user, pass);
        String insertSQL1 = "INSERT INTO history (username, time, result, opp, oppLev, xpAdd, coinAdd) VALUES (?,?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(insertSQL1);
        LocalDateTime currentDateTime1 = LocalDateTime.now();
        preparedStatement1.setString(1, winner.getUsername());
        preparedStatement1.setObject(2, currentDateTime1);
        preparedStatement1.setBoolean(3, true);
        preparedStatement1.setString(4, loser.getNickname());
        preparedStatement1.setInt(5, loser.level);
        preparedStatement1.setInt(6, winDam*2-loseDam);
        preparedStatement1.setInt(7, bet);
        preparedStatement1.executeUpdate();


        String insertSQL2 = "INSERT INTO history (username, time, result, opp, oppLev, xpAdd, coinAdd) VALUES (?,?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement2 = connection.prepareStatement(insertSQL2);
        LocalDateTime currentDateTime2 = LocalDateTime.now();
        preparedStatement2.setString(1, loser.getUsername());
        preparedStatement2.setObject(2, currentDateTime2);
        preparedStatement2.setBoolean(3, false);
        preparedStatement2.setString(4, winner.getNickname());
        preparedStatement2.setInt(5, winner.level);
        preparedStatement2.setInt(6, loseDam*2-winDam);
        preparedStatement2.setInt(7, -bet);
        preparedStatement2.executeUpdate();


    }

    public void upPlayer() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrl, user, pass);

        String updateQuery1 = "UPDATE users SET level=?, xp=?, coin=? WHERE username=?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(updateQuery1);
        preparedStatement1.setInt(1, winner.level);
        preparedStatement1.setInt(2, winner.xp);
        preparedStatement1.setInt(3, winner.coin);
        preparedStatement1.setString(4, winner.getUsername());
        preparedStatement1.executeUpdate();


        String updateQuery2 = "UPDATE users SET level=?, xp=?, coin=? WHERE username=?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(updateQuery2);
        preparedStatement2.setInt(1, loser.level);
        preparedStatement2.setInt(2, loser.xp);
        preparedStatement2.setInt(3, loser.coin);
        preparedStatement2.setString(4, loser.getUsername());
        preparedStatement2.executeUpdate();
    }
}
