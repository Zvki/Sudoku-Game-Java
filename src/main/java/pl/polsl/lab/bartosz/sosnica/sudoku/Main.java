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
     * @param args command-line arguments provided to the application. The expected order is:
     *             <ul>
     *             <li>args[0] - the username (String)</li>
     *             <li>args[1] - the difficulty level (String: "Easy", "Medium", "Hard")</li>
     *             </ul>
     */
    public static void main(String[] args) {
        gameController = new GameController(args);
    }

}
