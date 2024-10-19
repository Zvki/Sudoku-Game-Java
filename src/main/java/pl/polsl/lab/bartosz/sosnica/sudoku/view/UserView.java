package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;

/**
 * @author Bartosz Sośnica
 * @version 1.0
 * Class responsible for user interaction in the console-based Sudoku game.
 * Provides methods for getting input from the user and displaying information.
 */
public class UserView {

    /**
     * Default constructor for UserView.
     */
    public UserView() {}

    /**
     * Displays the Sudoku board in a formatted way, including row and column numbers for better readability.
     *
     * @param boardModel the model containing the Sudoku board.
     */
    public void boardDisplay(BoardModel boardModel) {
        String[][] board = boardModel.getBoard();

        // Display column numbers
        System.out.print("      ");
        for (int col = 0; col < board[0].length; col++) {
            System.out.print((col + 1) + "   ");
        }

        System.out.println();
        System.out.println();

        // Display row numbers and board content
        for (int i = 0; i < board.length; i++) {
            // Display row number
            System.out.print((i + 1) + "    ");

            // Display board content for each row
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }


    /**
     * Clears the console by printing empty lines.
     * This method simulates clearing the screen in a console application.
     */
    public void clearScreen() {
        // Print 50 empty lines to simulate clearing the console
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}