package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import lombok.Getter;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidUserInputException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.UserModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserInputGuiView;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

/**
 * <p>
 * The MainController class is responsible for managing user interactions and game flow.
 * It handles setting up the user, processing their input, and managing game turns.
 * </p>
 *
 * <p>
 * This controller interacts with the UserModel, BoardController, and various views to facilitate
 * the Sudoku game experience.
 * </p>
 *
 * @version 3.0
 */
@Getter
public class MainController {

    /**
     * The model representing the player.
     */
    private UserModel userModel;

    /**
     * The controller responsible for managing the game board.
     * -- GETTER --
     *  Returns the instance of the BoardController associated with the current MainController.
     *
     * @return The BoardController instance that manages the game board.

     */
    private BoardController boardController;

    /**
     * The view responsible for interacting with the user through console input.
     * -- GETTER --
     *  Returns the instance of the UserView associated with the current MainController.
     *
     * @return The UserView instance used for user interactions.

     */
    private UserView userView;

    /**
     * The view for user input in a graphical interface.
     */
    private UserInputGuiView userInputGuiView;

    /**
     * Default constructor for MainController.
     * Initializes the BoardController and UserModel.
     */
    public MainController() {
        this.boardController = new BoardController();
        this.userModel = new UserModel();
    }

    /**
     * Sets up the user by retrieving the username and difficulty level from command-line arguments.
     *
     * @param args command-line arguments containing the username and difficulty level.
     */
//    public void settingUpUser(String[] args) {
//
//        this.userModel.setUsername(getUsernameInput(args));
//
//        userView.displayMessage("Welcome, " + this.userModel.getUsername() + "!");
//
//        this.boardController.getBoardModel().settingDifficultyLevel(this.boardController.getDifficultyLevelInput(args));
//    }

    /**
     * Sets up the game by checking the user's input and initializing the game state.
     *
     * @param args command-line arguments containing user information.
     */
    public void GameSetUp(String[] args) {
        try {
            userModel.checkUsernameInput(args);
            userModel.setUsername(args[0]);

            BoardModel.DifficultyLevel difficultyLevel = BoardModel.DifficultyLevel.valueOf(args[1]);
            boardController.getBoardModel().settingDifficultyLevel(difficultyLevel);

            boardController.setSudokuGameView(userModel);
        } catch (InvalidUserInputException e) {
            userInputGuiView = new UserInputGuiView();
            handleLogin();
            loadGameRecordsFromFile();
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
            boardController.isMoveValid(realRow, realColumn, String.valueOf(value));
            boardController.getBoardModel().placeValue(realRow, realColumn, String.valueOf(value));
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

    /**
     * Handles the login process through a GUI, allowing the user to input their username and difficulty level.
     */
    public void handleLogin() {
        userInputGuiView.addSubmitButtonListener(e -> {
            String username = userInputGuiView.getUsername();
            String difficulty = userInputGuiView.getDifficulty();

            userModel.setUsername(username);

            BoardModel.DifficultyLevel level = BoardModel.DifficultyLevel.valueOf(difficulty);
            boardController.getBoardModel().settingDifficultyLevel(level);

            JOptionPane.showMessageDialog(null, "Welcome, " + username + "! Difficulty Level: " + difficulty);

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(userInputGuiView.getSubmitButton());
            boardController.setSudokuGameView(getUserModel());
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

    /**
     * Loads game records from a specified file and updates the GUI with the retrieved records.
     */
    public void loadGameRecordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/pl/polsl/lab/bartosz/sosnica/sudoku/resources/gamehistory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                if (data.length >= 4) { // Ensure there are enough fields
                    String username = data[0];
                    String date = data[1];
                    String difficulty = data[2];
                    String status = data[3];
                    userInputGuiView.addGameRecord(username, date, difficulty, status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
