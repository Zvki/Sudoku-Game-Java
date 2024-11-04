package pl.polsl.lab.bartosz.sosnica.sudoku.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;

import java.util.*;

/**
 * <p>
 * The BoardModel class represents the model of the Sudoku board.
 * It handles board setup, placement of values, validation of moves, and difficulty level management.
 * </p>
 * @author Bartosz Sośnica
 * @version 2.0
 */


@Getter
@Setter
@NoArgsConstructor
public class BoardModel {

    /**
     * A 2D array representing the Sudoku board, where each cell contains a string representing a value.
     * -- GETTER --
     *  Returns the current state of the board.
     *
     * @return the 2D array representing the board.

     */
    //private String[][] board;

    private List<List<BoardCell>> board;

    /**
     * The difficulty level of the Sudoku game, represented by the number of cells to remove.
     * -- GETTER --
     *  Returns the current difficulty level of the Sudoku game.
     *
     * @return the difficulty level as a string.

     */
    private String difficultyLevel;

    /**
     * The current status of the game (e.g., "Solved", "Unsolved").
     * -- GETTER --
     *  Returns the current status of the board.
     *
     * @return the status of the board.

     */
    private String status;

    /**
     * The number of cells to remove based on the difficulty level.
     * -- GETTER --
     *  Returns the number of cells to remove based on the difficulty level.
     *
     * @return the number of cells to remove.

     */
    private int numberDiff;

    public record BoardCell(String value){};

    /**
     * Initializes the board with empty cells represented by an empty string.
     */
    public void settingUpBoard() {
        board = new ArrayList<>(9);
        status = "Unsolved";

        for (int i = 0; i < 9; i++) {
            List<BoardCell> row = new ArrayList<>(Collections.nCopies(9, new BoardCell("")));
            board.add(row);
        }
    }

    /**
     * Changes the status of the board to "Solved".
     */
    public void changeStatus() {
        status = "Solved";
    }

    /**
     * Places a value at the specified row and column on the board.
     *
     * @param row   the row index where the value should be placed.
     * @param col   the column index where the value should be placed.
     * @param value the value to place on the board.
     */
    public void placeValue(int row, int col, String value) {
        board.get(row).set(col, new BoardCell(value != null ? value : ""));
    }

    /**
     * Removes a value from the specified row and column on the board, setting it to an empty string.
     *
     * @param row the row index.
     * @param col the column index.
     */
    public void removeValue(int row, int col) {
        board.get(row).set(col, new BoardCell(""));
    }

    /**
     * Checks if the specified value to be placed is valid based on Sudoku rules.
     *
     * @param row   the row index of the move.
     * @param col   the column index of the move.
     * @param value the value to place.
     * @throws InvalidSudokuMoveException if the move is invalid (e.g., coordinates or value out of bounds).
     */
    public void isValidNumber(int row, int col, String value) throws InvalidSudokuMoveException {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidSudokuMoveException("Provided value must be a number");
        }

        if (row < 0 || row >= board.size() || col < 0 || col >= board.size()) {
            throw new InvalidSudokuMoveException("Coordinates out of bounds: row=" + row + ", col=" + col);
        }

        int intValue = Integer.parseInt(value);
        if (intValue < 1 || intValue > 9) {
            throw new InvalidSudokuMoveException("Value out of bounds. Must be between 1 and 9.");
        }
    }

    /**
     * Validates if the specified value can be placed at the given row and column,
     * ensuring that the value does not already exist in the same row or column.
     *
     * @param row   the row index.
     * @param col   the column index.
     * @param value the value to place.
     * @throws InvalidSudokuMoveException if the value is already present in the row or column.
     */
    public void isValidPosition(int row, int col, String value) throws InvalidSudokuMoveException {
        for (int i = 0; i < board.size(); i++) {
            String cellValue = board.get(row).get(i).value();
            if (!cellValue.isEmpty() && Objects.equals(cellValue, value)) {
                throw new InvalidSudokuMoveException("Value already placed in row");
            }
        }

        for (List<BoardCell> boardCells : board) {
            String cellValue = boardCells.get(col).value();
            if (!cellValue.isEmpty() && Objects.equals(cellValue, value)) {
                throw new InvalidSudokuMoveException("Value already placed in column");
            }
        }
    }

    /**
     * Validates if the specified value already exists in the 3x3 subgrid
     * containing the given row and column.
     *
     * @param row   the row index.
     * @param col   the column index.
     * @param value the value to check.
     * @throws InvalidSudokuMoveException if the value is already present in the 3x3 subgrid.
     */
    public void isValueIn3x3(int row, int col, String value) throws InvalidSudokuMoveException {
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        // Check the 3x3 subgrid for the value
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (Objects.equals(board.get(i).get(j).value(), value)) {
                    throw new InvalidSudokuMoveException("Value already placed in 3x3 subgrid");
                }
            }
        }
    }

    /**
     * Checks if the input for the difficulty level is valid.
     *
     * @param diffLevel the user's input for difficulty level as a string array.
     * @throws InvalidSudokuMoveException if the difficulty input is invalid (e.g., too few arguments).
     */
    public void checkDifficultyInput(String[] diffLevel) throws InvalidSudokuMoveException {
        if (diffLevel.length < 2) {
            throw new InvalidSudokuMoveException("Enter your difficulty level: ");
        }
    }

    /**
     * Validates if the input string for the difficulty level can be parsed as a number.
     *
     * @param diffLevel the difficulty level as a string.
     * @throws InvalidSudokuMoveException if the difficulty level is not a number.
     */
    public void isDiffLvlNumber(String diffLevel) throws InvalidSudokuMoveException {
        try {
            Integer.parseInt(diffLevel);
        } catch (NumberFormatException e) {
            throw new InvalidSudokuMoveException("Difficulty level must be a number!");
        }
    }

    /**
     * Validates if the parsed difficulty level is within the allowed range (1-40).
     *
     * @param diffLevel the difficulty level as a string.
     * @throws InvalidSudokuMoveException if the difficulty level is outside the valid range.
     */
    public void isDiffLvlCorrect(String diffLevel) throws InvalidSudokuMoveException {
        int level = Integer.parseInt(diffLevel);
        if (level < 1 || level > 40) {
            throw new InvalidSudokuMoveException("Difficulty level must be between 1 and 40");
        }
    }

    /**
     * Sets the difficulty level of the Sudoku game based on the provided string.
     *
     * @param diffLevel the difficulty level as a string, which is parsed into an integer.
     */
    public void settingDifficultyLevel(String diffLevel) {
        this.difficultyLevel = diffLevel;
        convertDifficultyLevel();
    }

    /**
     * Converts the difficulty level string to an integer representing the number of cells to remove.
     */
    private void convertDifficultyLevel() {
        switch (this.difficultyLevel) {
            case "Easy":
                this.numberDiff = 3;
                break;
            case "Medium":
                this.numberDiff = 25;
                break;
            case "Hard":
                this.numberDiff = 35;
                break;
            default:
                this.numberDiff = 15; // Default value if an unrecognized level is provided
                break;
        }
    }

}
