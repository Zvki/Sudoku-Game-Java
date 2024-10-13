package pl.polsl.lab.bartosz.sosnica.sudoku.exception;

public class InvalidSudokuMoveException extends Exception{


    public InvalidSudokuMoveException(String message){
        super(message);
    }

    public InvalidSudokuMoveException() {
        super("Invalid move: The move does not comply with Sudoku rules.");
    }

}
