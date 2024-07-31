import java.util.Scanner;

public class LibraryManager {

    public static void handleBookOperations(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("1) View books\n2) Search for book\n3) Borrow a book\n4) Go back");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    BookController.viewBooks();
                    break;
                case 2:
                    BookController.searchForBook(scanner);
                    break;
                case 3:
                    BookController.borrowBook(scanner);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Choose a correct option");
                    break;
            }
        }
    }

    public static void handleUserProfileOperations(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("1) View borrowed books\n2) Return a book\n3) Update password\n4) Go back");
            int input = scanner.nextInt();

            switch (input) {
                case 1:
                    UserProfile.viewBorrowedBooks();
                    break;
                case 2:
                    UserProfile.returnBook(scanner);
                    break;
                case 3:
                    UserProfile.updatePassword();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Choose a correct option");
                    break;
            }
        }
    }

    public static boolean handleUserOperations(Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("1) Explore book section\n2) Your profile\n3) Logout");
            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    handleBookOperations(scanner);
                    break;
                case 2:
                    handleUserProfileOperations(scanner);
                    break;
                case 3:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Choose a correct option");
                    break;
            }
        }
        return loggedIn;
    }

    public static void main(String[] args) {
        boolean loggedIn = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! Welcome to the Library Management Program");

        while (true) {
            System.out.println("1) New user\n2) Existing user\n3) Exit");
            int input = scanner.nextInt();

            switch (input) {
                case 1:
                    UserManager.createNewUser(scanner);
                    break;
                case 2:
                    boolean isLoggedIn = SessionManager.verifyExistingUser(scanner);
                    if (isLoggedIn) {
                        loggedIn = handleUserOperations(scanner);
                    } else {
                        System.out.println("Try again");
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Choose a correct option");
                    break;
            }
        }
    }
}
