import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RecordManager rm = new RecordManager();
        FileHandler fh = new FileHandler("");

        boolean quit = false;
        while (!quit) {
            displayMenu();
            int choice = getChoice(scanner);
            if (choice == -1) continue;
            quit = execAction(choice, scanner, rm, fh);
        }
    }

    private static void displayMenu() {
        System.out.println("Book Records Menu:");
        System.out.println("1. Add Book");
        System.out.println("2. Display Books");
        System.out.println("3. Save Records");
        System.out.println("4. Load Records");
        System.out.println("5. Exit");
    }

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

    private static boolean execAction(int choice, Scanner scanner, RecordManager rm, FileHandler fh) {
        switch (choice) {
            case 1 -> addBook(scanner, rm);
            case 2 -> displayBooks(rm);
            case 3 -> saveRecords(scanner, rm, fh);
            case 4 -> loadRecords(scanner, rm, fh);
            case 5 -> { return true; }
        }
        return false;
    }

    private static void addBook(Scanner scanner, RecordManager rm) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter year: ");
        try {
            int year = scanner.nextInt();
            scanner.nextLine();
            BookRecord book = new BookRecord(title, author, year);
            rm.addRecord(book);
            System.out.println("Record with ID " + book.getId() + " added successfully.");
        } catch (InputMismatchException e) {
            System.err.println("Invalid year. Please enter a number.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.err.println("Unable to add book: " + e.getMessage());
        }
    }

    private static void displayBooks(RecordManager rm) {
        rm.displayAllRecords();
    }

    private static void saveRecords(Scanner scanner, RecordManager rm, FileHandler fh) {
        System.out.print("Enter filename to save to: ");
        String fileName = scanner.nextLine();
        fh.setFileName(fileName);
        fh.saveRecords(rm.getAllRecords());
    }

    private static void loadRecords(Scanner scanner, RecordManager rm, FileHandler fh) {
        System.out.print("Enter filename to load from: ");
        String fileName = scanner.nextLine();
        fh.setFileName(fileName);
        ArrayList<DisplayableRecord> records = fh.loadRecords();
        if (records != null) {
            try {
                rm.setRecords(records);
            } catch (IllegalArgumentException e) {
                System.err.println("Unable to load records: " + e.getMessage());
            }
        }
    }
}