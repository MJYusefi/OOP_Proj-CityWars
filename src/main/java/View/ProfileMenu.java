package View;

import Controller.AuthController;
import Controller.Database;
import Model.Card;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ChangeListener;

import java.io.IOException;
import java.sql.SQLException;


public class ProfileMenu extends Application {

    @FXML
    private Text username;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nicknameField;

    @FXML
    private Text coin;

    @FXML
    private ProgressBar xpProgressBar;

    @FXML
    private Text xp;

    @FXML
    private Text hp;

    @FXML
    private ListView<String> cardsListView;

    @FXML
    private ImageView cardImage;

    @FXML
    private Text cardName;

    @FXML
    private Text cardLevel;

    @FXML
    private Text cardDamage;

    @FXML
    private Text cardDuration;

    @FXML
    private Text cardDefense;

    public static Stage stage;

    @FXML
    private void initialize() {
        // Example user data
        username.setText("Welcome " + AuthController.LoginUser.getUsername());
        emailField.setText(AuthController.LoginUser.getEmail());
        nicknameField.setText(AuthController.LoginUser.getNickname());
        coin.setText(String.valueOf(AuthController.LoginUser.coin) + " Coins");
        xp.setText(STR."\{AuthController.LoginUser.xp} / 2000  XP");
        xpProgressBar.setProgress((double) AuthController.LoginUser.xp /2000);
        hp.setText(String.valueOf(AuthController.LoginUser.hp) + " HP");

        // Example deck cards
        ObservableList<String> cards = FXCollections.observableArrayList();
        for(Card i : AuthController.LoginUser.deck) cards.add(i.name);

        cardsListView.setItems(cards);

        cardsListView.setOnMouseClicked(this::onCardSelected);

        // Add listeners to detect changes in TextFields
        emailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                AuthController.LoginUser.setEmail(emailField.getText());
            }
        });

        nicknameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                AuthController.LoginUser.setEmail(nicknameField.getText());
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/ProfileMenu.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void onCardSelected(MouseEvent event) {
        String selectedCard = cardsListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            Card tmp = Database.getCardByName(selectedCard);
            cardName.setText(tmp.name);
            cardLevel.setText(String.valueOf(tmp.curlevel));
            if(tmp.playDamage == -1) cardDamage.setText("*");
            else cardDamage.setText(String.valueOf(tmp.playDamage));
            if(tmp.dur == -1) cardDamage.setText("*");
            else cardDuration.setText(String.valueOf(tmp.dur));
            if(tmp.defAtt == -1) cardDamage.setText("*");
            else cardDefense.setText(String.valueOf(tmp.defAtt));
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("/img/Avatar.jpg"))));
        }
    }

    public void back(MouseEvent mouseEvent) throws SQLException, IOException {
        Database.SaveEditInDB(AuthController.LoginUser);
        new MainMenu().start(stage);
    }
}
