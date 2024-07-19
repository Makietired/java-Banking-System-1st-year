import java.text.DecimalFormat;
import java.util.Scanner;

public class Choose {
    private DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    private Scanner scan = new Scanner(System.in);
    private double balance;
    private String username;

    public Choose(double balance, String username) {
        this.balance = balance; // Store the initial balance
        this.username = username; // Store the username
        Utils.clearTerminal(); // Clear the terminal screen
        displayMenu(); // Display the main menu
        handleUserChoice(); // Wait for and handle user input
    }

    private void displayMenu() {
        // Display the main menu options
        System.out.println("\t  H O M E\n");
        System.out.print(
                "[1] Withdraw\t  [5] Transfer\n" +
                        "[2] Deposit\t  [6] Exit\n" +
                        "[3] Check Balance\n" +
                        "[4] See Transaction History\n" +
                        "-> ");
    }

    private void handleUserChoice() {
        // Handle user input based on selected menu option
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                new Withdraw(balance, username); // Initiate withdrawal process
                break;
            case "2":
                new Deposit(balance, username); // Initiate deposit process
                break;
            case "3":
                checkBalance(); // Display current balance
                break;
            case "4":
                seeTransactionHistory(); // Display transaction history
                break;
            case "5":
                new Transfer(balance, username); // Initiate transfer process
                break;
            case "6":
                System.exit(0); // Exit the program
                break;
            default:
                new Choose(balance, username); // Loop back to menu for invalid input
                break;
        }
    }

    private void checkBalance() {
        // Display the current balance
        Utils.clearTerminal(); // Clear the terminal screen
        System.out.println("\t  H O M E\n");
        System.out.println("Available Balance: Php " + decimalFormat.format(balance));
        System.out.println("Press enter to continue");
        scan.nextLine(); // Wait for user to press Enter
        Utils.clearTerminal(); // Clear the terminal screen
        new Choose(balance, username); // Return to main menu
    }

    private void seeTransactionHistory() {
        // Display transaction history and handle options
        Utils.clearTerminal(); // Clear the terminal screen
        System.out.println(HandleData.fetchData(username)); // Fetch and display transaction data
        System.out.print("[1] Continue\t[2] Delete all\n->");

        while (true) {
            String choice = Utils.getStringInput(""); // Get user input for action choice

            if (choice.equals("1")) {
                break; // Continue to main menu
            } else if (choice.equals("2")) {
                HandleData.deleteTransactions(username); // Delete all transactions for user
                Utils.clearTerminal(); // Clear the terminal screen
                System.out.println("Transactions deleted\nPress enter to continue");
                Utils.getStringInput(""); // Wait for user to press Enter
                break; // Continue to main menu
            } else {
                Utils.clearTerminal(); // Clear the terminal screen
                seeTransactionHistory(); // Show history again for invalid input
            }
        }
        Utils.clearTerminal(); // Clear
    }
}