package View;

//import Model.Arrow;
import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CombatMenu extends Application {
Pane pane;
    public static Player player1;
    public static Player player2;
    public Label hp2;
    public Label hp1;
    public Label damage22;
    @FXML
    private Label damage11;
    public AnchorPane chart;

    public static int[][] board1;
    public static int[][] board2;
    int character1;
    int character2;
    int damage1;
    int damage2;
    int bet;

    @Override
    public void start(Stage stage) throws Exception {

        pane = FXMLLoader.load(CombatMenu.class.getResource("/FXML/CombatMenu.fxml"));
        Scene scene = new Scene(pane);


//        Arrow arrow=new Arrow(80-15,0);
//        arrow.arrowAnim=new ArrowAnim(arrow,board1, board2,this,this.damage11, player1, player2);
//        arrow.arrowAnim.play();
//        pane.getChildren().add(arrow);



        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    public void initialize() {

        damage11=new Label();
        chartBuilder();

    }

    public void chartBuilder() {
        for (int i = 84; i < chart.getChildren().size(); i++) {
            chart.getChildren().remove(chart.getChildren().get(i));
        }
        for (int i = 0; i < 21; i++) {
            if (board1[0][i] == -1) {
                Rectangle rect1 = new Rectangle(39, 61, Color.GRAY);
                Rectangle rect2 = new Rectangle(39, 61, Color.GRAY);
                Text text1 = new Text("??");
                Text text2 = new Text("00");
                text1.setFill(Color.BLACK);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.BLACK);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(125);
                stackPane2.setLayoutY(187.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);
            } else if (board1[0][i] == -2) {
                Rectangle rect = new Rectangle(i * 40, 125, 40, 125);
                rect.setFill(Color.BLACK);
                chart.getChildren().add(rect);
            } else if (board1[0][i] == 200) {
                Rectangle rect1 = new Rectangle(39, 61, Color.DARKSALMON);
                Rectangle rect2 = new Rectangle(39, 61, Color.DARKSALMON);
                Text text1 = new Text("SH");
                Text text2 = new Text("00");
                text1.setFill(Color.BLACK);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.BLACK);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(125);
                stackPane2.setLayoutY(187.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);
            } else if (board1[0][i] == 300) {
                Rectangle rect1 = new Rectangle(39, 61, Color.DARKSALMON);
                Rectangle rect2 = new Rectangle(39, 61, Color.DARKSALMON);
                Text text1 = new Text("HL");
                Text text2 = new Text("00");
                text1.setFill(Color.BLACK);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.BLACK);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(125);
                stackPane2.setLayoutY(187.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);

            } else if (board1[0][i] == 0) {
                continue;
            } else {
                Rectangle rect1 = new Rectangle(39, 61, Color.STEELBLUE);
                Rectangle rect2 = new Rectangle(39, 61, Color.STEELBLUE);
                Text text1 = new Text(Integer.toString(board1[0][i]));
                Text text2 = new Text(Integer.toString(board1[1][i]));
                text1.setFill(Color.WHITE);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.WHITE);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(125);
                stackPane2.setLayoutY(187.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);
            }
        }
        for (int i = 0; i < 21; i++) {
            if (board2[0][i] == -1) {
                Rectangle rect1 = new Rectangle(39, 61, Color.GRAY);
                Rectangle rect2 = new Rectangle(39, 61, Color.GRAY);
                Text text1 = new Text("??");
                Text text2 = new Text("00");
                text1.setFill(Color.BLACK);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.BLACK);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(0);
                stackPane2.setLayoutY(62.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);
            } else if (board2[0][i] == -2) {
                Rectangle rect = new Rectangle(i * 40, 0, 40, 125);
                rect.setFill(Color.BLACK);
                chart.getChildren().add(rect);
            } else if (board2[0][i] == 200) {
                Rectangle rect1 = new Rectangle(39, 61, Color.DARKSALMON);
                Rectangle rect2 = new Rectangle(39, 61, Color.DARKSALMON);
                Text text1 = new Text("SH");
                Text text2 = new Text("00");
                text1.setFill(Color.BLACK);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.BLACK);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(0);
                stackPane2.setLayoutY(62.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);
            } else if (board2[0][i] == 300) {
                Rectangle rect1 = new Rectangle(39, 61, Color.DARKSALMON);
                Rectangle rect2 = new Rectangle(39, 61, Color.DARKSALMON);
                Text text1 = new Text("HL");
                Text text2 = new Text("00");
                text1.setFill(Color.BLACK);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.BLACK);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(0);
                stackPane2.setLayoutY(62.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);
            } else if (board2[0][i] == 0) {
                continue;
            } else {
                Rectangle rect1 = new Rectangle(39, 61, Color.STEELBLUE);
                Rectangle rect2 = new Rectangle(39, 61, Color.STEELBLUE);
                Text text1 = new Text(Integer.toString(board2[0][i]));
                Text text2 = new Text(Integer.toString(board2[1][i]));
                text1.setFill(Color.WHITE);
                text1.setTextAlignment(TextAlignment.CENTER);
                text2.setFill(Color.WHITE);
                text2.setTextAlignment(TextAlignment.CENTER);
                StackPane stackPane1 = new StackPane();
                StackPane stackPane2 = new StackPane();
                stackPane1.getChildren().addAll(rect1, text1);
                stackPane2.getChildren().addAll(rect2, text2);
                stackPane1.setLayoutX(i * 40);
                stackPane2.setLayoutX(i * 40);
                stackPane1.setLayoutY(0);
                stackPane2.setLayoutY(62.5);
                chart.getChildren().add(stackPane1);
                chart.getChildren().add(stackPane2);
            }
        }
    }


}
