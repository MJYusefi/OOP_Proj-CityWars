package Controller;

import Model.User;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthController {
    public static User LoginUser ;
    static Scanner scanner = new Scanner(System.in);
    private static final int LOCKOUT_TIME_MS = 5000; // 5 seconds

    public static void signIn() {
        System.out.printf("if you forgot your password : Forgot my password -u <username>\n>> ");
        String command = scanner.nextLine();
        if(CommandExtractor.Login(command).find()) {
            while (true) {
                Matcher data = CommandExtractor.Login(command);
                if (data.find()) {
                    if (User.ExistThisUsername(data.group(1))) {
                        if (User.ExistThisUser(data.group(1), data.group(2))) {
                            LoginUser = User.GetUserByUsername(data.group(1));
                        } else {
                            System.out.println("Password and Username don’t match!");

                            long endTime = System.currentTimeMillis() + LOCKOUT_TIME_MS;
                            // Start a new thread to monitor user input during the lockout period
                            Thread monitorThread = new Thread(() -> monitorInput(scanner, endTime));
                            monitorThread.start();
                            try {
                                Thread.sleep(LOCKOUT_TIME_MS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // Stop the monitoring thread after the sleep period
                            monitorThread.interrupt();
                        }
                    } else {
                        System.out.println("Username doesn’t exist!");
                    }
                }
            }
        }
        else if(CommandExtractor.ForgotPassword(command).find()){
            Matcher data = CommandExtractor.ForgotPassword(command);
            data.find();
            System.out.printf("answer this question : %s\n>> ",User.GetUserByUsername(data.group(1)));
            String ans = scanner.nextLine();
            if(ans.equals(User.GetUserByUsername(data.group(1)).getAnswer())){
                LoginUser = User.GetUserByUsername(data.group(1));
            }
            else{
                System.out.println("Wrong answer!");
            }
        }
        else{
            System.out.println("Command not found!");
        }
    }

    public static void signUp() {
        // get user data
        System.out.print("Enter user Create : ");
        // extract data from command
        Matcher Userdata = CommandExtractor.sign_up(scanner.nextLine());
        if(CheckEmptyCommand(Userdata)){
            User tempUser = new User(Userdata.group(1),Userdata.group(2),Userdata.group(4),Userdata.group(3));
            if(isValidUsername(tempUser.getUsername())){
                if(!User.ExistThisUsername(tempUser.getUsername())){
                    if (isValidPassword(tempUser.getPassword()) || tempUser.getPassword().equals("random")){
                        if(isValidEmail(tempUser.getEmail())){
                            if(tempUser.getPassword().equals("random")){
                                tempUser.setPassword(PasswordGenerator.generateRandomPassword());
                                System.out.printf("Your random password: %s\n",tempUser.getPassword());
                                System.out.printf("Please enter your password : ");
                                String confirm = scanner.next();
                                if(confirm.equals(tempUser.getPassword())){
                                    System.out.println("User created successfully. Please choose a security question :\n" +
                                            "• 1-What is your father’s name ? \n" +
                                            "• 2-What is your favourite color ? \n" +
                                            "• 3-What was the name of your first pet?");
                                    while(true) {
                                        String line = scanner.nextLine();
                                        Matcher QuestionMatcher = QuestionCommand(line);
                                        if (QuestionMatcher.find()) {
                                            if (!QuestionMatcher.group(2).equals(QuestionMatcher.group(3)))
                                                System.out.println("Try Again");
                                            else{
                                                tempUser.setQuestionN(Integer.parseInt(QuestionMatcher.group(1)));
                                                tempUser.setAnswer(QuestionMatcher.group(2));
                                                break;
                                            }
                                        }
                                    }
                                    User.AddUser(tempUser);

                                }else {
                                    System.out.println("Faild in confirmation");
                                }
                            }
                        }
                    }
                }else{
                    System.out.println("This username is already taken");
                }
            }
        }else {
            System.out.println("please enter all fields");
        }
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
        String upperCasePattern = ".*[A-Z].*";
        String lowerCasePattern = ".*[a-z].*";
        String specialCharacterPattern = ".*[^a-zA-Z0-9].*";

        Pattern length = Pattern.compile(lengthPattern);
        Pattern upperCase = Pattern.compile(upperCasePattern);
        Pattern lowerCase = Pattern.compile(lowerCasePattern);
        Pattern specialCharacter = Pattern.compile(specialCharacterPattern);

        Matcher lengthMatcher = length.matcher(password);
        Matcher upperCaseMatcher = upperCase.matcher(password);
        Matcher lowerCaseMatcher = lowerCase.matcher(password);
        Matcher specialCharacterMatcher = specialCharacter.matcher(password);

        if(!lengthMatcher.find()) {
            System.out.println("The Password should has 8 character at least");
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

            // Fill the remaining characters
            for (int i = 3; i < PASSWORD_LENGTH; i++) {
                password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
            }

            // Shuffle the password to ensure randomness
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
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }

}
