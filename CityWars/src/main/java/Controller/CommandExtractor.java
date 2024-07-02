package Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandExtractor {
    public static Matcher sign_up(String command){
        String regex = "user create -u (\\S+) -p (\\S+) (\\S+) –email (\\S+) -n (\\S+)";
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher for the input command
        Matcher matcher = pattern.matcher(command);

        return matcher;
    }

    public static Matcher Login(String line){
        String regex = "user login -u (\\S+) -p (\\S+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        return matcher;
    }

    public static String ProfileMenu_Username(String line){
        String regex = "Profile change -u (\\S+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group(1);
    }

    public static Matcher ForgotPassword(String line){
        String regex = "Forgot my password -u (\\S+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        return matcher;
    }
}
