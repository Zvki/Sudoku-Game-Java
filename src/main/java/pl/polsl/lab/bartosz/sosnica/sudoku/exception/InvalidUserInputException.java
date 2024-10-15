package pl.polsl.lab.bartosz.sosnica.sudoku.exception;

public class InvalidUserInputException extends Exception {

    public InvalidUserInputException() {
        super("User input wasn't provided in command line");
    }

    public InvalidUserInputException(String message) {
        super(message);
    }

}
