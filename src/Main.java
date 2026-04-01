import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Basic book records application.
 * Provides a menu-driven console interface for manging simple book records.
 */

public class Main {
    /**
     * Entry point. Initialised components and run the menu until the user chooses to exit.
     * @param args (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RecordManager recordManager = new RecordManager();
        FileHandler fileHandler = new FileHandler("");

        boolean quit = false;
        while (!quit) {
            displayMenu();
            int choice = getChoice(scanner);
            if (choice == -1) continue;
            quit = execAction(choice, scanner, recordManager, fileHandler);
        }
    }

    /**
     * Prints the main menu options to standard output.
     */
    private static void displayMenu() {
        System.out.println("Book Records Menu:");
        System.out.println("1. Add Book");
        System.out.println("2. Display Books");
        System.out.println("3. Save Records");
        System.out.println("4. Load Records");
        System.out.println("5. Exit");
    }

    /**
     * Prompts the user for a menu selection and validates input
     * @param scanner scanner to read from standard input
     * @return validated menu choice (1-5) or -1 if input is invalid or out of range.
     * */
    private static int getChoice(Scanner scanner) {
        System.out.print("Enter choice (1 - 5): ");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Enter a choice corresponding to a menu item.");
                return -1;
            }
            return choice;
        } catch (InputMismatchException e) {
            System.err.println("Please enter a valid option number.");
            scanner.nextLine();
            return -1;
        }
    }

    /**
     * Actions the appropriate method depending on user's selected menu choice.
     * @param choice the validated menu choice
     * @param scanner scanner to read from standard input
     * @param recordManager the RecordManager object holding the current recordset
     * @param fileHandler FileHandler object to handle file I/O for saving or restoring records
     * @return true if user has selected exit (5), false otherwise
     */
    private static boolean execAction(int choice, Scanner scanner, RecordManager recordManager, FileHandler fileHandler) {
        switch (choice) {
            case 1 -> addBook(scanner, recordManager);
            case 2 -> displayBooks(recordManager);
            case 3 -> saveRecords(scanner, recordManager, fileHandler);
            case 4 -> loadRecords(scanner, recordManager, fileHandler);
            case 5 -> { return true; }
        }
        return false;
    }

    /**
     * Collects book details from user and adds a new BookRecord to the record manager.
     * @param scanner scanner to read from standard input
     * @param recordManager current records set
     */
    private static void addBook(Scanner scanner, RecordManager recordManager) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter year: ");
        try {
            int year = scanner.nextInt();
            scanner.nextLine(); // absorb with trailing newline since scanner is reused elsewhere
            BookRecord book = new BookRecord(title, author, year);
            recordManager.addRecord(book);
            System.out.println("Record with ID " + book.getId() + " added successfully.");
        } catch (InputMismatchException e) {
            System.err.println("Invalid year. Please enter a number.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.err.println("Unable to add book: " + e.getMessage());
        }
    }

    /**
     * Calls on RecordManager.displayAllRecords to print currently held book records.
     * @param recordManager current records set
     */
    private static void displayBooks(RecordManager recordManager) {
        recordManager.displayAllRecords();
    }

    /**
     * Prompts user for a file name and saves all current records to that file
     * @param scanner scanner to read from standard input
     * @param recordManager current record set (RecordManager object)
     * @param fileHandler FileHander object to handle I/O operations.
     */
    private static void saveRecords(Scanner scanner, RecordManager recordManager, FileHandler fileHandler) {
        System.out.print("Enter filename to save to: ");
        String fileName = scanner.nextLine();
        fileHandler.setFileName(fileName);
        fileHandler.saveRecords(recordManager.getAllRecords());
    }

    /**
     * Prompts user for a file name, loads records from that file and replaces the current record set.
     * @param scanner scanner to read from standard input.
     * @param recordManager
     * @param fileHandler
     */
    private static void loadRecords(Scanner scanner, RecordManager recordManager, FileHandler fileHandler) {
        System.out.print("Enter filename to load from: ");
        String fileName = scanner.nextLine();
        fileHandler.setFileName(fileName);
        ArrayList<DisplayableRecord> records = fileHandler.loadRecords();
        if (records != null) {
            try {
                recordManager.setRecords(records);
            } catch (IllegalArgumentException e) {
                System.err.println("Unable to load records: " + e.getMessage());
            }
        }
    }
}