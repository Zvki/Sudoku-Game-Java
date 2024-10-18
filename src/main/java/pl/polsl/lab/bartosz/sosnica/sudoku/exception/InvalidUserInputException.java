package pl.polsl.lab.bartosz.sosnica.sudoku.exception;

/**
 * Custom exception class for handling invalid user input.
 * This exception is thrown when the user input is missing or incorrect.
 */
public class InvalidUserInputException extends Exception {

    /**
     * Default constructor that creates an exception with a predefined message.
     * The message indicates that the user input was not provided via the command line.
     */
    public InvalidUserInputException() {
        super("User input wasn't provided in command line");
    }

    /**
     * Constructor for creating an exception with a custom message.
     *
     * @param message the detailed error message explaining the issue with user input.
     */
    public InvalidUserInputException(String message) {
        super(message);
    }

}
