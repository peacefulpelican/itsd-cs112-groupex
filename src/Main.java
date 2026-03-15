import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String FILENAME = "records.ser";
    private Scanner scanner;
    private RecordManager rm;
    private FileHandler fh;

    public Main() {
        scanner = new Scanner(System.in);
        rm = new RecordManager();
        fh = new FileHandler(FILENAME);
    }

    public void start() {
        boolean quit = false;
        while (!quit) {
            displayMenu();
            int choice = getChoice(scanner);
            if (choice == -1) continue;
            if (execAction(choice, scanner, rm, fh)) quit = true;
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
        int choice;
        System.out.println("Enter Choice (1 - 5)");
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
            System.err.println("Please enter a valid option number");
            scanner.nextLine();
            return -1;
        }
        if  (choice < 1 || choice > 5) {
            System.out.println("Invalid choice. Enter a choice corresponding to a menu item,");
            return -1;
        }
        return choice;
    }

    private boolean execAction(int choice, Scanner scanner, RecordManager rm, FileHandler fh) {
        boolean quit = false;
        switch (choice) {
            case 1 -> addBook(scanner, rm);
            case 2 -> displayBooks(rm);
            case 3 -> saveRecords(rm, fh);
            case 4 -> loadRecords(rm, fh);
            case 5 -> quit = true;
        }
        return quit;
    }

    private void addBook(Scanner scanner, RecordManager rm)   {
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Book Year: ");
        int year;
        try {
            year = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Enter a valid year");
            scanner.nextLine();
            return;
        }
        try {
            rm.addRecord(new BookRecord(title, author, year));
        }
        catch (IllegalArgumentException e) {
            System.err.println("Unable to add book: " + e.getMessage());
        }
    }

    private void displayBooks(RecordManager rm) {
        rm.displayAllRecords();
    }

    private void saveRecords(RecordManager rm, FileHandler fh) {
        fh.saveRecords(rm.getAllRecords());
    }

    private void loadRecords(RecordManager rm, FileHandler fh) {
        try {
            rm.setRecords(fh.loadRecords());
        }
        catch (IllegalArgumentException e) {
            System.err.println("Unable to load records. Please try again.");
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.start();
    }
}
