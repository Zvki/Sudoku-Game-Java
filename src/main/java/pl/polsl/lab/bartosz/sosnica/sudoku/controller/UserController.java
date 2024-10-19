package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidUserInputException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.UserModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

import java.util.Scanner;

/**
 * @author Bartosz Sośnica
 * @version 1.0
 * The UserController class is responsible for managing user interactions and game flow.
 * It handles setting up the user, processing their input, and managing game turns.
 */
public class UserController {

    /**
     * The model representing the player.
     */
    public UserModel userModel;

    /**
     * The view responsible for interacting with the user.
     */
    public UserView userView;

    /**
     * The controller responsible for managing the game board.
     */
    public BoardController boardController;

    /**
     * Default constructor for UserController.
     * Initializes the UserView and BoardController.
     */
    public UserController() {
        this.userView = new UserView();
        this.boardController = new BoardController();
    }

    /**
     * Sets up the user by retrieving the username and difficulty level.
     *
     * @param args command-line arguments containing the username and difficulty level.
     */
    public void settingUpUser(String[] args) {
        // Retrieve and set the username
        this.userModel = new UserModel();
        this.userModel.setUsername(getUsernameInput(args));

        // Welcome the player
        userView.displayMessage("Welcome, " + this.userModel.getUsername() + "!");

        // Set the difficulty level for the game
        this.boardController.getBoardModel().settingDifficultyLevel(this.boardController.getDifficultyLevelInput(args));
    }

    /**
     * Retrieves the username from command-line arguments or prompts the user if it is not provided.
     *
     * @param args command-line arguments containing the username.
     * @return the username.
     */
    public String getUsernameInput(String[] args) {
        try {
            // Check if the username is provided
            userModel.checkUsernameInput(args);
            return args[0];
        } catch (InvalidUserInputException e) {
            // Handle missing or invalid username input
            System.out.println(e.getMessage());
            Scanner inputUser = new Scanner(System.in);
            return inputUser.nextLine(); // Prompt the user for the correct username
        }
    }

    /**
     * Processes the user's move input by validating and placing it on the board.
     * Handles exceptions if the move is invalid.
     *
     * @param row    the row number of the move (1-based index).
     * @param column the column number of the move (1-based index).
     * @param value  the value to be placed at the specified row and column.
     */
    public void processUserInput(int row, int column, int value) {
        // Convert user input from 1-based index to 0-based index for internal use
        int realRow = row - 1;
        int realColumn = column - 1;

        // Validate and place the move if valid
        try {
            boardController.isMoveValid(realRow, realColumn, value);
            boardController.getBoardModel().placeValue(realRow, realColumn, value);
            userView.displayMessage("Move placed successfully!");
        } catch (InvalidSudokuMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves the player's move input from the console.
     * Prompts the user for row, column, and value to place.
     *
     * @return an array containing the row, column, and value for the move.
     */
    public int[] getUserMove() {
        Scanner inputUser = new Scanner(System.in);
        int[] move = new int[3];

        // Get row number
        userView.displayMessage("Enter row number (1-9): ");
        move[0] = Integer.parseInt(inputUser.nextLine());

        // Get column number
        userView.displayMessage("Enter column number (1-9): ");
        move[1] = Integer.parseInt(inputUser.nextLine());

        // Get value
        userView.displayMessage("Enter value (1-9): ");
        move[2] = Integer.parseInt(inputUser.nextLine());

        return move;
    }
}
