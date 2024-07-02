package View;

import Controller.AuthController;
import Controller.ProfileViewController;
import Model.User;

import java.util.Scanner;

public class ProfileView {
    public static User LoginUser = AuthController.LoginUser;
    static Scanner scanner = new Scanner(System.in);
    public static void showProfile() {
        System.out.println("User Profile:");
        System.out.println("Username: " + LoginUser.getUsername());
        System.out.println("Password: " + LoginUser.getPassword());
        System.out.println("Email: " + LoginUser.getEmail());
        System.out.printf(">> ");
        runmenu();
    }

    public static void runmenu(){
        String command ;
        while(!(command= scanner.nextLine()).equals("back")){
            ProfileViewController.CommandFinder(command);
        }
    }


}
