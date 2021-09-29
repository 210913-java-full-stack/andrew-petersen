package menus.validuser;

import DAOs.AccountsDAO;
import menus.OuterMenu;
import models.Accounts;
import models.Users;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountMenu {
    public void accountMenu(Users user, int account_id){

        try {
            Connection conn = ConnectionManager.getConnection();
            AccountsDAO accountListDAO = new AccountsDAO(conn);

            Accounts featureAccount = accountListDAO.getAccountById(account_id);

            System.out.println("Account ID: "+ account_id);
            System.out.println("Account Type: " + featureAccount.getAccount_type());
            System.out.println("Balance: $"+ featureAccount.getBalance());
            System.out.println("User ID: "+featureAccount.getUser_id());

            conn.close();
        }
        catch(SQLException | IOException e){
            e.printStackTrace();
        }

        Scanner choiceScanner = new Scanner(System.in);
        System.out.println("Please make a selection to continue:\n" +
                "(1) Deposit funds into this account\n" +
                "(2) Withdraw funds from this account\n" +
                "(3) Transfer funds from this account to a different account\n" +
                "(4) Return to Bank Menu\n" +
                "(5) Logout");
        String choice = choiceScanner.nextLine();

        switch(choice){
            case "1":
                //run deposit script
                Scanner depositScanner = new Scanner(System.in);
                System.out.println("How much would you like to deposit?");
                float depositAmmount = Float.parseFloat(depositScanner.nextLine());

                try{
                    Connection conn = ConnectionManager.getConnection();
                    AccountsDAO accountListDAO = new AccountsDAO(conn);

                    Accounts featureAccount = accountListDAO.getAccountById(account_id);

                    float oldBalance = featureAccount.getBalance();
                    float newBalance = oldBalance+depositAmmount;
                    featureAccount.setBalance(newBalance);

                    accountListDAO.updateAccounts(featureAccount);
                    conn.close();

                    new AccountMenu().accountMenu(user,account_id);
                }
                catch (SQLException|IOException e){
                    e.printStackTrace();
                }
                break;

            case "2":

                //run withdrawal script
                Scanner withdrawalScanner = new Scanner(System.in);
                System.out.println("How much would you like to deposit?");
                float withdrawalAmmount = Float.parseFloat(withdrawalScanner.nextLine());

                try{
                    Connection conn = ConnectionManager.getConnection();
                    AccountsDAO accountListDAO = new AccountsDAO(conn);

                    Accounts featureAccount = accountListDAO.getAccountById(account_id);

                    float oldBalance = featureAccount.getBalance();
                    float newBalance = oldBalance-withdrawalAmmount;
                    featureAccount.setBalance(newBalance);

                    accountListDAO.updateAccounts(featureAccount);
                    conn.close();

                    new AccountMenu().accountMenu(user,account_id);
                }
                catch (SQLException|IOException e){
                    e.printStackTrace();
                }
                break;

            case "3":

                //run transfer script

                break;
            case "4":
                new BankMenu().bankMenu(user);
                break;
            case "5":
                new OuterMenu().OuterMenu();
                break;
        }
    }
}