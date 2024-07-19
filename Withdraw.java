public class Withdraw {
    // Constructor for initiating a withdrawal
    Withdraw(Double initialBalance, String username) {
        Utils.clearTerminal(); // Clear the terminal screen

        // Ask user to proceed with withdrawal or return to main menu
        boolean proceed = Utils.proceed("W I T H D R A W");
        if (!proceed) {
            new Choose(initialBalance, username); // Return to main menu
        }

        // Get the amount user wants to withdraw
        double firstBalance = Utils.getPositiveAmount("Enter amount you want to withdraw\nPhp: ", "W I T H D R A W");

        // Check if the withdrawal amount exceeds current balance
        if (firstBalance > initialBalance) {
            Utils.clearTerminal();
            System.out.println("W I T H D R A W");
            System.out.println("Insufficient balance. Available Balance: " + Utils.formatCurrency(initialBalance)
                    + "\nPress enter to continue");
            Utils.getStringInput("");
            new Choose(initialBalance, username); // Return to main menu
        }

        // Allow user to confirm or re-enter withdrawal amount
        double finalBalance = Utils.choice(firstBalance, initialBalance, username, "withdraw", "W I T H D R A W");

        // Process the withdrawal
        processWithdrawal(finalBalance, initialBalance, username);
    }

    // Method to process the withdrawal
    private void processWithdrawal(double balance, double initialBalance, String username) {
        // Calculate new balance after withdrawal
        double newBalance = initialBalance - balance;

        // Update user's balance in data handler
        HandleData.updateBalance(newBalance, username);

        // Record the transaction details
        String transaction = "TRANSACTIONS: CASH WITHDRAW/DATE: "
                + DateTime.formatDateTime(DateTime.getCurrentDateTime()) +
                "/AMOUNT: " + Utils.formatCurrency(balance) + "/TO: WITHDRAW/";
        HandleData.updateTransaction(transaction, username);

        // Notify user of successful withdrawal
        System.out.println("Withdrawal completed\nPress enter to continue");
        Utils.getStringInput(""); // Wait for user confirmation
        Utils.clearTerminal(); // Clear terminal screen

        // Return to main menu
        new Choose(newBalance, username);
    }
}
