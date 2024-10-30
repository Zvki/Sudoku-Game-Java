package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.model.GameModel;

/**
 * <p>
 * The GameController class is responsible for managing the main game loop.
 * It checks the game state, displays the board, and handles player turns until the game is completed.
 * </p>
 * @author Bartosz Sośnica
 * @version 1.0
 */
public class GameController {

    /**
     * The model representing the game state.
     */
    GameModel gameModel;

    /**
     * Constructor for GameController.
     * Initializes the game model with command-line arguments.
     *
     * @param args command-line arguments passed to the game.
     */
    public GameController(String[] args) {
        gameModel = new GameModel(args);
    }

    /**
     * The main game loop that continues until the Sudoku puzzle is solved.
     * It clears the screen, displays the current state of the board, and processes the player's turn.
     */
    public void gameLoop() {

        // Continue looping until the Sudoku game is completed
//        if(gameModel.getUserController().getUserInputGuiView().LoginGUI(gameModel.getUserController().getBoardController(), gameModel.getUserController())){
//            while (!gameModel.getUserController().getBoardController().isGameCompleted()) {
//
//                // Clear the screen before displaying the updated board
//                gameModel.getUserController().getUserView().clearScreen();
//
//                // Display the current state of the board
//                gameModel.getUserController().getUserView().boardDisplay(gameModel.getUserController().getBoardController().getBoardModel());
//
//                // Process the player's turn
//                playTurn();
//            }
//        }

        gameModel.getUserController().getUserInputGuiView().LoginGUI(gameModel.getUserController().getBoardController(), gameModel.getUserController());

    }

    /**
     * Facilitates the player's turn by retrieving the move and processing it.
     * It retrieves the row, column, and value entered by the player and passes them to the UserController for validation and placement.
     */
    public void playTurn() {
        // Get the user's move
        int[] move = gameModel.getUserController().getUserMove();
        int row = move[0];
        int column = move[1];
        int value = move[2];

        // Process the user's input
        gameModel.getUserController().processUserInput(row, column, value);
    }
}
