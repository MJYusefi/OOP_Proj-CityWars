package Controller;

import Model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileViewController {
    public static void CommandFinder(String line){
        String show = "Show information";
        String username = "Profile change -u (\\S+)";
        String nickname = "Profile change -n (\\S+)";
        String password = "profile change password -o (\\S+) -n (\\S+)";
        
        Pattern Show = Pattern.compile(show);
        Pattern Username = Pattern.compile(username);
        Pattern Nickname = Pattern.compile(nickname);
        Pattern Password = Pattern.compile(password);

        Matcher matcher = Show.matcher(line); if(matcher.find()) ChangeUsernme(matcher.group(1));
        matcher = Username.matcher(line); if(matcher.find()) ChangeUsernme(matcher.group(1));
        matcher = Nickname.matcher(line); if(matcher.find()) ChangeNickname(matcher.group(1));
        matcher = Password.matcher(line); if(matcher.find()) ChangePassword(matcher.group(1));
    }

    public static void  ChangeUsernme(String username){
        if(User.ExistThisUsername(username)){
            System.out.println("This username already teken");
        }
        else{
            AuthController.LoginUser.setUsername(username);
            System.out.println("Username change successfully");
        }
    }

    public static void  ChangeNickname(String nickname) {
        AuthController.LoginUser.setUsername(nickname);
    }

    public static void  ChangeEmail(String email) {
        AuthController.LoginUser.setEmail(email);
    }

    public static void  ChangePassword(String password){
        if(AuthController.isValidPassword(password)){
            if(!password.equals(AuthController.LoginUser.getPassword())){
                AuthController.LoginUser.setPassword(password);
                System.out.println("Password change successfully");
            }else {
                System.out.println("Password is same as old password");
            }
        }else {
            System.out.println("Password is week");
        }
    }


}
