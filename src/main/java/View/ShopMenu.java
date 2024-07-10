package View;

import Controller.AuthController;
import Controller.Database;
import Model.Card;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class ShopMenu extends Application {
    @FXML
    private Text coinsText;

    @FXML
    private ListView<String> cardsListView;

    @FXML
    private ImageView cardImage;

    @FXML
    private ImageView lockedStatusImage;

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

    @FXML
    private Text cardCost;

    @FXML
    private Text cardUpgradeLevel;

    @FXML
    private Button actionButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Example user data
        coinsText.setText(AuthController.LoginUser.coin + " Coins");

        // Example cards data
        ObservableList<String> cards = FXCollections.observableArrayList();
        for(Card i : Database.AllCards) cards.add(i.name);

        cardsListView.setItems(cards);

        // Set the listener for card selection
        cardsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    if(isUnlocked(Database.getCardByName(newValue))) actionButton.setText("Upgrade");
                    else actionButton.setText("Buy");
                    displayCardDetails(Database.getCardByName(newValue));
                }
            }
        });

        // Set back button action
        backButton.setOnAction(event -> {
            try {
                handleBackButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Set action button action
        actionButton.setOnAction(event -> handleActionButton());
    }
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/ShopMenu.fxml"));
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void displayCardDetails(Card tmp) {
        if (isUnlocked(tmp)) {
            cardName.setText(tmp.name);
            cardLevel.setText(String.valueOf(tmp.curlevel));
            if(tmp.playDamage == -1) cardDamage.setText("*");
            else cardDamage.setText(String.valueOf(tmp.playDamage));
            if(tmp.dur == -1) cardDamage.setText("*");
            else cardDuration.setText(String.valueOf(tmp.dur));
            if(tmp.defAtt == -1) cardDamage.setText("*");
            else cardDefense.setText(String.valueOf(tmp.defAtt));
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("/img/Avatar.jpg"))));
            lockedStatusImage.setImage(new Image(String.valueOf(getClass().getResource("/img/unlocked.png"))));
        }
        else {
            cardName.setText(tmp.name);
            cardLevel.setText(String.valueOf(tmp.curlevel));
            if(tmp.playDamage == -1) cardDamage.setText("*");
            else cardDamage.setText(String.valueOf(tmp.playDamage));
            if(tmp.dur == -1) cardDamage.setText("*");
            else cardDuration.setText(String.valueOf(tmp.dur));
            if(tmp.defAtt == -1) cardDamage.setText("*");
            else cardDefense.setText(String.valueOf(tmp.defAtt));
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("/img/Avatar.jpg"))));
            lockedStatusImage.setImage(new Image(String.valueOf(getClass().getResource("/img/lock.png"))));
        }
    }

    private void handleActionButton() {
        String selectedCard = cardsListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            if (isUnlocked(Database.getCardByName(selectedCard))) {
               if(AuthController.LoginUser.coin > pow(AuthController.LoginUser.getCardFromDeckByName(selectedCard).curlevel,Database.getCardByName(selectedCard).upCost)){
                   if(AuthController.LoginUser.level > Database.getCardByName(selectedCard).upLevel){
                       AuthController.LoginUser.getCardFromDeckByName(selectedCard).curlevel++;
                       AuthController.LoginUser.coin -= pow(AuthController.LoginUser.getCardFromDeckByName(selectedCard).curlevel,Database.getCardByName(selectedCard).upCost);
                   }
               }
            }
            else {
                if(AuthController.LoginUser.coin > Database.getCardByName(selectedCard).upCost) {
                    if (AuthController.LoginUser.level > Database.getCardByName(selectedCard).upLevel) {
                        AuthController.LoginUser.deck.add(Database.getCardByName(selectedCard));
                        AuthController.LoginUser.coin -= Database.getCardByName(selectedCard).upCost;
                    }
                }
            }
        }
    }

    private void handleBackButton() throws IOException {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(stage);
    }

    public static boolean isUnlocked(Card c){
        for(Card i : AuthController.LoginUser.deck)
            if(i.name.equals(c.name)) return false;
        return true;
    }

    public int pow(int p, int base){
        int ans = 1;
        for(int i = 0 ; i < p ; i++) ans *= base;
        return ans;
    }
}
