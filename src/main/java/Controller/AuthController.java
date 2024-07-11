//@@@>>>MJY<<<@@@
package Controller;

import Model.Admin;
import Model.Player;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthController {
    public static Player LoginUser;
    public static Admin admin = new Admin("ali","1111");
    static Scanner scanner = new Scanner(System.in);
    private static final String AdminPass = "1111";

    //*****************%%%%%%%%%%%%%%%******************//
    public static boolean signIn(String username, String password) {
        if (Player.ExistThisUsername(username)) {
            if (Player.ExistThisUser(username, password)) {
                LoginUser = Player.GetUserByUsername(username);
                return true;
            }
        }
        return false;
    }


    public static String register(String username, String password , String nickname, String email , String securityQuestion, String answer) {
        Player tempUser = new Player(username,password,nickname,email,securityQuestion,answer);
        if(isValidUsername(tempUser.getUsername())) {
            if (!Player.ExistThisUsername(tempUser.getUsername())) {
                if (isValidPassword(tempUser.getPassword())) {
                    if(!nickname.equals("")) {
                        if (isValidEmail(tempUser.getEmail())) {
                            if (securityQuestion != null) {
                                if (!answer.equals("")) {
                                    return "Register successfully";
                                } else return "Enter answer of security question";
                            } else return "Enter security question";
                        } else return ("Enter valid email");
                    } else return "Enter nickname";
                } else return("Password is week");
            } else return("This username is already taken");
        } else return("Enter valid username");
    }

    public static Player signInPlayer2(String command) {
        if(CommandExtractor.Login(command).find()) {
            while (true) {
                Matcher data = CommandExtractor.Login(command);
                if (data.find()) {
                    if (Player.ExistThisUsername(data.group(1))) {
                        if (Player.ExistThisUser(data.group(1), data.group(2))) {
                            Player p2 = Player.GetUserByUsername(data.group(1));
                            System.out.println("Login successfully");
                            return p2;
                        }
                        else {
                            System.out.println("You are punished for 5 seconds. Please wait...");

                            long endTime = System.currentTimeMillis() + 5000;
                            boolean inputDuringDelay = false;

                            // Continuously check for input during the 5-second delay
                            while (System.currentTimeMillis() < endTime) {
                                if (scanner.hasNextLine()) {
                                    System.out.println("Input is not allowed during the delay period.");
                                    scanner.nextLine(); // Discard the input
                                    inputDuringDelay = true;
                                }
                            }
                            System.out.printf("input again >> ");
                            command = scanner.nextLine();
                        }
                    }
                    else {
                        System.out.println("Username doesn’t exist!");
                        break;
                    }
                }
            }
        }
        else if(CommandExtractor.ForgotPassword(command).find()){
            Matcher data = CommandExtractor.ForgotPassword(command);
            data.find();
            if(Player.ExistThisUsername(data.group(1))) {
                System.out.printf("answer this question : %s\n>> ", Player.GetUserByUsername(data.group(1)).getQuestion());
                String ans = scanner.nextLine();
                if (ans.equals(Player.GetUserByUsername(data.group(1)).getAnswer())) {
                    LoginUser = Player.GetUserByUsername(data.group(1));
                    System.out.println("Login successfully");
                } else {
                    System.out.println("Wrong answer!");
                }
            }
            else System.out.println("Username doesn’t exist!");
        }
        else{
            System.out.println("Command not found!");
        }
        return null;
    }


    //*****************%%%%%%%%%%%%%%%******************//

    public static boolean isValidUsername(String username) {
        String regex = "^[A-Za-z0-9_]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);

        if(!matcher.matches()){
            System.out.println("Invalid-Username!");
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String password) {
        String lengthPattern = ".{8,}";
        String numberPattern = ".*\\d.*";
        String upperCasePattern = ".*[A-Z].*";
        String lowerCasePattern = ".*[a-z].*";
        String specialCharacterPattern = ".*[^a-zA-Z0-9].*";

        Pattern length = Pattern.compile(lengthPattern);
        Pattern number = Pattern.compile(numberPattern);
        Pattern upperCase = Pattern.compile(upperCasePattern);
        Pattern lowerCase = Pattern.compile(lowerCasePattern);
        Pattern specialCharacter = Pattern.compile(specialCharacterPattern);

        Matcher lengthMatcher = length.matcher(password);
        Matcher numberMatcher = number.matcher(password);
        Matcher upperCaseMatcher = upperCase.matcher(password);
        Matcher lowerCaseMatcher = lowerCase.matcher(password);
        Matcher specialCharacterMatcher = specialCharacter.matcher(password);

        if(!lengthMatcher.find()) {
            System.out.println("The Password should has 8 character at least");
            return false;
        }

        if(!numberMatcher.find()) {
            System.out.println("The Password should has one number at least");
            return false;
        }

        if (!upperCaseMatcher.find()) {
            System.out.println("The password should has a uppercase letter at least");
            return false;
        }
        if (!lowerCaseMatcher.find()){
            System.out.println("The password should has a lower letter at least");
            return false;
        }
        if (!specialCharacterMatcher.find()){
            System.out.println("The password should has a specialCharacter at least");
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

       if(!matcher.find()){
           System.out.println("Email is invalid");
           return false;
       }
       return true;
    }

    public class generateRandomPassword {

        private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        private static final String DIGITS = "0123456789";
        private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
        private static final int PASSWORD_LENGTH = 8;
        private static SecureRandom random = new SecureRandom();

        public static String generateRandomPassword() {
            StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

            // Ensure at least one character from each required set
            password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
            password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
            password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
            password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

            for (int i = 3; i < PASSWORD_LENGTH; i++) {
                password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
            }

            return shuffleString(password.toString());
        }

        private static String shuffleString(String input) {
            char[] characters = input.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                int randomIndex = random.nextInt(characters.length);
                char temp = characters[i];
                characters[i] = characters[randomIndex];
                characters[randomIndex] = temp;
            }
            return new String(characters);
        }
    }

}
