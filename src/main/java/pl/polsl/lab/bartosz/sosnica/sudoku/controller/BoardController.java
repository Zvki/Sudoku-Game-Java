package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.SudokuGameView;

import javax.swing.*;
import java.util.*;

/**
 * <p>
 * The BoardController class is responsible for managing the Sudoku board.
 * It handles board setup, validation of moves, and game progress checking.
 * </p>
 * @author Bartosz Sośnica
 * @version 2.0
 */
public class BoardController {

    /**
     * The model representing the Sudoku board.
     */
    private boolean isNumberRemoved = true;

    private BoardModel boardModel;

    private SudokuGameView sudokuGameView;

    /**
     * Returns the current board model.
     *
     * @return the BoardModel object.
     */
    public BoardModel getBoardModel() {
        return boardModel;
    }

    public SudokuGameView getSudokuGameView(){
        return sudokuGameView;
    }

    /**
     * Default constructor for BoardController.
     * Initializes the BoardModel.
     */
    public BoardController() {
        boardModel = new BoardModel();
    }

    public void setSudokuGameView(){
        sudokuGameView = new SudokuGameView();
        initializeListeners();
    }

    public void initializeListeners(){
        sudokuGameView.addStartButtonListener(e -> {
            settingUpSudoku();
            updateBoard(sudokuGameView.getBoardPanel());
        });

        sudokuGameView.addAddValueButtonListener(e -> {
            readUserInput();
            updateBoard(sudokuGameView.getBoardPanel());
        });
    }

    private void updateBoard(JPanel boardPanel){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = (JTextField) boardPanel.getComponent(i * 9 + j);
                String value = getBoardModel().getBoard()[i][j].equals("[ ]") ? "" : getBoardModel().getBoard()[i][j];
                cell.setText(value);
                cell.setEditable(value.isEmpty());
            }
        }
    }

    private void readUserInput() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = sudokuGameView.getCellAt(i, j);
                String userInput = cell.getText().trim();
                // Sprawdź, czy wpis jest liczbą od 1 do 9 i czy nie jest pusty
                if(userInput.matches("[1-9]")){
                    try{
                        isMoveValid(i, j, Integer.parseInt(userInput));
                        getBoardModel().placeValue(i, j, Integer.parseInt(userInput));
                    } catch (InvalidSudokuMoveException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Sets up the Sudoku game by filling the board with numbers and removing some based on difficulty.
     */
    public void settingUpSudoku() {
        // Fill the board with numbers
        fillingUpBoard();
        // Remove numbers based on the chosen difficulty level
        removeNumbers(boardModel.getDifficultyLevel());
    }

    /**
     * Validates the move to ensure it adheres to Sudoku rules.
     *
     * @param row the row index of the move.
     * @param col the column index of the move.
     * @param value the value to place at the specified row and column.
     * @throws InvalidSudokuMoveException if the move violates Sudoku rules.
     */
    public void isMoveValid(int row, int col, int value) throws InvalidSudokuMoveException {

        boardModel.isValidNumber(row, col, value);
        boardModel.isValidPosition(row, col, value);
        boardModel.isValueIn3x3(row, col, value);

    }

    /**
     * Fills up the Sudoku board with valid numbers using backtracking.
     */
    public void fillingUpBoard() {
        fill(0, 0);
    }

    /**
     * Recursively fills the board starting from the specified row and column.
     * Uses a backtracking algorithm to generate a valid Sudoku board.
     *
     * @param row the starting row index.
     * @param col the starting column index.
     * @return true if the board is successfully filled, false otherwise.
     */
    private boolean fill(int row, int col) {
        if (row == boardModel.getBoard().length) return true;
        if (col == boardModel.getBoard()[row].length) return fill(row + 1, 0);

        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 1; i <= boardModel.getBoard().length; i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for(int number : numbers){

            try{
                isMoveValid(row,col,number);
                boardModel.placeValue(row, col, number);

                if(fill(row, col + 1)){
                    return true;
                }

                boardModel.removeValue(row, col);
            } catch (InvalidSudokuMoveException e){
                // Exception handling: skip invalid moves
            }
        }

        return false;

    }

    /**
     * Removes numbers from the board to create the puzzle based on the difficulty level.
     *
     * @param numbersRemoved the number of cells to remove.
     */
    private void removeNumbers(int numbersRemoved) {
        if(isNumberRemoved){
            Random rand = new Random();
            int removed = 0;

            while (removed < numbersRemoved) {
                int row = rand.nextInt(boardModel.getBoard().length);
                int col = rand.nextInt(boardModel.getBoard()[row].length);

                if (!boardModel.getBoard()[row][col].equals("[ ]")) {
                    boardModel.removeValue(row, col); // Remove the number
                    removed++;
                }
            }

            isNumberRemoved = false;
        }
    }

    /**
     * Checks if the Sudoku puzzle has been completed by the player.
     *
     * @return true if the puzzle is complete, false otherwise.
     */
    public boolean isGameCompleted() {

        for (int i = 0; i < boardModel.getBoard().length; i++) {
            for (int j = 0; j < boardModel.getBoard()[i].length; j++) {
                if (boardModel.getBoard()[i][j].equals("[ ]")) {
                    return false; // If any empty cell is found, the game is not completed
                }
            }
        }
        System.out.println("You solved sudoku!!"); // Display message when the puzzle is completed
        return true;
    }

    /**
     * Retrieves the difficulty level input from the command-line arguments.
     * If the input is invalid, prompts the user to input the difficulty level again via console.
     *
     * @param args the command-line arguments.
     * @return a valid difficulty level.
     */
    public String getDifficultyLevelInput(String[] args) {
        try {
            // Check if the difficulty level is provided
            getBoardModel().checkDifficultyInput(args);
            return args[1];
        } catch (InvalidSudokuMoveException e) {
            // Handle missing or invalid difficulty input
            System.out.println(e.getMessage());
            return getDifficultyLevelInputConsole();
        }
    }

    /**
     * Prompts the user for difficulty level input through the console and validates it.
     *
     * @return a valid difficulty level as a String.
     */
    public String getDifficultyLevelInputConsole() {

        Scanner inputUser = new Scanner(System.in); // Move Scanner outside of the loop
        String diffLevel;

        while (true) { // Loop until a valid difficulty level is provided

            diffLevel = inputUser.nextLine(); // Get user input

            try {
                getBoardModel().isDiffLvlNumber(diffLevel); // Validate the input
                getBoardModel().isDiffLvlCorrect(diffLevel);
                break; // Exit the loop if input is valid
            } catch (InvalidSudokuMoveException e) {
                System.out.println(e.getMessage()); // Display error message
            }
        }
        return diffLevel; // Return the valid difficulty level

    }
}
