package Model;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> Users = new ArrayList<>();
    private String Username;
    private String Password;
    private String Email;
    private String Nickname;
    private int QuestionN;
    private String Answer;

    //constructor
    public User(String username, String password , String nickname , String email) {
        Username = username;
        Password = password;
        Nickname = nickname;
        Email = email;
    }
    public User(){}

    public static boolean ExistThisUsername(String username){
        for(User i : Users){
            if(i.Username.equals(username)) return true;
        }
        return false;
    }

    public static User GetUserByUsername(String username){
        for(User i : Users){
            if(i.Username.equals(username)) return i;
        }
        return null;
    }

    public static void AddUser(User user) {
        Users.add(user);
    }

    public static boolean ExistThisUser(String username , String password){
        for(User i : Users){
            if(i.Username.equals(username) && i.Password.equals(password)) return true;
        }
        return false;
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

    public int getQuestionN() {
        return QuestionN;
    }

    public void setQuestionN(int questionN) {
        QuestionN = questionN;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
