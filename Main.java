import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. Log in \n2. Sign up \nChoose 1 or 2");
        int n = scan.nextInt();
        switch (n) {
            case 1:
                logIn();
                break;
            case 2:
                signUp();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        scan.close();
    }

    public static void logIn(){
        System.out.print("Write your username: ");
        String username = scan.next();
        System.out.print("Write your password: ");
        String password = scan.next();
        Singleton singleton = Singleton.getInstance();
        try (PreparedStatement preparedStatement = singleton.getConnection().prepareStatement("SELECT * FROM `userdb`.`users` WHERE USERNAME=? AND PASSWORD=?;")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet res = preparedStatement.executeQuery()) {
                if(res.next()){
                    System.out.println("Welcome " + username);
                }
                else{
                    System.out.println("Wrong log in or password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Welcome " + username);
        }
    }

    public static void signUp(){
        String username;
        String password;
        String repeatedPassword; 
    
        do{
            System.out.println("Please write your username: ");
            username = scan.next();
            if(isUsernameExists(username)){
                System.out.println("Username exists");
            }
        } while(isUsernameExists(username));
        
        do{
            System.out.print("Please write your password: ");
            password = scan.next();
            System.out.print("Please repeat your password: ");
            repeatedPassword = scan.next();
        } while (!checkPassword(password, repeatedPassword));
        
        Singleton singleton = Singleton.getInstance();
        
        try (PreparedStatement preparedStatement = singleton.getConnection().prepareStatement("INSERT INTO `users` (`username`, `password`) VALUES (?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate(); // Use executeUpdate() for INSERT
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public static boolean isUsernameExists(String username) {
        Singleton singleton = Singleton.getInstance();
        try (PreparedStatement preparedStatement = singleton.getConnection().prepareStatement("SELECT * FROM `userdb`.`users` WHERE USERNAME=?;")) {
            preparedStatement.setString(1, username);
            try (ResultSet res = preparedStatement.executeQuery()) {
                return res.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkPassword(String password, String repeatedPassword){
        if(password.equals(repeatedPassword)){
            return true;
        }
        return false;
    }
}
