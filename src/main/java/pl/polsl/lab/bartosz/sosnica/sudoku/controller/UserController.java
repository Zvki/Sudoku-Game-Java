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

    public void settingUpUser(String[] args){
        String username = userView.getUsernameInput(args);
        this.playerModel = new PlayerModel(username);
        userView.displayMessage("Welcome, " + username + "!");
    }

    public void processUserInput(int row, int column, int value) {

        int realRow = row - 1;
        int realColumn = column - 1;

        try {
            if (boardController.isMoveValid(realRow, realColumn, value)) {
                boardController.getBoardModel().placeValue(realRow, realColumn, value);
                userView.displayMessage("Move placed successfully!");
            } else {
                userView.displayMessage("Invalid move! Please try again.");
            }
        } catch (InvalidSudokuMoveException e) {
            userView.displayMessage("Error: Invalid Sudoku Move.");
        }
    }

    public void playTurn() {

        int[] move = userView.getUserMove();
        int row = move[0];
        int column = move[1];
        int value = move[2];

        processUserInput(row, column, value);

    }

}
