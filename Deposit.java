public class Deposit {
    // Constructor for initializing the deposit process
    Deposit(double initialBalance, String userNumber) {

        // Clear the terminal screen
        Utils.clearTerminal();

        // Ask user to proceed with withdrawal or return to main menu
        boolean proceed = Utils.proceed("D E P O S I T");
        if (!proceed) {
            new Choose(initialBalance, userNumber); // Return to main menu
        }

        // Prompt user to enter the deposit amount
        double firstBalance = Utils.getPositiveAmount("Enter Amount You want to deposit\nPhp: ", "D E P O S I T");

        // Provide user with a choice and process the deposit
        double finalBalance = Utils.choice(firstBalance, initialBalance, userNumber, "deposit", "D E P O S I T");
        processDeposit(finalBalance, initialBalance, userNumber);
    }

    // Process the deposit amount and update balance and transaction
    private void processDeposit(double balance, double initialBalance, String userNumber) {
        // Calculate new balance after deposit
        double newBalance = initialBalance + balance;

        // Update the balance in the system
        HandleData.updateBalance(newBalance, userNumber);

        // Prepare transaction details for logging
        String transaction = "TRANSACTIONS: CASH DEPOSIT/DATE: "
                + DateTime.formatDateTime(DateTime.getCurrentDateTime()) +
                "/AMOUNT: " + Utils.formatCurrency(balance) + "/TO: DEPOSIT/";

        // Log the transaction in the system
        HandleData.updateTransaction(transaction, userNumber);

        // Notify user that deposit is completed
        System.out.println("Deposit completed\nPress enter to continue");

        // Wait for user confirmation before proceeding
        Utils.getStringInput("");

        // Clear the terminal screen for next operations
        Utils.clearTerminal();

        // Redirect user to further options after deposit
        new Choose(newBalance, userNumber);
    }
}
