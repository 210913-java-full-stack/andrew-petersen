package menus;

import java.util.Scanner;

public class OuterMenu {
    public void OuterMenu() {
        boolean outer = true;

        while (outer) {
            Scanner menuScan = new Scanner(System.in);

            System.out.print("########## OUTER PORTAL ##########\n" +
                    "(1) Returning User- Login\n" +
                    "(2) New User- Create Username\n" +
                    "(3) Quit- Exit\n" +
                    "##################################\n" +
                    "Please make a selection and press 'Enter':");
            String outerInput = menuScan.nextLine();

            switch (outerInput) {
                case "1":
                    new LoginMenu().loginMenu();
                    break;
                case "2":
                    new NewUserMenu().newUserMenu();
                    break;
                case "3":
                case "Q":
                case "q":
                    outer = false;
                    break;
                default:
                    System.out.println("You entered an invalid selection! Please try again.");
                    break;
            }
        }
    }
}