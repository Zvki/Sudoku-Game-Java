package pl.polsl.lab.bartosz.sosnica.sudoku.model;

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
}
