package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidUserInputException;

/**

 * <p>
 * UserModel class represents the player in the Sudoku game.
 * It stores the player's username and score and provides methods
 * for setting and retrieving these values.
 * </p>
 * @author Bartosz Sośnica
 * @version 2.0
 */
@Setter
@Getter
public class UserModel {

    /**
     * The player's username.
     * -- GETTER --
     *  Returns the player's username.
     *
     *
     * -- SETTER --
     *  Sets the player's username.
     *
     @return The username of the player.
      * @param username The new username for the player.
      *                 It should not be null or empty.

     */
    private String username;

    /**
     * Validates the input for the username. Ensures that the user provides
     * at least one input argument for the username.
     *
     * @param username The array containing the user's input.
     * @throws InvalidUserInputException if the username input is invalid.
     */
    public void checkUsernameInput(String[] username) throws InvalidUserInputException {
        if (username == null || username.length < 1 || username[0].isEmpty()) {
            throw new InvalidUserInputException("Username cannot be empty. Please enter your username: ");
        }
    }

    /**
     * Validates if the provided data can be parsed as an integer.
     * If the input is not a valid number, it throws an InvalidUserInputException.
     *
     * @param providedData The input data provided by the user as a String.
     * @return The parsed integer value of the provided data.
     * @throws InvalidUserInputException if the provided data cannot be parsed as a valid integer.
     */
    public int isInputNumber(String providedData) throws InvalidUserInputException {
        try {
            return Integer.parseInt(providedData);
        } catch (NumberFormatException e) {
            throw new InvalidUserInputException("Provided data has to be a number");
        }
    }
}
