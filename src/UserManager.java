
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UserManager {



    public static void createNewUser(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter your username:");
        String userName = scanner.nextLine();

        System.out.println("Enter your password:");
        String userPassword = scanner.nextLine();

        String query = "INSERT INTO user_info (user_name, user_password) VALUES (?, ?)";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User created successfully!");
            } else {
                System.out.println("Error creating user.");
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}
