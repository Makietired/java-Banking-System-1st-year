import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Utils {
    private static Scanner scanner = new Scanner(System.in);
    private static DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

    // Method to get a positive amount from user input
    public static double getPositiveAmount(String prompt, String heading) {
        double amount = 0;
        System.out.println(heading + "\n");
        while (true) {
            try {
                System.out.print(prompt);
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    clearTerminal();
                    System.out.println(heading + "\n");
                    System.out.println("Amount must be greater than zero.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                clearTerminal();
                System.out.println(heading + "\n");
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Method to get string input from user
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Method to clear the terminal screen
    public static void clearTerminal() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // For Windows
            // new ProcessBuilder("clear").inheritIO().start().waitFor(); // For
            // Unix/Linux/Mac
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to format currency
    public static String formatCurrency(double amount) {
        return "Php " + decimalFormat.format(amount);
    }

    // Method to confirm user's choice to proceed or return
    public static boolean proceed(String prompt) {
        System.out.println(prompt);
        System.out.print("[1] Continue\n[2] Return\n-> ");
        while (true) {
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                clearTerminal();
                return true;
            } else if (choice.equals("2")) {
                return false;
            } else {
                // Handle invalid input (if any)
            }
        }
    }

    // Method to handle user's choice with balance and prompt for action
    public static double choice(double balance, double initialBalance, String username, String prompt, String heading) {
        while (true) {
            clearTerminal();
            System.out.println(heading);
            System.out.println("You entered " + formatCurrency(balance) +
                    "\n[1] Continue\n[2] Re-enter amount\n[3] Exit\n-> ");
            String choice = getStringInput("");
            if (choice.equals("1")) {
                clearTerminal();
                return balance;
            } else if (choice.equals("2")) {
                clearTerminal();
                balance = getPositiveAmount("Enter Amount You want to " + prompt + "\nPhp: ", heading);
            } else if (choice.equals("3")) {
                new Choose(initialBalance, username); // Return to main menu
            }
        }
    }
}
