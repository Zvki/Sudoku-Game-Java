package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.MethodSource;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidUserInputException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Test class for {@link UserModel} to verify its functionality.</p>
 * @author Bartosz Sośnica
 * @version 1.0
 */
class UserModelTest {

    private final UserModel userModel = new UserModel();

    /**
     * Tests if valid username inputs pass validation without throwing an exception.
     *
     * @param validInput an array of valid username strings to test
     */
    @ParameterizedTest
    @MethodSource("provideValidUsernameInputs")
    void checkUsernameInput_ValidInput_DoesNotThrow(String[] validInput) {
        // GIVEN: A valid username input
        // THEN: No exception is thrown for valid input
        assertDoesNotThrow(() -> userModel.checkUsernameInput(validInput),
                "checkUsernameInput should not throw an exception for valid input");
    }

    /**
     * Provides test data for valid username inputs.
     *
     * @return a stream of arguments representing valid username inputs
     */
    static Stream<Arguments> provideValidUsernameInputs() {
        return Stream.of(
                Arguments.of((Object) new String[]{"Bartosz"}),
                Arguments.of((Object) new String[]{"Anna"})
        );
    }

    /**
     * Tests if invalid username inputs throw an exception during validation.
     *
     * @param invalidInput an array of invalid username strings to test
     */
    @ParameterizedTest
    @MethodSource("provideInvalidUsernameInputs")
    void checkUsernameInputInvalidInputThrowsException(String[] invalidInput) {
        // GIVEN: An invalid username input
        // THEN: An exception is thrown
        assertThrows(InvalidUserInputException.class, () -> userModel.checkUsernameInput(invalidInput),
                "checkUsernameInput should throw InvalidUserInputException for invalid input");
    }

    /**
     * Provides test data for invalid username inputs.
     *
     * @return a stream of arguments representing invalid username inputs
     */
    static Stream<Arguments> provideInvalidUsernameInputs() {
        return Stream.of(
                Arguments.of((Object) null),          // Null input
                Arguments.of((Object) new String[]{}), // Empty array
                Arguments.of((Object) new String[]{""}) // Empty string
        );
    }

    /**
     * Tests if valid numeric string inputs are correctly parsed to integers without throwing an exception.
     *
     * @param input    the numeric string to parse
     * @param expected the expected integer value after parsing
     */
    @ParameterizedTest
    @CsvSource({
            "123, 123",
            "456, 456"
    })
    void isInputNumberValidNumberReturnsParsedValue(String input, int expected) {
        // GIVEN: A valid numeric string
        // WHEN: Parsing the number
        int result = assertDoesNotThrow(() -> userModel.isInputNumber(input),
                "isInputNumber should not throw an exception for valid numeric input");

        // THEN: The parsed value matches the expected number
        assertEquals(expected, result, "isInputNumber should return the parsed integer value for valid input");
    }

    /**
     * Tests if invalid numeric string inputs throw an exception during parsing.
     *
     * @param input the invalid numeric string to test
     */
    @ParameterizedTest
    @CsvSource({
            "abc",  // Non-numeric input
            "'\"\"'" // Empty string (escaped with single quotes)
    })
    void isInputNumberInvalidInputThrowsException(String input) {
        // GIVEN: An invalid numeric input
        // THEN: An exception is thrown
        assertThrows(InvalidUserInputException.class, () -> userModel.isInputNumber(input),
                "isInputNumber should throw InvalidUserInputException for invalid input");
    }

    /**
     * Tests if null numeric string inputs throw an exception during parsing.
     *
     * @param input the null input to test
     */
    @ParameterizedTest
    @NullSource
    void isInputNumberNullInputThrowsException(String input) {
        // GIVEN: A null input
        // THEN: An exception is thrown
        assertThrows(InvalidUserInputException.class, () -> userModel.isInputNumber(input),
                "isInputNumber should throw InvalidUserInputException for null input");
    }
}
