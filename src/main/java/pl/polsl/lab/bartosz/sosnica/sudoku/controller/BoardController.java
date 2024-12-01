package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import lombok.Getter;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.UserModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.SudokuGameView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 * The BoardController class is responsible for managing the Sudoku game.
 * It handles board setup, validation of moves, game progress checking, and saving game history.
 * </p>
 * <p>
 * This class interacts with the BoardModel to manage the board data,
 * SudokuGameView for UI interactions, and UserModel to track the user's progress and preferences.
 * </p>
 * @version 3.0
 */
public class BoardController {

    /**
     * Flag indicating whether the Sudoku board has been set up.
     */
    private boolean isSudokuSet = false;

    /**
     * The model representing the Sudoku board.
     * -- GETTER --
     *  Returns the current board model.
     *
     * @return the BoardModel object.

     */
    @Getter
    private BoardModel boardModel;

    /**
     * The view for displaying the Sudoku game.
     */
    private SudokuGameView sudokuGameView;

    /**
     * Returns the Sudoku game view.
     *
     * @return the SudokuGameView object.
     */
//    public SudokuGameView getSudokuGameView() {
//        return sudokuGameView;
//    }

    /**
     * Default constructor for BoardController.
     * Initializes the BoardModel.
     */
    public BoardController() {
        boardModel = new BoardModel();
    }

    /**
     * Sets up the Sudoku game view and initializes listeners for UI interactions.
     *
     * @param userModel the UserModel object representing the user.
     */
    public void setSudokuGameView(UserModel userModel) {
        sudokuGameView = new SudokuGameView();
        initializeListeners(userModel);
    }

