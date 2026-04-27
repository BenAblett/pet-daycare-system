package utils;

import java.util.Scanner;

/**
 * This class provides methods for the robust handling of I/O using Scanner.
 * It creates a new Scanner object for each read from the user, thereby
 * eliminating the Scanner bug (where the buffers don't flush correctly after an int read).
 *
 * The methods also parse the numeric data entered to ensure it is correct. If it isn't correct,
 * the user is prompted to enter it again.
 *
 * @author Siobhan Drohan, Mairead Meagher, Siobhan Roche
 * @version 1.0
 *
 */

public class ScannerInput {

    /**
     * Read an int from the user.  If the entered data isn't actually an int,
     * the user is prompted again to enter the int.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The number read from the user and verified as an int.
     */
    public static int readNextInt(String prompt) {
        do {
            var scanner = new Scanner(System.in);
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.next());
            }
            catch (NumberFormatException e) {
                System.err.println("\tEnter a number please.");
            }
        }  while (true);
    }

    /**
     * Read a double from the user.  If the entered data isn't actually a double,
     * the user is prompted again to enter the double.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The number read from the user and verified as a double.
     */
    public static double readNextDouble(String prompt) {
        do {
            var scanner = new Scanner(System.in);
            try{
                System.out.print(prompt);
                return Double.parseDouble(scanner.next());
            }
            catch (NumberFormatException e) {
                System.err.println("\tEnter a number please.");
            }
        }  while (true);
    }

    /**
     * Read a line of text from the user.  There is no validation done on the entered data.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The String read from the user.
     */
    public static String readNextLine(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        return input.nextLine();
    }

    /**
     * Read a single character of text from the user.  There is no validation done on the entered data.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The char read from the user.
     */
    public static char readNextChar(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        return input.next().charAt(0);
    }

    public static boolean readNextBoolean(String prompt) {
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print(prompt + " (yes/no): ");
            String input = scanner.next().toLowerCase();

            if (input.equals("yes") || input.equals("y")) return true;
            if (input.equals("no") || input.equals("n")) return false;

            System.err.println("\tEnter true or false please.");
        } while (true);
    }

    private int readIntInRange(String prompt, int min, int max) {
        int value;
        do {
            value = ScannerInput.readNextInt(prompt);
            if (value < min || value > max) {
                System.out.println("Invalid input. Enter a number between " + min + " and " + max);
            }
        } while (value < min || value > max);
        return value;
    }

    private String readNonEmptyString(String prompt) {
        String input;
        do {
            input = ScannerInput.readNextLine(prompt);
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty.");
            }
        } while (input.trim().isEmpty());
        return input;
    }

    private boolean readYesNo(String prompt) {
        String input;
        do {
            input = ScannerInput.readNextLine(prompt + " (y/n): ").toLowerCase();
            if (!input.equals("y") && !input.equals("n")) {
                System.out.println("Enter y or n only.");
            }
        } while (!input.equals("y") && !input.equals("n"));

        return input.equals("y");
    }

    private char readSex(String prompt) {
        char c;
        do {
            c = Character.toUpperCase(ScannerInput.readNextChar(prompt));
            if (c != 'M' && c != 'F') {
                System.out.println("Enter M or F only.");
            }
        } while (c != 'M' && c != 'F');

        return c;
    }

    private double readPositiveDouble(String prompt) {
        double value;
        do {
            value = ScannerInput.readNextDouble(prompt);
            if (value < 0) {
                System.out.println("Value must be >= 0.");
            }
        } while (value < 0);

        return value;
    }

}
