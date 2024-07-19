import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    // Formatter for numeric values
    private static DecimalFormat formatter;
    // Formatter for date and time
    private static DateTimeFormatter dateTimeFormatter;

    // Static block to initialize the formatters
    static {
        formatter = new DecimalFormat("#,###.00");
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    // Method to format a number to a specific decimal format
    public static String formatNumber(Double number) {
        return formatter.format(number);
    }

    // Method to format a LocalDateTime object to a string
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }

    // Method to format a LocalDate object to a string
    public static String formatDate(LocalDate date) {
        return date.toString(); // Returns date in ISO format (yyyy-MM-dd)
    }

    // Method to format a LocalTime object to a string
    public static String formatTime(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();

        // Format the time components with leading zeros if necessary
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    // Method to get the current date and time
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(); // Returns the current date and time
    }
}
