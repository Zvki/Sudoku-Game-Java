package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidUserInputException;

/**
 * @author Bartosz Sośnica
 * @version 1.0
 * UserModel class represents the player in the Sudoku game.
 * It stores the player's username and score and provides methods
 * for setting and retrieving these values.
 */
public class UserModel {

    /**
     * The player's username.
     */
    private String username;

    /**
     * The player's score.
     */
    private int score;

    /**
     * Sets the player's username.
     *
     * @param username The new username for the player.
     *                 It should not be null or empty.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the player's username.
     *
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the player's score.
     *
     * @param score The new score for the player.
     *              It should be a non-negative integer.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the player's score.
     *
     * @return The current score of the player.
     */
    public int getScore() {
        return score;
    }

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
}
