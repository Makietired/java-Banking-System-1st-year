/*
 * A simple Banking System ("This is my first program, incorporating all the knowledge I've gained during my first year in college.")
 * Author:Mark Cesar Sabado
 * Date: July 20, 2024
 */

import java.util.Scanner;

public class Main {
    // Class-level variables
    static int error = 0; // Counter for login attempts

    public static void main(String[] args) {
        Utils.clearTerminal(); // Clear the terminal screen
        Scanner scan = new Scanner(System.in);

        // Main loop for account creation and login
        while (true) {
            displayWelcomeMenu(); // Display the welcome menu options
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    Utils.clearTerminal();
                    CreateAccount.createAccount(); // Invoke account creation process
                    break;
                case "2":
                    loginUser(scan); // Invoke login process
                    break;
                default:
                    Utils.clearTerminal();
                    System.out.println("Invalid option. Please try again."); // Invalid input handling
                    break;
            }
        }
    }

    // Method to display the welcome menu options
    private static void displayWelcomeMenu() {
        System.out.println("W E L C O M E\n");
        System.out.print("[1] Create account\n[2] Log-in\n-> ");
    }

    // Method to handle the login process
    private static void loginUser(Scanner scan) {
        String number;
        Utils.clearTerminal();
        System.out.println("L O G - I N\n");

        // Login process
        while (true) {
            System.out.print("Enter Number: ");
            number = scan.nextLine();
            Utils.clearTerminal();
            System.out.println("L O G - I N\n");

            // Check if the entered number exists in the system
            if (HandleData.checkNumber(number)) {
                break; // Exit the loop if number is found
            } else {
                System.out.println("Number not registered"); // Inform user if number is not registered
            }
        }

        attemptLogin(scan, number); // Proceed to attempt login with valid number
    }

    // Method to attempt user login
    private static void attemptLogin(Scanner scan, String number) {
        while (true) {
            System.out.print("Enter Password: ");
            String password = scan.nextLine();

            // Check login credentials and retrieve user balance if successful
            double userBalance = HandleData.checkLogin(password, number);

            Utils.clearTerminal();
            System.out.println("L O G - I N\n");

            // If login successful (userBalance > -1.0), proceed to user options
            if (userBalance > -1.0) {
                new Choose(userBalance, number); // Redirect to user options
                break; // Exit login attempt loop
            } else {
                error++; // Increment error count for failed attempts
                // Check if maximum attempts reached
                if (error >= 5) {
                    Utils.clearTerminal();
                    System.out.println("Too many failed login attempts. Try again later.");
                    System.exit(0); // Exit the program if max attempts exceeded
                } else {
                    Utils.clearTerminal();
                    System.out.println("Invalid password. Attempts left: " + (5 - error));
                }
            }
        }
    }
}
