/*Important note...
Ensure that the table structure (mytable in this case) matches your actual database schema where user data is stored.
use this column to avoid error
Password | Number | userBalance | transactions | Name

Password: Stores the password associated with each user account. 
          This is used for authentication and should be stored securely, often through encryption or hashing methods.

Number: Represents a unique identifier or account number associated with each user. \
        This column helps uniquely identify each user's account within the database.

userBalance: Stores the current balance of each user's account. 
             It reflects the amount of money available in the account at any given time.

transactions: This column likely stores a history of transactions associated with each user account. 
              It can include details such as transaction types 
              (e.g., deposit, withdrawal, transfer), dates, amounts, and other relevant transaction information.

Name: Stores the name or username associated with each user account. 
      It typically represents the account holder's name or a chosen username for identification purposes.
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HandleData {
    // Database connection parameters
    static String url = "jdbc:mysql://localhost:3306/bankingdata"; // JDBC URL for MySQL database
    static String username = "root"; // Username for database access
    static String password = "Razec012605"; // Password for database access
    static String tableName = "mytable"; // Name of the table in the database

    // Method to check login credentials and retrieve user balance
    public static double checkLogin(String providedPassword, String providedNumber) {
        double balance = -1.0;

        // SQL query to select user data based on Number and case-sensitive Password
        // comparison
        String loginQuery = "SELECT * FROM " + tableName + " WHERE Number = ? AND BINARY Password = ?";

        try (
                // Establishing a connection to the database
                Connection connection = DriverManager.getConnection(url, username, password);
                // Preparing a statement with placeholders for parameterized query
                PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {

            // Setting parameters in the prepared statement
            preparedStatement.setString(1, providedNumber); // Bind Number parameter
            preparedStatement.setString(2, providedPassword); // Bind Password parameter using BINARY for case
                                                              // sensitivity

            // Executing the query and obtaining the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Checking if there is a matching user
            if (resultSet.next()) {
                // Return the user's balance if login succeeds
                return resultSet.getDouble("userBalance");
            }

            // Return -1.0 if login fails (no matching credentials found)
            return balance;

        } catch (SQLException e) {
            // Print the stack trace if a SQL exception occurs
            e.printStackTrace();
            // Return -1.0 in case of SQL exception
            return balance;
        }
    }

    // Method to retrieve user's balance based on provided number
    public static double getBalance(String providedNumber) {
        double balance = 0.0;
        String query = "SELECT * FROM " + tableName + " WHERE Number = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, providedNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("userBalance");
            }
            return balance; // Return 0.0 if user not found or balance not retrieved
        } catch (SQLException e) {
            e.printStackTrace();
            return balance; // Return 0.0 in case of SQL exception
        }
    }

    // Method to retrieve user's name based on provided number
    public static String getName(String providedNumber) {
        String userName = null;
        String query = "SELECT * FROM " + tableName + " WHERE Number = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, providedNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("Name");
            }
            return userName; // Return null if user not found or name not retrieved
        } catch (SQLException e) {
            e.printStackTrace();
            return userName; // Return null in case of SQL exception
        }
    }

    // Method to update user's balance in the database
    public static void updateBalance(double newBalance, String number) {
        String updateQuery = "UPDATE " + tableName + " SET userBalance = ? WHERE Number = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setDouble(1, newBalance);
            updateStatement.setString(2, number);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update transaction history for the user in the database
    public static void updateTransaction(String transaction, String number) {
        String updateQuery = "UPDATE " + tableName
                + " SET transactions = IFNULL(CONCAT(transactions, ?), ?) WHERE Number = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, transaction);
            statement.setString(2, transaction);
            statement.setString(3, number);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch transaction history for the user from the database
    public static String fetchData(String number) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT transactions FROM " + tableName + " WHERE Number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, number);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String transactions = result.getString("transactions");
                if (transactions != null && !transactions.isEmpty()) {
                    // Format transaction data for display
                    String[] values = transactions.split("/");
                    StringBuilder dataBuilder = new StringBuilder();
                    dataBuilder.append("T R A N S A C T I O N S:\n");
                    dataBuilder.append("*************************************************************\n");
                    int line = 0;
                    for (String value : values) {
                        if (line == 4) {
                            dataBuilder.append("*************************************************************\n");
                            dataBuilder.append(value.trim()).append("\n");
                            line = 0;
                        } else {
                            dataBuilder.append(value.trim()).append("\n");
                        }
                        line++;
                    }
                    return dataBuilder.toString();
                } else {
                    return "   NO TRANSACTION RECORD";
                }
            } else {
                System.err.println("User not found: " + number);
            }

            result.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
        return ""; // Return empty string if an error occurs
    }

    // Method to delete transaction history for the user in the database
    public static void deleteTransactions(String number) {
        String updateQuery = "UPDATE " + tableName + " SET transactions = '' WHERE Number = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, number);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a number exists in the database (for login validation)
    public static boolean checkNumber(String number) {
        String query = "SELECT 1 FROM " + tableName + " WHERE Number = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, number);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next(); // Return true if number exists in database
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // Return false in case of SQL exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of connection or statement creation error
        }
    }

    // Method to add new user data to the database
    public static void addData(String number, String name, String userPassword) {
        String insertQuery = "INSERT INTO " + tableName
                + " (Number, Name, Password, userBalance, transactions) VALUES (?, ?, ?, 0.0, '')";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, userPassword);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account created successfully");
            } else {
                System.out.println("No rows affected, account not created.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
