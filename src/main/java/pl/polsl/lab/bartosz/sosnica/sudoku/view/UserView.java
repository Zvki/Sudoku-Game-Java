package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;

import java.util.List;

/**
 * <p>
 * Class responsible for user interaction in the console-based Sudoku game.
 * Provides methods for getting input from the user and displaying information.
 * </p>
 * @author Bartosz Sośnica
 * @version 1.0
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
        List<List<BoardModel.BoardCell>> board = boardModel.getBoard();


        System.out.print("      ");
        for (int col = 0; col < board.get(0).size(); col++) {
            System.out.print((col + 1) + "   ");
        }

        System.out.println();
        System.out.println();

        for (int i = 0; i < board.size(); i++) {

            System.out.print((i + 1) + "    ");


            for (int j = 0; j < board.get(i).size(); j++) {
                String value = board.get(i).get(j).value();
                System.out.print((value.isEmpty() ? "." : value) + "   ");
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