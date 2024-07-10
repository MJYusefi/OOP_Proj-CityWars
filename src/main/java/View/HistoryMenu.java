package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryMenu extends Application {
    public static class HistoryRecord {
        private String username;
        private String time;
        private boolean result;
        private String opp;
        private int oppLev;
        private int xpAdd;
        private int coinAdd;

        public HistoryRecord(String username, String time, boolean result, String opp, int oppLev, int xpAdd, int coinAdd) {
            this.username = username;
            this.time = time;
            this.result = result;
            this.opp = opp;
            this.oppLev = oppLev;
            this.xpAdd = xpAdd;
            this.coinAdd = coinAdd;
        }

        // Getters
        public String getUsername() { return username; }
        public String getTime() { return time; }
        public boolean getResult() { return result; }
        public String getOpp() { return opp; }
        public int getOppLev() { return oppLev; }
        public int getXpAdd() { return xpAdd; }
        public int getCoinAdd() { return coinAdd; }
    }

    @FXML
    private TableView<HistoryRecord> historyTable;
    @FXML
    private TableColumn<HistoryRecord, String> usernameColumn;
    @FXML
    private TableColumn<HistoryRecord, Timestamp> timeColumn;
    @FXML
    private TableColumn<HistoryRecord, Byte> resultColumn;
    @FXML
    private TableColumn<HistoryRecord, String> oppColumn;
    @FXML
    private TableColumn<HistoryRecord, Integer> oppLevColumn;
    @FXML
    private TableColumn<HistoryRecord, Integer> xpColumn;
    @FXML
    private TableColumn<HistoryRecord, Integer> coinColumn;
    @FXML
    private Pagination pagination;
    @FXML
    private Button backButton;

    private final int rowsPerPage = 10;
    private List<HistoryRecord> historyData;

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        oppColumn.setCellValueFactory(new PropertyValueFactory<>("opp"));
        oppLevColumn.setCellValueFactory(new PropertyValueFactory<>("oppLev"));
        xpColumn.setCellValueFactory(new PropertyValueFactory<>("xpAdd"));
        coinColumn.setCellValueFactory(new PropertyValueFactory<>("coinAdd"));

        historyTable.setPlaceholder(new Label("No history available"));

        historyData = getHistoryData();
        pagination.setPageCount(getPageCount());
        pagination.setPageFactory(this::createPage);

        backButton.setOnAction(event -> {
            try {
                handleBackButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/FXML/History.fxml"));
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private ObservableList<HistoryRecord> getHistoryData() {
        ObservableList<HistoryRecord> data = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/citywars", "root", "Mohammad6900");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM history");

            while (rs.next()) {
                data.add(new HistoryRecord(
                        rs.getString("username"),
                        rs.getTimestamp("time").toString(),
                        rs.getBoolean("result"),
                        rs.getString("opp"),
                        rs.getInt("oppLev"),
                        rs.getInt("xpAdd"),
                        rs.getInt("coinAdd")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private int getPageCount() {
        int totalRecords = historyData.size();
        return (totalRecords / rowsPerPage) + (totalRecords % rowsPerPage == 0 ? 0 : 1);
    }

    private AnchorPane createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, historyData.size());
        historyTable.setItems(FXCollections.observableArrayList(historyData.subList(fromIndex, toIndex)));
        return new AnchorPane(historyTable);
    }

    private void handleBackButton() throws IOException {
        new MainMenu().start(stage);
    }

}
