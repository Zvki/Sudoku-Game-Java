package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.MethodSource;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidUserInputException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    private final UserModel userModel = new UserModel();

    @ParameterizedTest
    @MethodSource("provideValidUsernameInputs")
    void checkUsernameInput_ValidInput_DoesNotThrow(String[] validInput) {
        // GIVEN: A valid username input
        // THEN: No exception is thrown for valid input
        assertDoesNotThrow(() -> userModel.checkUsernameInput(validInput),
                "checkUsernameInput should not throw an exception for valid input");
    }

    static Stream<Arguments> provideValidUsernameInputs() {
        return Stream.of(
                Arguments.of((Object) new String[]{"Bartosz"}),
                Arguments.of((Object) new String[]{"Anna"})
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUsernameInputs")
    void checkUsernameInput_InvalidInput_ThrowsException(String[] invalidInput) {
        // GIVEN: An invalid username input
        // THEN: An exception is thrown
        assertThrows(InvalidUserInputException.class, () -> userModel.checkUsernameInput(invalidInput),
                "checkUsernameInput should throw InvalidUserInputException for invalid input");
    }

    static Stream<Arguments> provideInvalidUsernameInputs() {
        return Stream.of(
                Arguments.of((Object) null),          // Null input
                Arguments.of((Object) new String[]{}), // Empty array
                Arguments.of((Object) new String[]{""}) // Empty string
        );
    }

    @ParameterizedTest
    @CsvSource({
            "123, 123",
            "456, 456"
    })
    void isInputNumber_ValidNumber_ReturnsParsedValue(String input, int expected) {
        // GIVEN: A valid numeric string
        // WHEN: Parsing the number
        int result = assertDoesNotThrow(() -> userModel.isInputNumber(input),
                "isInputNumber should not throw an exception for valid numeric input");

        // THEN: The parsed value matches the expected number
        assertEquals(expected, result, "isInputNumber should return the parsed integer value for valid input");
    }

    @ParameterizedTest
    @CsvSource({
            "abc",  // Non-numeric input
            "'\"\"'" // Empty string (escaped with single quotes)
    })
    void isInputNumber_InvalidInput_ThrowsException(String input) {
        // GIVEN: An invalid numeric input
        // THEN: An exception is thrown
        assertThrows(InvalidUserInputException.class, () -> userModel.isInputNumber(input),
                "isInputNumber should throw InvalidUserInputException for invalid input");
    }

    @ParameterizedTest
    @NullSource
    void isInputNumber_NullInput_ThrowsException(String input) {
        // GIVEN: A null input
        // THEN: An exception is thrown
        assertThrows(InvalidUserInputException.class, () -> userModel.isInputNumber(input),
                "isInputNumber should throw InvalidUserInputException for null input");
    }
}
