package pl.polsl.lab.bartosz.sosnica.sudoku.exception;

import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;

public class InvalidSudokuMoveException extends Exception{


    public InvalidSudokuMoveException(String message){
        super(message);
    }

    public InvalidSudokuMoveException() {
        super("Invalid move: The move does not comply with Sudoku rules.");
    }

    public static void checkCoordinates(int row, int col, String[][] board) throws InvalidSudokuMoveException {
        if (row > board.length - 1 || row < 0 || col > board.length - 1 || col < 0) {
            throw new InvalidSudokuMoveException("Coordinates out of bounds: row=" + row + ", col=" + col);
        }
    }

    public static void checkValue(int value) throws InvalidSudokuMoveException {
        if (value < 0 || value > 10) {
            throw new InvalidSudokuMoveException("Value out of bounds");
        }
    }

}
