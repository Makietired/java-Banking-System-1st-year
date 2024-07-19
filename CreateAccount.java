import java.util.Scanner;

public class CreateAccount {

    // Method to initiate account creation process
    public static void createAccount() {
        Scanner scan = new Scanner(System.in);
        String firstName, middleName, lastName, phoneNumber;

        System.out.println("C R E A T E  A C C O U N T\n");

        // Collecting user names
        System.out.print("Enter Your First Name\n-> ");
        firstName = scan.nextLine();
        System.out.print("Enter Your Middle Name\n-> ");
        middleName = scan.nextLine();
        System.out.print("Enter Your Last Name\n-> ");
        lastName = scan.nextLine();
        Utils.clearTerminal();

        // Creating full name in uppercase format
        String fullName = (lastName + ", " + firstName + " " + middleName + ".").toUpperCase();

        // Collecting and validating phone number
        phoneNumber = collectPhoneNumber(scan);

        // Collecting and validating password
        String password = collectPassword(scan);

        // Account creation success message
        Utils.clearTerminal();
        System.out.println("Account created successfully\nPress enter to continue");
        scan.nextLine();

        // Adding account data to storage
        HandleData.addData(phoneNumber, fullName, password);
        Utils.clearTerminal();
    }

    // Method to collect and validate phone number
    private static String collectPhoneNumber(Scanner scan) {
        String phoneNumber;
        System.out.println("C R E A T E  A C C O U N T\n");
        while (true) {
            System.out.print("Enter a 11-digit phone number starting with 09\n-> ");
            phoneNumber = scan.nextLine().trim();

            // Validate phone number length and starting digits
            if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09")) {
                Utils.clearTerminal();
                System.out.println("C R E A T E  A C C O U N T\n");
                System.out.println("Invalid phone number. Please try again.");
                continue;
            }

            // Validate numeric characters and uniqueness
            if (!isNumeric(phoneNumber) || HandleData.checkNumber(phoneNumber)) {
                Utils.clearTerminal();
                System.out.println("C R E A T E  A C C O U N T\n");
                System.out.println("Phone number already exists or contains invalid characters. Please try another.");
                continue;
            }

            break; // Valid phone number
        }
        return phoneNumber;
    }

    // Method to collect and validate password
    private static String collectPassword(Scanner scan) {
        String password1;
        Utils.clearTerminal();
        System.out.println("C R E A T E  A C C O U N T\n");
        while (true) {
            System.out.print("Enter password: ");
            password1 = scan.nextLine();
            System.out.print("Re-enter password: ");
            String password2 = scan.nextLine();

            // Check if passwords match
            if (password1.equals(password2)) {
                break; // Passwords match
            } else {
                Utils.clearTerminal();
                System.out.println("C R E A T E  A C C O U N T\n");
                System.out.println("Password mismatch. Please enter again.");
            }
        }
        return password1;
    }

    // Method to check if a string consists of only numeric characters
    private static boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
}
