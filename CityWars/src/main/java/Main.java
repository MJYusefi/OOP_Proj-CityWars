import Controller.AuthController;
import Model.User;
import View.ProfileView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    AuthController.signIn();
                    break;
                case 2:
                    AuthController.signUp();
                    break;
                case 3:
                    ProfileView.showProfile();
                    break;
                case 4:
                    System.out.println("Exiting the game...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void showMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Sign In");
        System.out.println("2. Sign Up");
        System.out.println("3. Profile");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
}
