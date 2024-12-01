package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BoardModelTest {

    private BoardModel boardModel;

    @BeforeEach
    void setUp() {
        boardModel = new BoardModel();
        boardModel.settingUpBoard();
    }

    @Test
    void shouldPlaceValueInCorrectCell() {
        // GIVEN: A board and a request to place a value at (0, 0)
        boardModel.placeValue(0, 0, "5");

        // WHEN: Retrieving the value at (0, 0)
        String resultValue = boardModel.getBoard().get(0).get(0).value();

        // THEN: The value should match the placed value
        assertEquals("5", resultValue);
    }

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

    @ParameterizedTest
    @MethodSource("provideInvalidMoves")
    void shouldThrowExceptionWhenValueIsDuplicate(int row, int col, String value, int newRow, int newCol) {
        // GIVEN: A board with a value placed in a cell
        boardModel.placeValue(row, col, value);

        // THEN: Placing the same value in the same row, column, or block should throw an exception
        assertThrows(InvalidSudokuMoveException.class,
                () -> boardModel.validateValueUniqueness(newRow, newCol, value));
    }

    static Stream<Arguments> provideInvalidMoves() {
        return Stream.of(
                Arguments.of(0, 0, "5", 1, 0), // Same column
                Arguments.of(0, 0, "5", 0, 1)  // Same row
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidValues")
    void shouldValidateMultipleSingleDigitValues(String[] values) {
        // GIVEN: A set of single-digit values
        // THEN: Validation should pass without throwing an exception
        assertDoesNotThrow(() -> boardModel.checkMultipleValues(values));
    }

    static Stream<Arguments> provideValidValues() {
        return Stream.of(
                Arguments.of((Object) new String[]{"1", "2", "3"}),
                Arguments.of((Object) new String[]{"4", "5", "6"})
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidValues")
    void shouldThrowExceptionForInvalidMultipleValues(String[] values) {
        // GIVEN: A set of invalid values
        // THEN: Validation should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> boardModel.checkMultipleValues(values));
    }

    static Stream<Arguments> provideInvalidValues() {
        return Stream.of(
                Arguments.of((Object) new String[]{"1", "10", "3"}), // Two-digit value
                Arguments.of((Object) new String[]{"1", "a", "3"})   // Non-numeric value
        );
    }

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
