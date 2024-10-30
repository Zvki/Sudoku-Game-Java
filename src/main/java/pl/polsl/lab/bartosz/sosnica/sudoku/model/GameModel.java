package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.UserController;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.SudokuGameView;

/**
 * <p>
 * GameModel class represents the data and logic for the overall game state.
 * It initializes the user and board controllers and sets up the game for the user.
 * </p>
 * @author Bartosz Sośnica
 * @version 1.0
 */
public class GameModel {

    /**
     * The controller that manages user input and interactions.
     */
    private UserController userController;


    /**
     * Constructor for GameModel.
     * Initializes the user by calling the UserController and sets up the Sudoku board.
     *
     * @param args Command line arguments passed during game setup (e.g., username, difficulty).
     */
    public GameModel(String[] args) {
        userController = new UserController();
    }

    /**
     * Returns the UserController instance managing the user and their interactions.
     *
     * @return The UserController instance.
     */
    public UserController getUserController() {
        return userController;
    }
}

