
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserProfile extends SessionManager {
    private static String userName;
    private static int id;
    private static String password ;

    UserProfile(String userName , int id , String password) {
        this.userName= userName;
        this.password = password;
        this.id = id;
    }

    public static void viewBorrowedBooks() {
        String query = "SELECT * FROM books_borrowed WHERE student_name = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String bookName = resultSet.getString("book_name");
                String returnDate = resultSet.getString("return_date");

                System.out.printf("%-10d %-25s %-25s\n", studentId, bookName, returnDate);
            }

            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void returnBook(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the name of the book you want to return:");
        String bookName = scanner.nextLine();

        String query = "DELETE FROM books_borrowed WHERE student_id = ? AND book_name = ?";

        try {

            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, bookName);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                String updateBookQuery = "UPDATE books SET quantity = quantity + 1 WHERE book_name = ?";
                PreparedStatement updateBookStatement = connection.prepareStatement(updateBookQuery);
                updateBookStatement.setString(1, bookName);
                updateBookStatement.executeUpdate();

                System.out.println("Book returned successfully!");
            } else {
                System.out.println("No matching book found in borrowed list.");
            }

            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatePassword() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your current password:");
        String currentPassword = scanner.nextLine();

        if (!currentPassword.equals(password)) {
            System.out.println("Incorrect current password!");
            return;
        }

        System.out.println("Enter your new password:");
        String newPassword = scanner.nextLine();

        String query = "UPDATE user_info SET user_password = ? WHERE user_id = ?";

        try {

            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                password = newPassword;
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Error updating password.");
            }

            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
    }
}
