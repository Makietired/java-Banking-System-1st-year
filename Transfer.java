import java.time.LocalDateTime;
import java.util.Scanner;

public class Transfer {
    private Scanner scan = new Scanner(System.in);

    // Constructor for initiating a transfer
    public Transfer(double initialBalance, String userNumber) {
        Utils.clearTerminal();

        // Ask user to proceed with withdrawal or return to main menu
        boolean proceed = Utils.proceed("T R A N S F E R");
        if (!proceed) {
            new Choose(initialBalance, userNumber); // Return to main menu
        }

        // Get recipient's number
        String receiverNumber = getReceiverNumber(userNumber);
        Utils.clearTerminal();

        // Get transfer amount from user
        double transferAmount = Utils.getPositiveAmount("Enter amount to transfer\nPhp: ", "T R A N S F E R");

        // Check if transfer amount exceeds current balance
        if (transferAmount > initialBalance) {
            Utils.clearTerminal();
            System.out.println("T R A N S F E R\n");
            System.out.println("Insufficient balance. Available Balance: " + Utils.formatCurrency(initialBalance)
                    + "\nPress enter to exit");
            Utils.getStringInput("");
            new Choose(initialBalance, userNumber); // Return to main menu
        }

        // Confirm the transfer details
        confirmTransfer(transferAmount, receiverNumber, initialBalance, userNumber);
    }

    // Method to get the receiver's number for transfer
    private String getReceiverNumber(String userNumber) {
        String receiverNumber;
        System.out.println("T R A N S F E R\n");

        // Loop until valid receiver number is entered
        while (true) {
            System.out.print("Enter recipient's number: ");
            receiverNumber = scan.nextLine();
            Utils.clearTerminal();

            // Check if the receiver's number exists and is not the same as sender's number
            if (HandleData.checkNumber(receiverNumber) && !receiverNumber.equals(userNumber)) {
                // Confirm recipient details or re-enter
                while (true) {
                    System.out.println("T R A N S F E R\n");
                    String choice = Utils
                            .getStringInput("You are transferring to \"" + HandleData.getName(receiverNumber)
                                    + "\"\n[1] Continue\n[2] Re-enter\n-> ");
                    if (choice.equals("1")) {
                        return receiverNumber; // Return valid receiver number
                    } else if (choice.equals("2")) {
                        Utils.clearTerminal();
                        System.out.println("T R A N S F E R\n");
                        break; // Retry entering receiver number
                    } else {
                        Utils.clearTerminal(); // Invalid choice, clear terminal for re-entry
                    }
                }
            } else if (receiverNumber.equals(userNumber)) {
                Utils.clearTerminal();
                System.out.println("T R A N S F E R\n");
                System.out.println("Transferring to own account is not allowed or number is invalid.");
            } else {
                Utils.clearTerminal();
                System.out.println("T R A N S F E R\n");
                System.out.println("Invalid input. Please enter a valid recipient's number.");
            }
        }
    }

    // Method to confirm transfer details
    private void confirmTransfer(double transferAmount, String receiverNumber, double initialBalance,
            String userNumber) {
        Utils.clearTerminal();

        // Loop until user confirms or exits transfer
        while (true) {
            System.out.println("Amount: " + Utils.formatCurrency(transferAmount) +
                    "\nRecipient: " + HandleData.getName(receiverNumber) +
                    "\nNumber: " + receiverNumber);
            String choice = Utils.getStringInput("\n[1] Continue\t[2] Exit\n-> ");
            if (choice.equals("1")) {
                Utils.clearTerminal();
                break; // Continue with the transfer
            } else if (choice.equals("2")) {
                Utils.clearTerminal();
                new Choose(initialBalance, userNumber); // Return to main menu
            } else {
                Utils.clearTerminal(); // Invalid choice, clear terminal for re-entry
            }
        }

        // Finalize the transfer
        double finalTransferAmount = Utils.choice(transferAmount, initialBalance, userNumber, "transfer",
                "T R A N S F E R");
        executeTransfer(finalTransferAmount, receiverNumber, initialBalance, userNumber);
    }

    // Method to execute the transfer
    private void executeTransfer(double transferAmount, String receiverNumber, double initialBalance,
            String userNumber) {
        // Calculate new balances
        double senderBalance = HandleData.getBalance(userNumber) - transferAmount;
        double receiverBalance = HandleData.getBalance(receiverNumber) + transferAmount;

        // Update balances in the data handler
        HandleData.updateBalance(senderBalance, userNumber);
        HandleData.updateBalance(receiverBalance, receiverNumber);

        // Record transaction details
        LocalDateTime dateTime = DateTime.getCurrentDateTime();
        String transaction = "TRANSACTIONS: CASH TRANSFER/DATE: " + DateTime.formatDateTime(dateTime) +
                "/AMOUNT: " + Utils.formatCurrency(transferAmount) + "/TO: " + receiverNumber + "/";

        HandleData.updateTransaction(transaction, userNumber);

        // Notify user and proceed
        System.out.println("Transfer completed\nPress enter to continue");
        Utils.getStringInput(""); // Wait for user confirmation
        Utils.clearTerminal(); // Clear terminal for next operation
    }
}
