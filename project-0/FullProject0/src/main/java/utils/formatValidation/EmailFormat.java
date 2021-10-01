package utils.formatValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface EmailFormat {

    default boolean emailChecker(String email){
        //something@something.***

        Pattern emailPattern = Pattern.compile("^([-a-zA-Z0-9_.]+[@][-a-zA-Z0-9_]+[.][a-zA-Z]{3})$");
        Matcher checkEmail = emailPattern.matcher(email);

        boolean validEmail=checkEmail.matches();

        return validEmail;
    }
}
