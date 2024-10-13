package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.*;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.PlayerModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

public class UserController{

    public PlayerModel playerModel;
    public UserView userView;
    public BoardController boardController;

    public UserController(BoardController boardController, UserView userView){
        this.boardController = boardController;
        this.userView = userView;
    }

    public void settingUpUser(String username){

    }

    public void processUserInput(int row, int column, int value) throws InvalidSudokuMoveException {

        int realRow = row - 1;
        int realColumn = column - 1;

        if(boardController.isMoveValid(realRow, realColumn, value)){
            boardController.placeValue(realRow, realColumn, value);
            userView.boardDisplay(boardController.boardModel);
            System.out.println();
        }else{
            throw new InvalidSudokuMoveException();
        }
    }
}
