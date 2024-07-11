package View;

import Model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javafx.util.Duration;
import javafx.application.Platform;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class CombatMenu extends Application {
    @FXML
    public AnchorPane anpane;
    @FXML
    public AnchorPane chart;
    @FXML
    public Label hp2;
    @FXML
    public Label hp1;
    @FXML
    public Label damage22;
    @FXML
    private Label damage11;
    @FXML
    public ImageView prof2;
    @FXML
    public ImageView prof1;
    public static Player player1;
    public static Player player2;
    public static int[][] board1;
    public static int[][] board2;
    static int character1;
    static int character2;
    int damage1;
    int damage2;
    static int bet;
    private ProgressBar progressBar;
    private Label percentageLabel;
    private Timeline timeline;
    private boolean progressBarStarted = false;
    private boolean progressBarStopped = false;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(CombatMenu.class.getResource("/FXML/CombatMenu.fxml")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    public void initialize() {
        Image prof11 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char1.png")).toExternalForm());
        Image prof22 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char2.png")).toExternalForm());
        Image prof33 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char3.png")).toExternalForm());
        Image prof44 = new Image(Objects.requireNonNull(Game.class.getResource("/img/char4.png")).toExternalForm());
        switch (character1){
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
        switch (character2){
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
        chartBuilder();
        Rectangle arrow=new Rectangle(30,50);
        arrow.setX(80-15);
        arrow.setY(175-50);
        arrow.setFill(new ImagePattern(new Image(Objects.requireNonNull(CombatMenu.class.getResource("/img/arrow.png")).toExternalForm())));
        anpane.getChildren().add(arrow);

        progressBar = new ProgressBar(0);
        progressBar.setPrefSize(500, 50);
        progressBar.setLayoutX(300);
        progressBar.setLayoutY(600-((double) 175 /2)-25);
        progressBar.setVisible(false);
        progressBar.setStyle("-fx-accent: #00FF00;");

        percentageLabel = new Label("0.00%");
        percentageLabel.setLayoutX(800);
        percentageLabel.setLayoutY(progressBar.getLayoutY());
        percentageLabel.setVisible(false);
        percentageLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: black;");

        anpane.getChildren().addAll(progressBar, percentageLabel);

        timeline = createTimeline();
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
    public void click() throws Exception {
        Rectangle foo= (Rectangle)anpane.getChildren().get(anpane.getChildren().size()-3);
        foo.setX(foo.getX()+40);

        if (foo.getX() >= 500 && !progressBarStarted) {
            progressBar.setVisible(true);
            percentageLabel.setVisible(true);
            timeline.play();
            progressBarStarted = true;
        } else if (progressBarStarted && !progressBarStopped) {
            timeline.stop();
            double finalPercentage = progressBar.getProgress()*100;
            if (damage1>damage2){
                player1.hp*= (int) (progressBar.getProgress()*2);
                upDamage();
            }
            if (damage2>damage1){
                player2.hp*= (int) (progressBar.getProgress()*2);
                upDamage();
            }
            percentageLabel.setText(String.format("%.2f%%", finalPercentage));
            progressBarStopped = true;
        }
        damage1+=board1[1][(int) (foo.getX()-80)/40];
        damage2+=board2[1][(int) (foo.getX()-80)/40];
        player2.hp-=board1[1][(int) (foo.getX()-80)/40];
        player1.hp-=board2[1][(int) (foo.getX()-80)/40];
        upDamage();
        if (player1.hp<=0) {
            WinLoseMenu bar=new WinLoseMenu();
            WinLoseMenu.winner =player2;
            WinLoseMenu.loser =player1;
            WinLoseMenu.main=player1;
            WinLoseMenu.characterWin=character2;
            WinLoseMenu.characterLose=character1;
            WinLoseMenu.bet =bet;
            bar.winDam=damage2;
            bar.loseDam=damage1;
            bar.start(MainMenu.stage);
        }
        else if (player2.hp<=0) {
            WinLoseMenu bar=new WinLoseMenu();
            WinLoseMenu.winner =player1;
            WinLoseMenu.loser =player2;
            WinLoseMenu.main=player1;
            WinLoseMenu.characterWin=character1;
            WinLoseMenu.characterLose=character2;
            WinLoseMenu.bet =bet;
            bar.winDam=damage1;
            bar.loseDam=damage2;
            bar.start(MainMenu.stage);
        }
        if (foo.getX()>=880){
            Game bar=new Game();
            Game.player1 = player1;
            Game.player2 = player2;
            Game.bet = bet;
            Game.character1 = character1;
            Game.character2 = character2;

            int[][] board11 = new int[2][21];
            int[][] board22 = new int[2][21];

            Game.board1 =board11;
            Game.board2 =board22;
            bar.start(MainMenu.stage);
        }
    }

    public void upDamage(){
        damage11.setText(Integer.toString(damage1));
        damage11.setTextAlignment(TextAlignment.CENTER);
        damage22.setText(Integer.toString(damage2));
        damage22.setTextAlignment(TextAlignment.CENTER);
        hp1.setText("HP: "+player1.hp);
        hp1.setTextAlignment(TextAlignment.CENTER);
        hp2.setText("HP: "+player2.hp);
        hp2.setTextAlignment(TextAlignment.CENTER);
    }

    private Timeline createTimeline() {
        AtomicBoolean increasing = new AtomicBoolean(true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(20), _ -> {
                    double currentProgress = progressBar.getProgress();
                    if (increasing.get()) {
                        if (currentProgress < 1) {
                            progressBar.setProgress(currentProgress + 0.005);
                        } else {
                            increasing.set(false);
                        }
                    } else {
                        if (currentProgress > 0) {
                            progressBar.setProgress(currentProgress - 0.005);
                        } else {
                            increasing.set(true);
                        }
                    }
                    updatePercentageLabel();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    private void updatePercentageLabel() {
        double percentage = progressBar.getProgress() * 100;
        Platform.runLater(() -> percentageLabel.setText(String.format("%.2f%%", percentage)));
    }

}
