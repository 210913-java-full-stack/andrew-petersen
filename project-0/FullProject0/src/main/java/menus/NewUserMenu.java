package menus;

import DAOs.UsersDAO;
import models.Users;
import utils.CallAndResponse;
import utils.ConnectionManager;
import utils.MyArrayList;
import utils.formatValidation.EmailFormat;
import utils.formatValidation.NameFormat;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * NewUserMenu is an interface that contains one default method, "newUserMenu".
 */
public interface NewUserMenu extends CallAndResponse, EmailFormat, NameFormat {

    /**
     * The newUserMenu method scans user input and runs validation on it. If all entries
     * are valid, it creates a new Users object and adds the data to the database. If any
     * entries are not valid, it prints a message.
     */
    default void newUserMenu() {

        //This section asks the user for input and sets 5 string references to be used later
        String username1 = caller("Please enter a username and then press 'Enter':");
        String password1 = caller("Please enter a password and then press 'Enter':");
        String email1 = caller("Please enter your email and then press 'Enter':");
        String first_name1 = caller("Please enter your first (given) name and then press 'Enter':");
        String last_name1 = caller("Please enter your last (family) name and then press 'Enter':");


        try{
            //Checking to verify there are no duplicate usernames
            Connection conn = ConnectionManager.getConnection();
            UsersDAO dao = new UsersDAO(conn);
            MyArrayList<Users> fullUserList = dao.getUserList();
            MyArrayList<String> allUsernames = new MyArrayList<>();

            //Adds all the username Strings in the database to a list
            int i = 0;
            while(i<fullUserList.size()){
                Users tempUser = fullUserList.get(i);
                if(tempUser!=null) {
                    String tempUsername = tempUser.getUsername();
                    allUsernames.add(tempUsername);
                }
                i++;
            }

            if(allUsernames.contains(username1)){
                System.out.println("That username is already being used");
            }
            else{
                //Email validation using the emailChecker method
                boolean goodEmail = emailChecker(email1);

                //Making sure the first letter of first_name is automatically capitalized
                String firstFirst = first_name1.substring(0,1);
                String restFirst = first_name1.substring(1);
                firstFirst.toUpperCase();
                String First_name1 = firstFirst.concat(restFirst);

                //Name validation using the two methods from the NameFormat interface
                boolean goodFirstName = firstNameChecker(First_name1);
                boolean goodLastName = lastNameChecker(last_name1);


                //Email validation using regex in the EmailFormat interface
                if(!goodEmail){
                    System.out.println("The email you entered is not in a valid email format");
                }else
                    //If the email is valid:
                    //Name validation using regex in the NameFormat interface
                    if(!goodFirstName|!goodLastName){
                        System.out.println("Please make sure you are typing your name without any numbers" +
                                "or special characters.");
                    }

                    //If names and email are validated, add the user to our DB.
                    else {
                        Users addingUser = new Users();
                        addingUser.setUsername(username1);
                        addingUser.setPassword(password1);
                        addingUser.setEmail(email1);
                        addingUser.setFirst_name(First_name1);
                        addingUser.setLast_name(last_name1);

                        dao.updateUser(addingUser);
                    }
                }
            }
        catch(SQLException|IOException e){
            e.printStackTrace();
        }
    }
}
