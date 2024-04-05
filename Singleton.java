import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public final class Singleton {
    private static Singleton instance;
    public Statement statement;
    public Connection connection;

    JSONParser parser = new JSONParser();


    private Singleton() {
        try {
            Object obj = parser.parse(new FileReader("C:\\Users\\User\\Desktop\\Patterns\\Assignment3\\config.json"));
            JSONObject jsonObject =  (JSONObject) obj;
            String url = (String) jsonObject.get("url");
            String username = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            this.statement = statement;
            this.connection = connection;
        } catch (SQLException | IOException | ParseException e) { 
            e.printStackTrace();
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public Statement getStatement() {
        return statement;
    }

    public Connection getConnection() {
        return connection;
    }
}
