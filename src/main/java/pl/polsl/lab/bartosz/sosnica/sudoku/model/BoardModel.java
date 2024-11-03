package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;

import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * The BoardModel class represents the model of the Sudoku board.
 * It handles board setup, placement of values, validation of moves, and difficulty level management.
 * </p>
 */
public class BoardModel {

    /**
     * A 2D array representing the Sudoku board, where each cell contains a string representing a value.
     */
    private String[][] board;

    /**
     * The difficulty level of the Sudoku game, represented by the number of cells to remove.
     */
    private String difficultyLevel;

    private int numberDiff;

    /**
     * Constructor for BoardModel.
     * Initializes the board by calling the settingUpBoard method.
     */
    public BoardModel() {
        settingUpBoard();
    }

    /**
     * Returns the current state of the board.
     *
     * @return the 2D array representing the board.
     */
    public String[][] getBoard() {
        return board;
    }

    public int getNumberDiff() {
        return numberDiff;
    }

    /**
     * Initializes the board with empty cells represented by an empty string.
     */
    public void settingUpBoard() {
        board = new String[9][9];

        for (String[] row : board) {
            Arrays.fill(row, ""); // Set each cell to empty string
        }
    }

    /**
     * Places a value at the specified row and column on the board.
     *
     * @param row   the row index where the value should be placed.
     * @param col   the column index where the value should be placed.
     * @param value the value to place on the board.
     */
    public void placeValue(int row, int col, String value) {
        board[row][col] = value != null ? value : ""; // Place the value or empty string
    }

    /**
     * Removes a value from the specified row and column on the board, setting it to an empty string.
     *
     * @param row the row index.
     * @param col the column index.
     */
    public void removeValue(int row, int col) {
        board[row][col] = ""; // Set to empty string when removing a value
    }

    /**
     * Checks if the specified value to be placed is valid based on the Sudoku rules.
     *
     * @param row   the row index of the move.
     * @param col   the column index of the move.
     * @param value the value to place.
     * @throws InvalidSudokuMoveException if the move is invalid (e.g., coordinates or value out of bounds).
     */
    public void isValidNumber(int row, int col, String value) throws InvalidSudokuMoveException {

        try{
            Integer.parseInt(value);
        }catch (NumberFormatException e){
            throw new InvalidSudokuMoveException("Provided value must be a number");
        }

        if (row > board.length - 1 || row < 0 || col > board.length - 1 || col < 0) {
            throw new InvalidSudokuMoveException("Coordinates out of bounds: row=" + row + ", col=" + col);
        }

        if (Integer.parseInt(value) < 1 || Integer.parseInt(value) > 9) {
            throw new InvalidSudokuMoveException("Value out of bounds");
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
        // Sprawdź, czy wartość już istnieje w wierszu
        for (int i = 0; i < getBoard().length; i++) {
            String cellValue = getBoard()[row][i];
            if (!Objects.equals(cellValue, "") && !cellValue.isEmpty() && Objects.equals(cellValue, String.valueOf(value))) {
                throw new InvalidSudokuMoveException("Value already placed in row");
            }
        }

        // Sprawdź, czy wartość już istnieje w kolumnie
        for (int i = 0; i < getBoard().length; i++) {
            String cellValue = getBoard()[i][col];
            if (!Objects.equals(cellValue, "") && !cellValue.isEmpty() && Objects.equals(cellValue, String.valueOf(value))) {
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
                if (Objects.equals(getBoard()[i][j], value)) {
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
     * Returns the current difficulty level of the Sudoku game.
     *
     * @return the difficulty level as an integer.
     */
    public String getDifficultyLevel() {
        return difficultyLevel;
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

    private void convertDifficultyLevel() {
        switch(this.difficultyLevel){
            case "Easy": {
                this.numberDiff = 10;
                break;
            }
            case "Medium": {
                this.numberDiff = 20;
                break;
            }
            case "Hard": {
                this.numberDiff = 30;
                break;
            }
        }
    }
}
