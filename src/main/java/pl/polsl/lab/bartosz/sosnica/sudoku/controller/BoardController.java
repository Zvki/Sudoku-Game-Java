package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;

import java.util.*;

public class BoardController {

    public BoardModel boardModel;

    public BoardController() throws InvalidSudokuMoveException {
        boardModel = new BoardModel();
        fillingUpBoard();
    }

    public BoardController(int numbersRemoved) throws InvalidSudokuMoveException {
        boardModel = new BoardModel();
        fillingUpBoard();
        removeNumbers(numbersRemoved);
    }

    public boolean isMoveValid(int row, int col, int value) throws InvalidSudokuMoveException {
        if(value > 10 || value < 1){
            throw new InvalidSudokuMoveException("The value must be between 1 and 10");
        }

        if(row > boardModel.getBoard().length - 1 || row < 0 || col > boardModel.getBoard().length - 1 || col < 0){
            throw new InvalidSudokuMoveException("The row must be between 0 and " + (boardModel.getBoard().length - 1));
        }

        for(int i = 0; i < boardModel.getBoard().length; i++){
            if(Objects.equals(boardModel.getBoard()[row][i], "[" + value + "]")){
                return false;
            }
        }

        for(int i = 0; i < boardModel.getBoard().length; i++){
            if(Objects.equals(boardModel.getBoard()[i][col], "[" + value + "]")){
                return false;
            }
        }

        if(isValueIn3x3(row, col, value)){
            return false;
        }

        return true;
    }

    public void placeValue(int row, int col, int value){

        boardModel.getBoard()[row][col] = "[" + value + "]";

    }

    public boolean isValueIn3x3(int row, int col, int value){

        int startColumn = (col / 3) * 3 ;
        int startRow = (row / 3) * 3 ;

        for(int i = startRow; i < startRow + 3; i++) {
            for(int j = startColumn; j < startColumn + 3; j++) {
                if(Objects.equals(boardModel.getBoard()[i][j], "[" + value + "]")){
                    return true;
                }
            }
        }

        return false;
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
