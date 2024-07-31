
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SessionManager {

    private static String name;
    private static String password;
    private static int id;

    public static boolean verifyExistingUser(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter your username:");
        String userName = scanner.nextLine();

        System.out.println("Enter your password:");
        String userPassword = scanner.nextLine();


        String query = "SELECT * FROM user_info WHERE user_name =? AND user_password =?";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("user_id");
                name = userName;
                password = userPassword;

                BookController bookController = new BookController(name,id);
                UserProfile userProfile = new UserProfile(name,id,password);

                System.out.println("Welcome back, " + userName + "!");
                return true;
            } else {
                System.out.println("Incorrect username or password!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean logout() {
        return false;
    }

    public static String getUserName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }

    public static int getId() {
        return id;
    }

    public static void main(String[] args) {
    }
}
