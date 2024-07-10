package View;

import Model.Card;
import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Application {
    public AnchorPane root;
    public AnchorPane chart;
    public ImageView prof1;
    public ImageView prof2;
    public Label hp1;
    public Label hp2;
    public Label round1;
    public Label round2;
    public Label damage11;
    public Label damage22;
    public Rectangle hand2rec1;
    public Text hand2tex1;
    public Rectangle hand2rec2;
    public Text hand2tex2;
    public Rectangle hand2rec3;
    public Text hand2tex3;
    public Rectangle hand2rec4;
    public Text hand2tex4;
    public Rectangle hand2rec5;
    public Text hand2tex5;
    public Rectangle hand1rec1;
    public Text hand1tex1;
    public Rectangle hand1rec2;
    public Text hand1tex2;
    public Rectangle hand1rec3;
    public Text hand1tex3;
    public Rectangle hand1rec4;
    public Text hand1tex4;
    public Rectangle hand1rec5;
    public Text hand1tex5;
    public HBox hand22;
    public HBox hand11;
    Random rand = new Random();
    static Player player1;
    static Player player2;
    int character1;
    int character2;
    int damage1;
    int damage2;
    public static int[][] board1 = new int[2][21];
    public static int[][] board2 = new int[2][21];
    public ArrayList<Card> hand1 = new ArrayList<>();
    public ArrayList<Card> hand2 = new ArrayList<>();
    int remround1;
    int remround2;
    int bet;
    int lastClicked;
    int lastCard;



    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(Game.class.getResource("/FXML/Game.fxml"));
        Scene scene = new Scene(pane);
//        chartBuilder();
//        createHand();
        //upDamage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void initialize() {
        remround1=4;
        remround2=4;
        for (int i = 0; i < 5; i++) {
            int randInt = rand.nextInt(0, player1.deck.size());
            {
                hand1.add(player1.deck.get(randInt));
            }
        }
        for (int i = 0; i < 5; i++) {
            int randInt = rand.nextInt(0, player2.deck.size());
            {
                hand2.add(player2.deck.get(randInt));
            }
        }
        int randInt = rand.nextInt(0, 21);
        board1[0][randInt] = -2;
        randInt = rand.nextInt(0, 21);
        board2[0][randInt] = -2;

        Image prof11 = new Image(Game.class.getResource("/img/char1.png").toExternalForm());
        Image prof22 = new Image(Game.class.getResource("/img/char2.png").toExternalForm());
        Image prof33 = new Image(Game.class.getResource("/img/char3.png").toExternalForm());
        Image prof44 = new Image(Game.class.getResource("/img/char4.png").toExternalForm());
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
        createHand();


        int a = rand.nextInt(0, 2);
        if (a == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Player1 starts the game >>>");
            alert.showAndWait();
            lastClicked = 4;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Player2 starts the game >>>");
            alert.showAndWait();
            lastClicked = 2;
        }
        chartBuilder();
        createHand();
        upDamage();
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

    public void createHand() {
        for (int i = 0; i < hand2.size(); i++) {
            Card card = hand2.get(i);
            StackPane stack = (StackPane) hand22.getChildren().get(i);
            if (card.id < 100) {
                for (Node node : stack.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle rec = (Rectangle) node;
                        rec.setFill(Color.STEELBLUE);
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        text.setText(card.name + "\n\nDeffence/Attack: " + card.defAtt + "\n\nDuration: " + card.dur + "\n\nPlayerDamage: " + card.playDamage);
                        text.setFill(Color.WHITE);
                        text.setTextAlignment(TextAlignment.CENTER);
                    }
                }
            } else if (card.id == 101 || card.id == 102) {
                for (Node node : stack.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle rec = (Rectangle) node;
                        rec.setFill(Color.DARKSALMON);
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        text.setText(card.name + "\n\nDuration: " + card.dur);
                        text.setFill(Color.BLACK);
                        text.setTextAlignment(TextAlignment.CENTER);
                    }
                }

            } else {
                for (Node node : stack.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle rec = (Rectangle) node;
                        rec.setFill(Color.DARKSALMON);
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        text.setText(card.name);
                        text.setFill(Color.BLACK);
                        text.setTextAlignment(TextAlignment.CENTER);
                    }
                }
            }
        }

        for (int i = 0; i < hand1.size(); i++) {
            Card card = hand1.get(i);
            StackPane stack = (StackPane) hand11.getChildren().get(i);
            if (card.id < 100) {
                for (Node node : stack.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle rec = (Rectangle) node;
                        rec.setFill(Color.STEELBLUE);
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        text.setText(card.name + "\n\nDeffence/Attack: " + card.defAtt + "\n\nDuration: " + card.dur + "\n\nPlayerDamage: " + card.playDamage);
                        text.setFill(Color.WHITE);
                        text.setTextAlignment(TextAlignment.CENTER);
                    }
                }
            } else if (card.id == 101 || card.id == 102) {
                for (Node node : stack.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle rec = (Rectangle) node;
                        rec.setFill(Color.DARKSALMON);
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        text.setText(card.name + "\n\nDuration: " + card.dur);
                        text.setFill(Color.BLACK);
                        text.setTextAlignment(TextAlignment.CENTER);
                    }
                }

            } else {
                for (Node node : stack.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle rec = (Rectangle) node;
                        rec.setFill(Color.DARKSALMON);
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        text.setText(card.name);
                        text.setFill(Color.BLACK);
                        text.setTextAlignment(TextAlignment.CENTER);
                    }
                }
            }
        }
    }
    public void upDamage(){
        damage11.setText(Integer.toString(damage1));
        damage11.setTextAlignment(TextAlignment.CENTER);
        damage22.setText(Integer.toString(damage2));
        damage22.setTextAlignment(TextAlignment.CENTER);
        hp1.setText("HP: "+player1.hp);
        hp1.setTextAlignment(TextAlignment.CENTER);
        hp2.setText("HP: "+player1.hp);
        hp2.setTextAlignment(TextAlignment.CENTER);
        round1.setText(Integer.toString(remround1));
        round1.setTextAlignment(TextAlignment.CENTER);
        round2.setText(Integer.toString(remround2));
        round2.setTextAlignment(TextAlignment.CENTER);
    }

    public boolean selPla1(int i) {
        Card foo = hand1.get(lastCard - 1);
        boolean placed=false;
        switch (foo.name) {
            case "SHIELD" -> {
                if (checkEmpty1(foo, i)) {
                    for (int j = 0; j < foo.dur; j++) {
                        board1[0][i + j - 1] = 200;
                        board1[1][i + j - 1] = 0;
                    }
                    compNormal();
                    upDam();
                    remround1 -= 1;
                    placed = true;
                    hand1.remove(foo);
                    int randInt = rand.nextInt(0, player1.deck.size());
                    hand1.add(player1.deck.get(randInt));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Not enough space!");
                    alert.showAndWait();
                }
            }
            case "HEAL" -> {
                if (checkEmpty1(foo, i)) {
                    for (int j = 0; j < foo.dur; j++) {
                        board1[0][i + j - 1] = 300;
                        board1[1][i + j - 1] = 0;
                    }
                    player1.hp += 100;
                    remround1 -= 1;
                    placed = true;
                    hand1.remove(foo);
                    int randInt = rand.nextInt(0, player1.deck.size());
                    hand1.add(player1.deck.get(randInt));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Not enough space!");
                    alert.showAndWait();
                }
            }
            case "BOOSTER" -> {
                int a = rand.nextInt(0, hand1.size());
                while (hand1.get(a).id > 100) {
                    a = rand.nextInt(0, hand2.size());
                }
                hand1.get(a).playDamage += 10;
                remround1 -= 1;
                placed = true;
                hand1.remove(foo);
                int randInt = rand.nextInt(0, player1.deck.size());
                hand1.add(player1.deck.get(randInt));
            }
            case "HOLE" -> {
                for (int j = 0; j < 21; j++) {
                    if (board1[0][j] == -2) {
                        board1[0][j] = 0;
                        break;
                    }
                }
                int a = rand.nextInt(0, 21);
                while (board1[0][a] != 0) {
                    a = rand.nextInt(0, 21);
                }
                board1[0][a] = -2;
                remround1 -= 1;
                placed = true;
                hand1.remove(foo);
                int randInt = rand.nextInt(0, player1.deck.size());
                hand1.add(player1.deck.get(randInt));
            }
            case "FIXER" -> {
                for (int j = 0; j < 21; j++) {
                    if (board1[0][j] == -2) {
                        board1[0][j] = 0;
                        break;
                    }
                }
                remround1 -= 1;
                placed = true;
                hand1.remove(foo);
                int randInt = rand.nextInt(0, player1.deck.size());
                hand1.add(player1.deck.get(randInt));
            }
            case "RoundReducer" -> {
                remround1 -= 2;
                remround2 -= 1;
                placed = true;
                hand1.remove(foo);
                int randInt = rand.nextInt(0, player1.deck.size());
                hand1.add(player1.deck.get(randInt));
            }
            case "WeakenOppCard" -> {
                int a = rand.nextInt(0, hand2.size());
                while (hand2.get(a).id > 100) {
                    a = rand.nextInt(0, hand2.size());
                }
                hand2.get(a).defAtt -= 10;

                int b = rand.nextInt(0, hand2.size());
                while (hand2.get(b).id > 100) {
                    b = rand.nextInt(0, hand2.size());
                }
                hand2.get(b).playDamage -= 10;
                remround1 -= 1;
                placed = true;
                hand1.remove(foo);
                int randInt = rand.nextInt(0, player1.deck.size());
                hand1.add(player1.deck.get(randInt));
            }
            default -> {
                if (checkEmpty1(foo, i)) {
                    for (int j = 0; j < foo.dur; j++) {
                        board1[0][i + j - 1] = foo.defAtt;
                        board1[1][i + j - 1] = foo.playDamage / foo.dur;
                    }
                    compNormal();
                    upDam();
                    remround1 -= 1;
                    placed = true;
                    hand1.remove(foo);
                    int randInt = rand.nextInt(0, player1.deck.size());
                    hand1.add(player1.deck.get(randInt));

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Not enough space!");
                    alert.showAndWait();
                }
            }
        }
        upDamage();
        return placed;
    }
    public boolean selPla2(int i) {
        Card foo = hand2.get(lastCard - 1);
        boolean placed = false;
        switch (foo.name) {
            case "SHIELD" -> {
                if (checkEmpty2(foo, i)) {
                    for (int j = 0; j < foo.dur; j++) {
                        board2[0][i + j - 1] = 200;
                        board2[1][i + j - 1] = 0;
                    }
                    compNormal();
                    upDam();
                    remround2 -= 1;
                    placed = true;
                    hand2.remove(foo);
                    int randInt = rand.nextInt(0, player2.deck.size());
                    hand2.add(player2.deck.get(randInt));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Not enough space!");
                    alert.showAndWait();
                }
            }
            case "HEAL" -> {
                if (checkEmpty2(foo, i)) {
                    for (int j = 0; j < foo.dur; j++) {
                        board2[0][i + j - 1] = 300;
                        board2[1][i + j - 1] = 0;
                    }
                    player2.hp += 100;
                    remround2 -= 1;
                    placed = true;
                    hand2.remove(foo);
                    int randInt = rand.nextInt(0, player2.deck.size());
                    hand2.add(player2.deck.get(randInt));

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Not enough space!");
                    alert.showAndWait();
                }
            }
            case "BOOSTER" -> {
                int a = rand.nextInt(0, hand2.size());
                while (hand2.get(a).id > 100) {
                    a = rand.nextInt(0, hand2.size());
                }
                hand2.get(a).playDamage += 10;
                remround2 -= 1;
                placed = true;
                hand2.remove(foo);
                int randInt = rand.nextInt(0, player2.deck.size());
                hand2.add(player2.deck.get(randInt));
            }
            case "HOLE" -> {
                for (int j = 0; j < 21; j++) {
                    if (board2[0][j] == -2) {
                        board2[0][j] = 0;
                        break;
                    }
                }
                int a = rand.nextInt(0, 21);
                while (board2[0][a] != 0) {
                    a = rand.nextInt(0, 21);
                }
                board2[0][a] = -2;
                remround2 -= 1;
                placed = true;
                hand2.remove(foo);
                int randInt = rand.nextInt(0, player2.deck.size());
                hand2.add(player2.deck.get(randInt));
            }
            case "FIXER" -> {
                for (int j = 0; j < 21; j++) {
                    if (board2[0][j] == -2) {
                        board2[0][j] = 0;
                        break;
                    }
                }
                remround2 -= 1;
                placed = true;
                hand2.remove(foo);
                int randInt = rand.nextInt(0, player2.deck.size());
                hand2.add(player2.deck.get(randInt));
            }
            case "RoundReducer" -> {
                remround1 -= 1;
                remround2 -= 2;
                placed = true;
                hand2.remove(foo);
                int randInt = rand.nextInt(0, player2.deck.size());
                hand2.add(player2.deck.get(randInt));
            }
            case "WeakenOppCard" -> {
                int a = rand.nextInt(0, hand1.size());
                while (hand1.get(a).id > 100) {
                    a = rand.nextInt(0, hand1.size());
                }
                hand1.get(a).defAtt -= 10;

                int b = rand.nextInt(0, hand1.size());
                while (hand1.get(b).id > 100) {
                    b = rand.nextInt(0, hand1.size());
                }
                hand1.get(b).playDamage -= 10;
                remround2 -= 1;
                placed = true;
                hand2.remove(foo);
                int randInt = rand.nextInt(0, player2.deck.size());
                hand2.add(player2.deck.get(randInt));
            }
            default -> {
                if (checkEmpty2(foo, i)) {
                    for (int j = 0; j < foo.dur; j++) {
                        board2[0][i + j - 1] = foo.defAtt;
                        board2[1][i + j - 1] = foo.playDamage / foo.dur;
                    }
                    compNormal();
                    upDam();
                    remround2 -= 1;
                    placed = true;
                    hand2.remove(foo);
                    int randInt = rand.nextInt(0, player2.deck.size());
                    hand2.add(player2.deck.get(randInt));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Not enough space!");
                    alert.showAndWait();
                }
            }
        }
        upDamage();
        return placed;
    }

    public boolean checkEmpty1(Card card, int i) {
        if (card.dur + i - 2 > 20) return false;
        for (int j = 0; j < card.dur && i + j - 1 < board1[0].length; j++) {
            if (board1[0][i + j - 1] < 0) return false;
        }
        return true;
    }

    public boolean checkEmpty2(Card card, int i) {
        if (card.dur + i - 2 > 20) return false;
        for (int j = 0; j < card.dur && i + j - 1 < board2[0].length; j++) {
            if (board2[0][i + j - 1] != 0) return false;
        }
        return true;
    }

    public void compNormal() {
        for (int i = 0; i < 21; i++) {
            if (board1[0][i] == 300 || board2[0][i] == 300) {
                continue;
            } else if (board1[0][i] > board2[0][i] && board1[0][i] > 0 && board2[0][i] > 0) {
                board2[0][i] = -1;
                board2[1][i] = 0;
            } else if (board1[0][i] < board2[0][i] && board1[0][i] > 0 && board2[0][i] > 0) {
                board1[0][i] = -1;
                board1[1][i] = 0;

            } else if (board1[0][i] == board2[0][i] && board1[0][i] > 0 && board2[0][i] > 0) {
                board1[0][i] = -1;
                board2[0][i] = -1;
                board1[1][i] = 0;
                board2[1][i] = 0;
            }
        }
//        hashCheck1();
//        hashCheck2();
    }

    public void upDam() {
        damage1 = 0;
        for (int i = 0; i < 21; i++) {
            damage1 += board1[1][i];
        }
        damage2 = 0;
        for (int i = 0; i < 21; i++) {
            damage2 += board2[1][i];
        }
    }


    public void hand2card1(MouseEvent mouseEvent) {
        if (lastClicked == 2) {
            lastCard = 1;
            lastClicked = 3;
        }
    }

    public void hand2card2(MouseEvent mouseEvent) {
        if (lastClicked == 2) {
            lastCard = 2;
            lastClicked = 3;
        }
    }

    public void hand2card3(MouseEvent mouseEvent) {
        if (lastClicked == 2) {
            lastCard = 3;
            lastClicked = 3;
        }
    }

    public void hand2card4(MouseEvent mouseEvent) {
        if (lastClicked == 2) {
            lastCard = 4;
            lastClicked = 3;
        }
    }

    public void hand2card5(MouseEvent mouseEvent) {
        if (lastClicked == 2) {
            lastCard = 5;
            lastClicked = 3;
        }
    }

    //***************************************************

    public void hand1card1(MouseEvent mouseEvent) {
        if (lastClicked == 4) {
            lastCard = 1;
            lastClicked = 1;
        }
    }

    public void hand1card2(MouseEvent mouseEvent) {
        if (lastClicked == 4) {
            lastCard = 2;
            lastClicked = 1;
        }
    }

    public void hand1card3(MouseEvent mouseEvent) {
        if (lastClicked == 4) {
            lastCard = 3;
            lastClicked = 1;
        }
    }

    public void hand1card4(MouseEvent mouseEvent) {
        if (lastClicked == 4) {
            lastCard = 4;
            lastClicked = 1;
        }
    }

    public void hand1card5(MouseEvent mouseEvent) {
        if (lastClicked == 4) {
            lastCard = 5;
            lastClicked = 1;
        }
    }

    //***************************************************

    public void b2_1(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(1)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }

    public void b2_2(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(2)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }


    public void b2_3(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(3)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }


    public void b2_4(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(4)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }

    public void b2_5(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(5)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }

    public void b2_6(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(6)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }

    public void b2_7(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(7)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_8(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(8)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }


    public void b2_9(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 9) {
            if (selPla2(2)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }

    public void b2_10(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 10) {
            if (selPla2(2)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }

    public void b2_11(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(11)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_12(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(12)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }

    }

    public void b2_13(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(13)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_14(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(14)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_15(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(15)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_16(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(16)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_17(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(17)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_18(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(18)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }


    public void b2_19(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(19)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b2_20(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(20)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }


    public void b2_21(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 3) {
            if (selPla2(21)){
                lastClicked=4;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    //***************************************************

    public void b1_1(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(1)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_2(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(2)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_3(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(3)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_4(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(4)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_5(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(5)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_6(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 6) {
            if (selPla1(1)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_7(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(7)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_8(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(8)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }


    public void b1_9(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(9)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_10(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(10)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_11(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(11)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_12(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(12)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_13(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(13)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_14(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(14)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_15(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(15)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_16(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(16)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_17(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(17)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_18(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(18)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_19(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(19)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_20(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(20)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);

                }
            }

        }
    }

    public void b1_21(MouseEvent mouseEvent) throws Exception {
        if (lastClicked == 1) {
            if (selPla1(21)){
                lastClicked=2;
                createHand();
                chartBuilder();
                if (remround1==0&&remround2==0){
                    CombatMenu foo=new CombatMenu();
                    CombatMenu.player1 =player1;
                    CombatMenu.player2 =player2;
                    foo.bet=bet;
                    foo.character1=character1;
                    foo.character2=character2;
                    foo.board1=board1;
                    foo.board2=board2;
                    foo.start(MainMenu.stage);
                }
            }
        }
    }
}

