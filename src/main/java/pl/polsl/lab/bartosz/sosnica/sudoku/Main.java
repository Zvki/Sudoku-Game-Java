package pl.polsl.lab.bartosz.sosnica.sudoku;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.GameController;

/**
 * <p>
 * The Main class serves as the entry point for the Sudoku application.
 * It initializes the game and starts the main game loop.
 * </p>
 * @author Bartosz Sośnica
 * @version 1.0
 */
public class Main {

    /**
     * The controller responsible for managing the game's flow.
     */
    static GameController gameController;

    /**
     * The main method that serves as the starting point of the application.
     * It creates a new GameController and starts the game loop.
     *
     * @param args command-line arguments provided to the application (username and difficulty level).
     */
    public static void main(String[] args) {

        // Initialize the GameController with the provided command-line arguments
        gameController = new GameController(args);

        // Start the main game loop
        gameController.gameLoop();
    }

}
