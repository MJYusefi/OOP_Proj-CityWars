//@@@>>>MJY<<<@@@
package Controller;

import Model.Card;
import Model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;



public class Database {
    public static String jdbcUrl = "jdbc:mysql://localhost:3306/citywars";
    public static String user = "root";
    public static String pass = "Mohammad6900";
    public static int MAX_CARD_ID = 0 ;
    public static int MAX_USER_ID = 0 ;
    public static ArrayList<Card> AllCards = new ArrayList<Card>();
    public static ArrayList<Player> Players = new ArrayList<Player>();

    public static void InitProgram() throws SQLException {
        getAllCards();
        FetchUserFromDatabase();
        Player.setUsers(Players);
    }

    public static void getAllCards() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrl, user, pass);
        Statement statement = connection.createStatement();

        try (ResultSet rs = statement.executeQuery("SELECT * FROM cards")) {
            while (rs.next()) {
                int cardId = rs.getInt("id");
                String name = rs.getString("name");
                int defAtt = rs.getInt("defAtt");
                int dur = rs.getInt("dur");
                int playDamage = rs.getInt("playDamage");
                int upLevel = rs.getInt("upLevel");
                int upCost = rs.getInt("upCost");
                Card card = new Card(cardId, name, defAtt, dur, playDamage, upLevel, upCost);
                AllCards.add(card);
                if(MAX_CARD_ID < cardId) MAX_CARD_ID = cardId ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MAX_CARD_ID++;
    }

    public static void saveAllCards() throws SQLException{

        Connection connection = DriverManager.getConnection(jdbcUrl, user, pass);
        PreparedStatement pstmt = null , stmt = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, user, pass);

            String clear = "TRUNCATE TABLE cards";
            String sql = "INSERT INTO cards (id, name, defAtt, dur, playDamage, upLevel, upCost) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            stmt = connection.prepareStatement(clear);

            stmt.executeUpdate(clear);

            for (Card card : AllCards) {
                pstmt.setInt(1, card.id);
                pstmt.setString(2, card.name);
                pstmt.setInt(3, card.defAtt);
                pstmt.setInt(4, card.dur);
                pstmt.setInt(5, card.playDamage);
                pstmt.setInt(6, card.upLevel);
                pstmt.setInt(7, card.upCost);
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void FetchUserFromDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrl, user, pass);
        Statement statement = connection.createStatement();

        try (ResultSet rs = statement.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String nickname = rs.getString("nickname");
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                int level = rs.getInt("level");
                int coin = rs.getInt("coin");
                int xp = rs.getInt("xp");

                Player pl = new Player(id, username, password, email, nickname, question, answer, level, xp, coin);
                Players.add(pl);
                MAX_USER_ID++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MAX_USER_ID++;
        Player.IdCounter = MAX_USER_ID;
    }

    public static void SaveEditInDB(Player player) throws SQLException {
        Connection conn;
        PreparedStatement pstmt;

        try {
            conn = DriverManager.getConnection(jdbcUrl, user, pass);

            // delete the player
            String deleteSQL = "DELETE FROM users WHERE user_id = ?";
            pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setInt(1, player.id);
            pstmt.executeUpdate();

            // Add the player back into the table
            String insertSQL = "INSERT INTO users (user_id, Username, Password, Email, Nickname, Question, Answer, level, xp, coin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setInt(1,player.id);
            pstmt.setString(2, player.getUsername());
            pstmt.setString(3, player.getPassword());
            pstmt.setString(4, player.getEmail());
            pstmt.setString(5, player.getNickname());
            pstmt.setString(6, player.getQuestion());
            pstmt.setString(7, player.getAnswer());
            pstmt.setInt(8, player.level);
            pstmt.setInt(9, player.xp);
            pstmt.setInt(10, player.coin);
            pstmt.executeUpdate();

            String delc = "DELETE FROM userscards WHERE user_id = ?";
            pstmt = conn.prepareStatement(delc);
            pstmt.setInt(1, player.id);
            pstmt.executeUpdate();

            String addc = "INSERT INTO userscards (user_id, id, lvl) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(addc);

            for (Card card : player.deck) {
                pstmt.setInt(1, player.id);
                pstmt.setInt(2, card.id);
                pstmt.setInt(3,card.curlevel);
                pstmt.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Card getCardById(int id) {
        for(Card c : AllCards){
            if(c.id == id) return c;
        }
        return null;
    }

    public static boolean ExistNameForCard(String name) {
        for(Card i : AllCards){
            if(i.name.equals(name)) return true;
        }
        return false;
    }

    public static boolean ExistThisUsername(String Username) {
        for(Player i : Players){
            if(i.getUsername().equals(Username)) return true;
        }
        return false;
    }

    public static Player getByUsername(String Username) {
        for(Player i : Players){
            if(i.getUsername().equals(Username)) return i;
        }
        return null;
    }


    ////*************////
    public static void ShowCards(){
        String leftAlignFormat = "| %-4d | %-15s | %-7d | %-4d | %-10d | %-7d | %-7d |%n";

        System.out.format("+------+---------------------+---------+------+------------+---------+---------+%n");
        System.out.format("| ID   | Name                | DefAtt  | Dur  | PlayDamage | UpLevel | UpCost  |%n");
        System.out.format("+------+---------------------+---------+------+------------+---------+---------+%n");

        for (Card card : AllCards) {
            System.out.format(leftAlignFormat, card.id, card.name, card.defAtt, card.dur, card.playDamage, card.upLevel, card.upCost);
        }

        System.out.format("+------+---------------------+---------+------+------------+---------+---------+%n");
    }

    public static String EditName(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter name : ");
        String name = scanner.next();
        if(!ExistNameForCard(name)){
            System.out.printf("Are you sure ? : ");
            if(scanner.next().equals("y")) {
                System.out.println("Change Successfully");
                return name;
            }
            return "no";
        }
        System.out.println("Name taken already");
        return "error";
    }

    public static int EditDefatt(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter DefAtt : ");
        int def = scanner.nextInt();
        System.out.printf("Are you sure ? : ");
        if(scanner.next().equals("y")) {
            System.out.println("Change Successfully");
            return def;
        }
        return -10;
    }

    public static int Editdur(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter Duration : ");
        int dur = scanner.nextInt();
        System.out.printf("Are you sure ? : ");
        if(scanner.next().equals("y")) {
            System.out.println("Change Successfully");
            return dur;
        }
        return -10;
    }

    public static int Editpd(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter PlayDamage : ");
        int dur = scanner.nextInt();
        System.out.printf("Are you sure ? : ");
        if(scanner.next().equals("y")) {
            System.out.println("Change Successfully");
            return dur;
        }
        return -10;
    }

    public static int Editupcost(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter UpCost : ");
        int dur = scanner.nextInt();
        System.out.printf("Are you sure ? : ");
        if(scanner.next().equals("y")) {
            System.out.println("Change Successfully");
            return dur;
        }
        return -10;
    }

    public static int Edituplevel(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter UpLevel : ");
        int dur = scanner.nextInt();
        System.out.printf("Are you sure ? : ");
        if(scanner.next().equals("y")) {
            System.out.println("Change Successfully");
            return dur;
        }
        return -10;
    }

    //*** Admin Command ***//
    public static void AddCard(){

        Scanner scanner = new Scanner(System.in);
        String name;
        int defAtt;
        int dur;
        int playDamage;
        int upLevel;
        int upCost;

        System.out.println("Name : ");
        while (ExistNameForCard(name = scanner.next())){
            System.out.println("This name already taken");
            System.out.println("Name : ");
        }

        System.out.println("DeffAtt : ");
        while (true){
            defAtt = scanner.nextInt();
            if(10<=defAtt && 100>=defAtt) break;
            else {
                System.out.println("Should between 10 and 100");
                System.out.println("deffAtt : ");
            }
        }

        System.out.println("Duration : ");
        while (true){
            dur = scanner.nextInt();
            if(1<=dur && 5>=dur) break;
            else {
                System.out.println("Should between 1 and 5");
                System.out.println("Duration : ");
            }
        }

        System.out.println("PlayDamage : ");
        while (true){
            playDamage = scanner.nextInt();
            if(10<=playDamage && 50>=playDamage) break;
            else {
                System.out.println("Should between 10 and 50");
                System.out.println("playDamage : ");
            }
        }

        System.out.println("UpCost : ");
        upCost = scanner.nextInt();

        System.out.println("UpLevel : ");
        upLevel = scanner.nextInt();

        AllCards.add(new Card(MAX_CARD_ID, name, defAtt, dur, playDamage, upLevel, upCost));
        System.out.println("Card Added Successfully");
        MAX_CARD_ID++;
    }

    public static void EditCard(){
        while(true) {
            ShowCards();
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            if (command.equals("show")) ShowCards();
            else if(command.equals("back")) break;
            else {
                if (Integer.parseInt(command) < MAX_CARD_ID && Integer.parseInt(command) > 0) {
                    System.out.println("1-Name\n2-DefAtt\n3-Dur\n4-PlayDamage\n5-UpLevel\n6-UpCost\n>> ");
                    int choice = scanner.nextInt();
                    int tmpi;
                    switch (choice) {
                        case 1:
                            String tmp = EditName();
                            if(!(tmp.equals("error") || tmp.equals("no"))) getCardById(Integer.parseInt(command)).name = tmp;
                            break;
                        case 2:
                            if((tmpi = EditDefatt()) != -10) getCardById(Integer.parseInt(command)).defAtt = tmpi;
                            break;
                        case 3:
                            if((tmpi = Editdur()) != -10) getCardById(Integer.parseInt(command)).dur = tmpi;
                            break;
                        case 4:
                            if((tmpi = Editpd()) != -10) getCardById(Integer.parseInt(command)).playDamage = tmpi;
                            break;
                        case 5:
                            if((tmpi = Edituplevel()) != -10) getCardById(Integer.parseInt(command)).upLevel = tmpi;
                            break;
                        case 6:
                            if((tmpi = Editupcost()) != -10) getCardById(Integer.parseInt(command)).upCost = tmpi;
                            break;
                    }
                }
                else System.out.println("Enter a valid Card ID");
            }
        }
    }

    public static void RemoveCard(){
        while (true) {
            System.out.println("0-back");
            ShowCards();
            Scanner scanner = new Scanner(System.in);
            int c = scanner.nextInt();
            if(c == 0) break;
            if (c < MAX_CARD_ID) {
                AllCards.remove(getCardById(c));
                System.out.println("Remove Successfully");
            }
            else System.out.println("Enter a valid Card ID");
        }
    }

    public static void ShowPlayer(){
        String leftAlignFormat = "| %-15s | %-5d | %-5d |%n";

        System.out.format("+---------------------+-------+-------+%n");
        System.out.format("| Name                | Level | Coin  |%n");
        System.out.format("+---------------------+-------+-------+%n");

        for (Player p : Players) {
            System.out.format(leftAlignFormat, p.getUsername(), p.level, p.coin);
        }
        System.out.format("+---------------------+-------+-------+%n");
    }
}

