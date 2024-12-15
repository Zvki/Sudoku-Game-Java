package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Test class for {@link BoardModel} to verify its functionality.</p>
 *
 * @author Bartosz Sośnica
 * @version 1.0
 */
class BoardModelTest {

    private BoardModel boardModel;

    /**
     * Sets up a new instance of {@link BoardModel} and initializes the board before each test.
     */
    @BeforeEach
    void setUp() {
        boardModel = new BoardModel();
        boardModel.settingUpBoard();
    }

    /**
     * Tests whether a value can be correctly placed in a specified cell on the board.
     */
    @Test
    void shouldPlaceValueInCorrectCell() {
        // GIVEN: A board and a request to place a value at (0, 0)
        boardModel.placeValue(0, 0, "5");

        // WHEN: Retrieving the value at (0, 0)
        String resultValue = boardModel.getBoard().get(0).get(0).value();

        // THEN: The value should match the placed value
        assertEquals("5", resultValue);
    }

    /**
     * Tests if the difficulty level and corresponding number of cells to remove are set correctly.
     *
     * @param difficulty    the difficulty level to be set
     * @param expectedDiff  the expected number of cells to remove
     */
    @ParameterizedTest
    @CsvSource({
            "Easy, 3",
            "Medium, 25",
            "Hard, 35"
    })
    void shouldSetDifficultyLevel(BoardModel.DifficultyLevel difficulty, int expectedDiff) {
        // GIVEN: A specific difficulty level
        boardModel.settingDifficultyLevel(difficulty);

        // THEN: The difficulty and number of cells to remove match expectations
        assertEquals(difficulty, boardModel.getDifficultyLevel());
        assertEquals(expectedDiff, boardModel.getNumberDiff());
    }

    /**
     * Tests if placing a duplicate value in the same row, column, or block throws an exception.
     *
     * @param row       the row of the first value
     * @param col       the column of the first value
     * @param value     the value to be placed
     * @param newRow    the row of the duplicate value
     * @param newCol    the column of the duplicate value
     */
    @ParameterizedTest
    @MethodSource("provideInvalidMoves")
    void shouldThrowExceptionWhenValueIsDuplicate(int row, int col, String value, int newRow, int newCol) {
        // GIVEN: A board with a value placed in a cell
        boardModel.placeValue(row, col, value);

        // THEN: Placing the same value in the same row, column, or block should throw an exception
        assertThrows(InvalidSudokuMoveException.class,
                () -> boardModel.validateValueUniqueness(newRow, newCol, value));
    }

    /**
     * Provides test data for invalid moves where duplicate values are placed in the same row, column, or block.
     *
     * @return a stream of arguments representing invalid moves
     */
    static Stream<Arguments> provideInvalidMoves() {
        return Stream.of(
                Arguments.of(0, 0, "5", 1, 0), // Same column
                Arguments.of(0, 0, "5", 0, 1)  // Same row
        );
    }

    /**
     * Tests if multiple valid single-digit values pass validation without throwing an exception.
     *
     * @param values an array of single-digit values to validate
     */
    @ParameterizedTest
    @MethodSource("provideValidValues")
    void shouldValidateMultipleSingleDigitValues(String[] values) {
        // GIVEN: A set of single-digit values
        // THEN: Validation should pass without throwing an exception
        assertDoesNotThrow(() -> boardModel.checkMultipleValues(values));
    }

    /**
     * Provides test data for valid single-digit values.
     *
     * @return a stream of arguments representing valid values
     */
    static Stream<Arguments> provideValidValues() {
        return Stream.of(
                Arguments.of((Object) new String[]{"1", "2", "3"}),
                Arguments.of((Object) new String[]{"4", "5", "6"})
        );
    }

    /**
     * Tests if invalid values (non-numeric or multi-digit) throw an exception during validation.
     *
     * @param values an array of invalid values to validate
     */
    @ParameterizedTest
    @MethodSource("provideInvalidValues")
    void shouldThrowExceptionForInvalidMultipleValues(String[] values) {
        // GIVEN: A set of invalid values
        // THEN: Validation should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> boardModel.checkMultipleValues(values));
    }

    /**
     * Provides test data for invalid values (non-numeric or multi-digit).
     *
     * @return a stream of arguments representing invalid values
     */
    static Stream<Arguments> provideInvalidValues() {
        return Stream.of(
                Arguments.of((Object) new String[]{"1", "10", "3"}), // Two-digit value
                Arguments.of((Object) new String[]{"1", "a", "3"})   // Non-numeric value
        );
    }

    /**
     * Tests whether a value can be correctly removed from a specified cell on the board.
     */
    @Test
    void shouldRemoveValueFromCell() {
        // GIVEN: A value placed in a cell
        boardModel.placeValue(0, 0, "5");

        // WHEN: The value is removed
        boardModel.removeValue(0, 0);

        // THEN: The cell should be empty
        String resultValue = boardModel.getBoard().get(0).get(0).value();
        assertEquals("", resultValue);
    }
}
