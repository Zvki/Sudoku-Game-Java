package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The BoardModel class represents the model of the Sudoku board.
 * It handles the board setup, placement of values, validation of moves,
 * and difficulty level management. It also provides methods to manipulate
 * the board state and check for validity of player moves.
 *
 * @author Bartosz Sośnica
 * @version 3.0
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardModel {

    /**
     * Enum representing difficulty levels.
     * The levels include Easy, Medium, and Hard, each associated with
     * the number of cells to remove when setting up the board.
     */
    public enum DifficultyLevel {
        Easy(3), Medium(25), Hard(35);

        private final int cellsToRemove;

        /**
         * Constructor to initialize the difficulty level with the number of cells to remove.
         *
         * @param cellsToRemove the number of cells to remove for this difficulty level.
         */
        DifficultyLevel(int cellsToRemove) {
            this.cellsToRemove = cellsToRemove;
        }

        /**
         * Gets the number of cells to remove for this difficulty level.
         *
         * @return the number of cells to remove.
         */
        public int getCellsToRemove() {
            return cellsToRemove;
        }
    }

    /**
     * Record representing a cell in the Sudoku board.
     * A cell is identified by its value.
     */
    public record BoardCell(String value) {}

    private List<List<BoardCell>> board;
    private DifficultyLevel difficultyLevel;
    private String status;
    private int numberDiff;

    /**
     * Initializes the board with empty cells represented by an empty string.
     * The board is a 9x9 grid, with each cell initially empty.
     */
    public void settingUpBoard() {
        board = new ArrayList<>(9);
        status = "Unsolved";

        for (int i = 0; i < 9; i++) {
            List<BoardCell> row = Collections.nCopies(9, new BoardCell(""));
            board.add(new ArrayList<>(row));
        }
    }

    /**
     * Changes the status of the board to "Solved".
     * This method is called when the player successfully completes the Sudoku puzzle.
     */
    public void changeStatus() {
        status = "Solved";
    }

    /**
     * Places a value at the specified row and column on the board.
     * If the provided value is null, an empty string is placed instead.
     *
     * @param row   the row index (0-based).
     * @param col   the column index (0-based).
     * @param value the value to place in the specified cell.
     */
    public void placeValue(int row, int col, String value) {
        board.get(row).set(col, new BoardCell(Optional.ofNullable(value).orElse("")));
    }

    /**
     * Validates if the specified value is unique across the specified row and column.
     * If the value is already present in the row or column, an exception is thrown.
     *
     * @param row   the row index (0-based).
     * @param col   the column index (0-based).
     * @param value the value to validate.
     * @throws InvalidSudokuMoveException if the value already exists in the row or column.
     */
    public void validateValueUniqueness(int row, int col, String value) throws InvalidSudokuMoveException {
        if (board.get(row).stream().anyMatch(cell -> cell.value().equals(value))) {
            throw new InvalidSudokuMoveException("Value already present in row");
        }

        if (board.stream().anyMatch(r -> r.get(col).value().equals(value))) {
            throw new InvalidSudokuMoveException("Value already present in column");
        }
    }

    /**
     * Generic method to find duplicates in a collection.
     * This method is currently unused but could be extended for other validation purposes.
     *
     * @param collection the collection to check for duplicates.
     * @param value      the value to find duplicates of.
     * @param <T>        the type of elements in the collection.
     * @return true if duplicates exist, false otherwise.
     */
    private <T> boolean findDuplicates(List<T> collection, T value) {
        return collection.stream().filter(value::equals).count() > 1;
    }

    /**
     * Sets the difficulty level and calculates the number of cells to remove based on the level.
     *
     * @param level the difficulty level (Easy, Medium, Hard).
     */
    public void settingDifficultyLevel(DifficultyLevel level) {
        this.difficultyLevel = level;
        this.numberDiff = level.getCellsToRemove();
    }

    /**
     * Validates multiple values using a variable number of parameters.
     * Each value must be a single digit (1-9), otherwise an exception is thrown.
     *
     * @param values the values to validate.
     * @throws IllegalArgumentException if any value is invalid (not a single digit).
     */
    public void checkMultipleValues(String... values) {
        Arrays.stream(values).forEach(value -> {
            if (value.length() > 1 || !value.matches("\\d")) {
                throw new IllegalArgumentException("Invalid Sudoku value: " + value);
            }
        });
    }

    /**
     * Removes a value from the specified row and column on the board, setting it to an empty string.
     * This method is used to reset a cell to its initial state.
     *
     * @param row the row index (0-based).
     * @param col the column index (0-based).
     */
    public void removeValue(int row, int col) {
        board.get(row).set(col, new BoardCell(""));
    }
}
