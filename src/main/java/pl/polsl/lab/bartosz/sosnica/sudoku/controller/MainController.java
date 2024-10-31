package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidUserInputException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.UserModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.SudokuGameView;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserInputGuiView;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

import javax.swing.*;
import java.util.Scanner;

/**
 * <p>
 * The UserController class is responsible for managing user interactions and game flow.
 * It handles setting up the user, processing their input, and managing game turns.
 * </p>
 *
 * @author Bartosz Sośnica
 * @version 2.0
 */
public class MainController {

    /**
     * The model representing the player.
     */
    private UserModel userModel;

    /**
     * The controller responsible for managing the game board.
     */
    private BoardController boardController;

    /**
     * The view responsible for interacting with the user.
     */
    private UserView userView;

    private UserInputGuiView userInputGuiView;

    private SudokuGameView sudokuGameView;

    /**
     * Returns the instance of the BoardController associated with the current UserController.
     *
     * @return The BoardController instance that manages the game board.
     */
    public BoardController getBoardController() {
        return boardController;
    }

    /**
     * Returns the instance of the UserView associated with the current UserController.
     * This view is responsible for interacting with the user.
     *
     * @return The UserView instance used for user interactions.
     */
    public UserView getUserView() {
        return userView;
    }

    /**
     * Default constructor for UserController.
     * Initializes the UserView and BoardController.
     */
    public MainController() {
        this.userView = new UserView();
        this.boardController = new BoardController();
        this.userModel = new UserModel();
    }

    public UserModel getUserModel(){
        return userModel;
    }

    public UserInputGuiView getUserInputGuiView(){
        return userInputGuiView;
    }

    public SudokuGameView getSudokuGameView() {
        return sudokuGameView;
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

    public void GameSetUp(String[] args){
        try{
            userModel.checkUsernameInput(args);
            userModel.setUsername(args[0]);
            boardController.getBoardModel().settingDifficultyLevel(this.boardController.getDifficultyLevelInput(args));
        } catch (InvalidUserInputException e) {
            System.out.println(e.getMessage());
            userInputGuiView = new UserInputGuiView();
            handleLogin(userInputGuiView);
        }
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
        move[0] = getUserMoveInput(inputUser);

        // Get column number
        userView.displayMessage("Enter column number (1-9): ");
        move[1] = getUserMoveInput(inputUser);

        // Get value
        userView.displayMessage("Enter value (1-9): ");
        move[2] = getUserMoveInput(inputUser);

        return move;
    }

    public void handleLogin(UserInputGuiView userInputGuiView){
        userInputGuiView.addSubmitButtonListener(e -> {
            String username = userInputGuiView.getUsername();
            String difficulty = userInputGuiView.getDifficulty();

            userModel.setUsername(username);
            boardController.getBoardModel().settingDifficultyLevel(difficulty);

            JOptionPane.showMessageDialog(null, "Welcome, " + username + "! Difficulty Level: " + difficulty);

            // Zamknięcie okna po rozpoczęciu gry
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(userInputGuiView.getSubmitButton());
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    /**
     * Continuously prompts the user for input until a valid integer is provided.
     * It uses the isInputNumber method to validate the input and returns the integer once valid input is received.
     * If the input is not a valid number, it catches the InvalidUserInputException and displays an error message,
     * prompting the user to try again.
     *
     * @param inputUser A Scanner object used to read the user's input from the console.
     * @return The valid integer input provided by the user.
     */
    private int getUserMoveInput(Scanner inputUser) {

        while (true) {
            try {
                return userModel.isInputNumber(inputUser.nextLine());
            } catch (InvalidUserInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
