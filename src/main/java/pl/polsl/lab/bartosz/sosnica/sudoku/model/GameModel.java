package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.MainController;

/**
 * <p>
 * GameModel class represents the data and logic for the overall game state.
 * It initializes the main and board controllers and sets up the game for the user.
 * </p>
 * @author Bartosz Sośnica
 * @version 2.0
 */

public class GameModel {

    /**
     * The controller that manages user input and interactions.
     */
    private MainController mainController;


    /**
     * Constructor for GameModel.
     * Initializes the user by calling the MainController and sets up the Sudoku board.
     *
     * @param args Command line arguments passed during game setup (e.g., username, difficulty).
     */
    public GameModel(String[] args) {
        mainController = new MainController();
    }

    /**
     * Returns the MainController instance managing the user and their interactions.
     *
     * @return The MainController instance.
     */
    public MainController getUserController() {
        return mainController;
    }

}