    /**
     * Initializes listeners for buttons and window events in the Sudoku game view.
     *
     * @param userModel the UserModel object representing the user.
     */
    public void initializeListeners(UserModel userModel) {
        sudokuGameView.addStartButtonListener(e -> {
            settingUpSudoku();
            updateBoard(sudokuGameView.getBoardPanel());
        });

        sudokuGameView.addAddValueButtonListener(e -> {
            readUserInput();
            updateBoard(sudokuGameView.getBoardPanel());
            isGameCompleted(userModel);
        });

        sudokuGameView.addWindowCloseListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCloseRequest(userModel);
            }
        });
    }

    /**
     * Updates the visual board in the Sudoku game view to reflect the current board model.
     *
     * @param panel the JPanel containing the Sudoku grid.
     */
    private void updateBoard(JPanel panel) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = sudokuGameView.getCellAt(i, j);
                String value = getBoardModel().getBoard().get(i).get(j).value();
                cell.setText(value != null ? value : "");
                cell.setEditable(value.isEmpty());
            }
        }
    }

    /**
     * Reads user input from the view, validates, and updates the board accordingly.
     * Displays error messages if the user makes invalid moves.
     */
    private void readUserInput() {
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        int i = blockRow * 3 + row;
                        int j = blockCol * 3 + col;
                        JTextField cell = sudokuGameView.getCellAt(i, j);
                        String userInput = cell.getText().trim();

                        if (!userInput.isEmpty() && !userInput.equals(getBoardModel().getBoard().get(i).get(j).value())) {
                            try {
                                isMoveValid(i, j, userInput);
                                getBoardModel().placeValue(i, j, userInput);
                            } catch (InvalidSudokuMoveException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets up the Sudoku game by preparing the board and removing cells based on difficulty level.
     */
    public void settingUpSudoku() {
        if (!isSudokuSet) {
            getBoardModel().settingUpBoard();
            fillingUpBoard();
            removeNumbers(boardModel.getNumberDiff());
        }
    }

    /**
     * Validates if a move is valid according to Sudoku rules.
     *
     * @param row the row index of the move.
     * @param col the column index of the move.
     * @param value the value to place at the specified row and column.
     * @throws InvalidSudokuMoveException if the move violates Sudoku rules.
     */
    public void isMoveValid(int row, int col, String value) throws InvalidSudokuMoveException {
        boardModel.validateValueUniqueness(row, col, value);
        boardModel.checkMultipleValues(value);
    }

    /**
     * Fills up the Sudoku board with valid numbers using a backtracking algorithm.
     */
    public void fillingUpBoard() {
        fill(0, 0);
    }

    /**
     * Recursively fills the board starting from the specified row and column using backtracking.
     *
     * @param row the starting row index.
     * @param col the starting column index.
     * @return true if the board is successfully filled, false otherwise.
     */
    private boolean fill(int row, int col) {
        if (row == boardModel.getBoard().size()) return true;
        if (col == boardModel.getBoard().get(row).size()) return fill(row + 1, 0);

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= boardModel.getBoard().size(); i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int number : numbers) {
            try {
                isMoveValid(row, col, String.valueOf(number));
                boardModel.placeValue(row, col, String.valueOf(number));

                if (fill(row, col + 1)) {
                    return true;
                }

                boardModel.removeValue(row, col);
            } catch (InvalidSudokuMoveException e) {
                // Skip invalid moves
            }
        }

        return false;
    }

    /**
     * Removes a specified number of cells from the board based on difficulty level to create the puzzle.
     *
     * @param numbersRemoved the number of cells to remove.
     */
    private void removeNumbers(int numbersRemoved) {
        Random rand = new Random();
        int removed = 0;
        while (removed < numbersRemoved) {
            int row = rand.nextInt(boardModel.getBoard().size());
            int col = rand.nextInt(boardModel.getBoard().get(row).size());

            if (!boardModel.getBoard().get(row).get(col).value().isEmpty()) {
                boardModel.removeValue(row, col);
                removed++;
            }
        }
        isSudokuSet = true;
    }

    /**
     * Checks if the Sudoku puzzle has been completed by the player.
     * Displays a message if the game is successfully completed.
     *
     * @param userModel the UserModel object representing the user.
     */
    public void isGameCompleted(UserModel userModel) {
        for (int i = 0; i < boardModel.getBoard().size(); i++) {
            for (int j = 0; j < boardModel.getBoard().get(i).size(); j++) {
                if (boardModel.getBoard().get(i).get(j).value().isEmpty()) {
                    isSudokuSet = true;
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "You solved sudoku!!");
        getBoardModel().changeStatus();
        isSudokuSet = false;
    }

    /**
     * Handles actions for closing the window, prompting the user for confirmation,
     * and saving the game history.
     *
     * @param userModel the UserModel object representing the user.
     */
    private void onCloseRequest(UserModel userModel) {
        int response = JOptionPane.showConfirmDialog(
                sudokuGameView,
                "Are you sure you want to close the window?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            sudokuGameView.dispose();
            saveGameRecordToFile(userModel);
        }
    }

    /**
     * Saves the game record to a file, including the username, date, difficulty level, and game status.
     *
     * @param userModel the UserModel object representing the user.
     */
    public void saveGameRecordToFile(UserModel userModel) {
        LocalDate currentDate = LocalDate.now();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/pl/polsl/lab/bartosz/sosnica/sudoku/resources/gamehistory.txt", true))) {
            writer.write(userModel.getUsername() + " " + currentDate + " " + getBoardModel().getDifficultyLevel() + " " + getBoardModel().getStatus());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the difficulty level from command-line arguments.
     * If invalid, prompts the user to enter the difficulty level.
     *
     * @param args the command-line arguments.
     * @return a valid difficulty level.
     */
//    public String getDifficultyLevelInput(String[] args) {
//        try {
//            getBoardModel().checkDifficultyInput(args);
//            return args[1];
//        } catch (InvalidSudokuMoveException e) {
//            System.out.println(e.getMessage());
//            return getDifficultyLevelInputConsole();
//        }
//    }

    /**
     * Prompts the user for difficulty level input through the console and validates it.
     *
     * @return a valid difficulty level as a String.
     */
//    public String getDifficultyLevelInputConsole() {
//        Scanner inputUser = new Scanner(System.in);
//        String diffLevel;
//
//        while (true) {
//            diffLevel = inputUser.nextLine();
//
//            try {
//                getBoardModel().isDiffLvlNumber(diffLevel);
//                getBoardModel().isDiffLvlCorrect(diffLevel);
//                break;
//            } catch (InvalidSudokuMoveException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        return diffLevel;
//    }

}
