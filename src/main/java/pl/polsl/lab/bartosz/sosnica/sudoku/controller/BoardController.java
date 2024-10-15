package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;

import java.util.*;

public class BoardController {

    private BoardModel boardModel;

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardController() throws InvalidSudokuMoveException {
        boardModel = new BoardModel();
        fillingUpBoard();
    }

    public BoardController(int numbersRemoved) throws InvalidSudokuMoveException {
        boardModel = new BoardModel();
        fillingUpBoard();
        removeNumbers(numbersRemoved);
        System.out.println("niggers");
    }

    public boolean isMoveValid(int row, int col, int value) throws InvalidSudokuMoveException {

        boardModel.isValidNumber(row, col, value);

        if(!boardModel.isValidPosition(row, col, value)){
            return false;
        }

        if(boardModel.isValueIn3x3(row, col, value)){
            return false;
        }

        return true;
    }

    public void fillingUpBoard() throws InvalidSudokuMoveException {
       fill(0,0);
    }

    public boolean fill(int row, int col) throws InvalidSudokuMoveException {
        if (row == boardModel.getBoard().length) return true;
        if (col == boardModel.getBoard()[row].length) return fill(row + 1, 0);

        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 1; i <= boardModel.getBoard().length; i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for(int number : numbers){
            if(isMoveValid(row,col,number)){
                boardModel.getBoard()[row][col] = "[" + number + "]";

                if(fill(row, col + 1)){
                    return true;
                }

                boardModel.getBoard()[row][col] = "[ ]";
            }
        }

        return false;

    }

    private void removeNumbers(int numbersRemoved){
        Random rand = new Random();
        int removed = 0;

        while(removed < numbersRemoved){
            int row = rand.nextInt(boardModel.getBoard().length);
            int col = rand.nextInt(boardModel.getBoard().length);

            if(!boardModel.getBoard()[row][col].equals("[ ]")){
                boardModel.getBoard()[row][col] = "[ ]";
                removed++;
            }
        }
    }

}
