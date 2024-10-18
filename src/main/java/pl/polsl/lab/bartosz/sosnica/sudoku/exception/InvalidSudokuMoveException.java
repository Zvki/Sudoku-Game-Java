package pl.polsl.lab.bartosz.sosnica.sudoku.exception;

/**
 * Custom exception class for handling invalid Sudoku moves.
 * This exception is thrown when a move violates the rules of Sudoku.
 */
public class InvalidSudokuMoveException extends Exception {

    /**
     * Constructor for creating an exception with a custom message.
     *
     * @param message the detailed error message explaining the invalid move.
     */
    public InvalidSudokuMoveException(String message) {
        super(message);
    }

    /**
     * Default constructor for creating an exception with a predefined message.
     * The message indicates that the move does not comply with Sudoku rules.
     */
    public InvalidSudokuMoveException() {
        super("Invalid move: The move does not comply with Sudoku rules.");
    }

}
