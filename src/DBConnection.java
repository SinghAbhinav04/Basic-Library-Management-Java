
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private DBConnection(){
    }

    public static Connection getConnection() throws SQLException , ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, USERNAME,PASSWORD);
    }
}
