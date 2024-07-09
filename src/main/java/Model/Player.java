package Model;

import Controller.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Player {
    public static int IdCounter ;

    private static ArrayList<Player> Users = new ArrayList<>();
    public int id;
    private String Username;
    private String Password;
    private String Email;
    private String Nickname;
    private String Question;
    private String Answer;
    public ArrayList<Card> deck;
    public int level;
    public int hp;
    public int xp;
    public int coin;

    //constructor
    public Player(String username, String password, String nickname, String email, String question, String answer) {
        this.id = IdCounter++;
        this.Username = username;
        this.Password = password;
        this.Nickname = nickname;
        this.Email = email;
        this.Question = question;
        this.Answer = answer;
        this.level =1;
        this.hp = 100;
        this.xp = 0;
        this.coin = 100;
        deck = GiveCard();
    }


    public Player(int id, String username, String password, String email, String nickname, String question, String answer, int level, int xp, int coin) throws SQLException {
        this.id = id;
        this.Username = username;
        this.Password = password;
        this.Nickname = nickname;
        this.Question = question;
        this.Answer = answer;
        this.Email = email;
        this.level = level;
        this.hp = level * 100;
        this.xp = xp;
        this.coin = coin;
        deck = GetUserCard(username);
    }

    //**********************//
    public static boolean ExistThisUsername(String username) {
        for (Player i : Users) {
            if (i.Username.equals(username)) return true;
        }
        return false;
    }

    public static Player GetUserByUsername(String username) {
        for (Player i : Users) {
            if (i.Username.equals(username)) return i;
        }
        return null;
    }

    public static void AddUser(Player user) {
        Users.add(user);
    }

    public static boolean ExistThisUser(String username, String password) {
        for (Player i : Users) {
            if (i.Username.equals(username) && i.Password.equals(password)) return true;
        }
        return false;
    }

    public static ArrayList<Card> GiveCard(){
        List<Card> copy = new ArrayList<>(Database.AllCards);
        Collections.shuffle(copy);
        return new ArrayList<>(copy.subList(0, 20));
    }

    private ArrayList<Card> GetUserCard(String username) throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/citywars";
        String user = "root";
        String pass = "Mohammad6900";
        Connection connection = DriverManager.getConnection(jdbcUrl, user, pass);
        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList<Card> playerDeck = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(jdbcUrl, user, pass);

            String sql = "SELECT id FROM userscards WHERE user_id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int cardId = rs.getInt("id");
                Card card = Database.getCardById(cardId);
                if (card != null) {
                    playerDeck.add(card);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerDeck;
    }

    public Card getCardFromDeck(int id){
        for(Card i : this.deck){
            if(i.id == id) return i;
        }
        return null;
    }

    //getter and setter
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public static void setUsers(ArrayList<Player> players) {
        Users = players;
    }

}