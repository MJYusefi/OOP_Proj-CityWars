//@@@>>>MJY<<<@@@
package Controller;

import Model.Admin;
import Model.Player;
import com.github.lalyos.jfiglet.FigletFont;
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


    public static boolean signUp(String command) {
        Matcher Userdata = CommandExtractor.sign_up(command);
        if(!CheckEmptyCommand(Userdata)){
            Player tempUser = new Player(Userdata.group(1),Userdata.group(2),Userdata.group(5),Userdata.group(4));
            if(isValidUsername(tempUser.getUsername())){
                if(!Player.ExistThisUsername(tempUser.getUsername())){
                    if (isValidPassword(tempUser.getPassword()) || tempUser.getPassword().equals("random")){
                        if(isValidEmail(tempUser.getEmail())){
                            if(tempUser.getPassword().equals("random")){
                                tempUser.setPassword(PasswordGenerator.generateRandomPassword());
                                System.out.printf("Your random password: %s\n",tempUser.getPassword());
                                while(true) {
                                    System.out.print("Please enter your password for confirmation : ");
                                    String confirm = scanner.next();
                                    if (!confirm.equals(tempUser.getPassword())) {
                                        System.out.println("Faild in confirmation");
                                    }
                                }
                            }
                            else{
                                if(!tempUser.getPassword().equals(Userdata.group(3))){
                                    System.out.println("The confirmation password failed!");
                                    return false;
                                }
                            }
                            System.out.println("User created successfully. Please choose a security question :\n" +
                                    "• 1-What is your father’s name ? \n" +
                                    "• 2-What is your favourite color ? \n" +
                                    "• 3-What was the name of your first pet?");
                            while(true) {
                                System.out.printf(">> ");
                                String line = scanner.nextLine();
                                Matcher QuestionMatcher = QuestionCommand(line);
                                if (QuestionMatcher.find()) {
                                    if (!QuestionMatcher.group(2).equals(QuestionMatcher.group(3)))
                                        System.out.println("Try Again");
                                    else{
                                        if(QuestionMatcher.group(1).equals("1")) {
                                            tempUser.setQuestion("What is your father’s name ?");
                                            tempUser.setAnswer(QuestionMatcher.group(2));
                                        }
                                        if(QuestionMatcher.group(1).equals("2")) {
                                            tempUser.setQuestion("What is your favourite color ?");
                                            tempUser.setAnswer(QuestionMatcher.group(2));
                                        }
                                        if(QuestionMatcher.group(1).equals("3")) {
                                            tempUser.setQuestion("What was the name of your first pet?");
                                            tempUser.setAnswer(QuestionMatcher.group(2));
                                        }
                                        break;
                                    }
                                }
                            }

                            while(true) {
                                String CaptchaANS = AsciiArtCaptcha.start();
                                System.out.printf("\n enter captcha : ");
                                if (CaptchaANS.equals(scanner.next())) {
                                    Player.AddUser(tempUser);
                                    LoginUser = tempUser ;
                                    System.out.println("User created successfully.");
                                    return true;
                                }
                                else System.out.printf("Wrong captcha\n");
                            }

                        }
                    }
                }else{
                    System.out.println("This username is already taken");
                }
            }
        }else {
            System.out.println("please enter all fields correctly");
        }
        return false;
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

    public static boolean SignInAdmin(){
        String command = scanner.nextLine();
        Pattern password = Pattern.compile("login\\s+admin\\s+(\\S+)");
        Matcher matcher = password.matcher(command);
        if(matcher.find()) {
            if (matcher.group(1).equals(AdminPass)) {
                System.out.println("Admin Successfully logged in");
                return true;
            }
            System.out.println("Password is wrong");
            return false;
        }
        System.out.println("Command not found");
        return false;
    }

    //*****************%%%%%%%%%%%%%%%******************//
    public static boolean CheckEmptyCommand(Matcher matcher){
        boolean hasError = false;
        // Find and extract command parts
        if (matcher.find()) {

            String username = matcher.group(1);
            String password = matcher.group(2);
            String passwordConfirmation = matcher.group(3);
            String email = matcher.group(4);
            String nickname = matcher.group(5);

            // Check for empty fields
            if (username.isEmpty()) {
                System.out.println("Error: Username is empty.");
                hasError = true;
            }
            if (password.isEmpty()) {
                System.out.println("Error: Password is empty.");
                hasError = true;
            }
            if (passwordConfirmation.isEmpty()) {
                System.out.println("Error: Password confirmation is empty.");
                hasError = true;
            }
            if (email.isEmpty()) {
                System.out.println("Error: Email is empty.");
                hasError = true;
            }
            if (nickname.isEmpty()) {
                System.out.println("Error: Nickname is empty.");
                hasError = true;
            }
        }
        else hasError=true;

        return hasError;
    }

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

    public class PasswordGenerator {

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

    public class AsciiArtCaptcha {

        // Method to generate a random string
        public static String generateRandomString(int length) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder stringBuilder = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
            }
            return stringBuilder.toString();
        }

        public static String start() {
            String captcha = generateRandomString(6);

            try {
                String asciiArt = FigletFont.convertOneLine(captcha);

                System.out.println("CAPTCHA:");
                System.out.println(asciiArt);
                return captcha;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return captcha;
        }
    }

    public static Matcher QuestionCommand(String line){
        String regex = "question pick -q (\\S+) -a (.+?) -c (.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        return matcher;
    }

    private static void monitorInput(Scanner scanner, long endTime) {
        while (!Thread.currentThread().isInterrupted()) {
            if (scanner.hasNext()) {
                long currentTime = System.currentTimeMillis();
                long remainingTime = endTime - currentTime;
                if (remainingTime > 0) {
                    System.out.printf("Please wait %.2f seconds before entering another password.%n", remainingTime / 1000.0);
                } else {
                    System.out.println("You can try entering the password now.");
                }
                scanner.nextLine();
            }
        }
    }

}
