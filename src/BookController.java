
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

public class BookController extends SessionManager {

    private static String userName;
    private static int id;

    BookController(String userName , int id) {
        this.userName =  userName;
        this.id = id;

    }

    public static void addBook(String bookName, String authorName, int quantity) {
        String query = "INSERT INTO books(book_name, author_name, quantity) VALUES (?, ?, ?)";

        try {
            if (userName.equals("ADMINISTRATOR")) {
                String existingBookQuery = "SELECT * FROM books WHERE book_name = ?";

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DBConnection.getConnection();
                PreparedStatement existingBookStatement = connection.prepareStatement(existingBookQuery);

                existingBookStatement.setString(1, bookName);
                ResultSet result = existingBookStatement.executeQuery();

                if (result.next()) {
                    String updateQuantityQuery = "UPDATE books SET quantity = quantity + ? WHERE book_name = ?";
                    PreparedStatement updateQuantityStatement = connection.prepareStatement(updateQuantityQuery);

                    updateQuantityStatement.setInt(1, quantity);
                    updateQuantityStatement.setString(2, bookName);
                    updateQuantityStatement.executeUpdate();

                    System.out.println("Book successfully added to library");
                } else {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, bookName);
                    preparedStatement.setString(2, authorName);
                    preparedStatement.setInt(3, quantity);

                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Rows Affected: " + rowsAffected);

                    connection.close();
                }
            } else {
                System.out.println("Sorry, you are not authorized to add books.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewBooks() {
        String query = "SELECT * FROM books";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bookID = resultSet.getInt("book_id");
                String bookName = resultSet.getString("book_name");
                String authorName = resultSet.getString("author_name");

                System.out.printf("%-10d %-25s %-25s\n", bookID, bookName, authorName);
            }

            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void borrowBook(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the name of the book you want to borrow:");
        String bookName = scanner.nextLine();

        String query = "SELECT * FROM books WHERE book_name =?";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int bookAvailable = resultSet.getInt("quantity");
                if (bookAvailable >= 1) {

                    LocalDate today = LocalDate.now();
                    String returnDate = today.plusMonths(1).toString();

                    String insertQuery = "INSERT INTO books_borrowed (student_id, student_name, book_name, return_date) VALUES (?,?,?,?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                    insertStatement.setInt(1, id);
                    insertStatement.setString(2, userName);
                    insertStatement.setString(3, bookName);
                    insertStatement.setString(4, returnDate);

                    insertStatement.executeUpdate();

                    String queryForBook = "UPDATE books SET quantity = quantity -1 WHERE book_name =?";
                    PreparedStatement updateStatement = connection.prepareStatement(queryForBook);

                    updateStatement.setString(1, bookName);
                    updateStatement.executeUpdate();

                    System.out.println("Book borrowed successfully!");
                } else {
                    System.out.println("Book not available in library");
                }
            } else {
                System.out.println("Book not available in library");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchForBook(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the name of the book you want to search:");
        String bookName = scanner.nextLine();

        String query = "SELECT * FROM books WHERE book_name LIKE ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "%" + bookName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No books found.");
            }

            while (resultSet.next()) {
                int bookID = resultSet.getInt("book_id");
                String resultBookName = resultSet.getString("book_name");
                String authorName = resultSet.getString("author_name");
                int quantity = resultSet.getInt("quantity");

                System.out.printf("%-10d %-25s %-25s %-10d\n", bookID, resultBookName, authorName, quantity);
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
