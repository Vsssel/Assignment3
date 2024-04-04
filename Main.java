import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Singleton singleton = Singleton.getInstance();
        ResultSet res = singleton.getStatement().executeQuery("SELECT * FROM USERS");


        if (res.next()) {
            String ans = res.getString("username");
            System.out.println(ans);
        } else {
            System.out.println("No users found.");
        }
        singleton.getStatement().close();
    }
}
