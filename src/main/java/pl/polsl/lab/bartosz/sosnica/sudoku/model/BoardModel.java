package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;

import java.util.Objects;

public class BoardModel {

    private String[][] board;

    public BoardModel() {
        settingUpBoard();
    }

    public String[][] getBoard() {
        return board;
    }

    public void settingUpBoard(){
        board = new String[9][9];

        for(int i = 0; i< board.length; i++){
            for(int j = 0; j< board[i].length; j++){
                board[i][j] = "[ ]";
            }
        }
    }

    public void placeValue(int row, int col, int value){
        board[row][col] = "[" + value + "]";
    }

    public void isValidNumber(int row,int col, int value)  {

        if(value > 10 || value < 1){
            try {
                throw new InvalidSudokuMoveException("The value must be between 1 and 10");
            } catch (InvalidSudokuMoveException e) {
                throw new RuntimeException(e);
            }
        }

        if(row > getBoard().length - 1 || row < 0 || col > getBoard().length - 1 || col < 0){
            try {
                throw new InvalidSudokuMoveException("The row must be between 0 and " + (getBoard().length - 1));
            } catch (InvalidSudokuMoveException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean isValidPosition(int row, int col, int value){

        for(int i = 0; i < getBoard().length; i++){
            if(Objects.equals(getBoard()[row][i], "[" + value + "]")){
                return false;
            }
        }

        for(int i = 0; i < getBoard().length; i++){
            if(Objects.equals(getBoard()[i][col], "[" + value + "]")){
                return false;
            }
        }

        return true;

    }

    public boolean isValueIn3x3(int row, int col, int value){

        int startColumn = (col / 3) * 3 ;
        int startRow = (row / 3) * 3 ;

        for(int i = startRow; i < startRow + 3; i++) {
            for(int j = startColumn; j < startColumn + 3; j++) {
                if(Objects.equals(getBoard()[i][j], "[" + value + "]")){
                    return true;
                }
            }
        }

        return false;
    }

}
