package pl.polsl.lab.bartosz.sosnica.sudoku;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.BoardController;
import pl.polsl.lab.bartosz.sosnica.sudoku.controller.UserController;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

public class Main {

    static UserController userController;

    public static void main(String[] args) {

        try {
            userController = new UserController(new BoardController(3), new UserView());
            userController.settingUpUser(args);
            gameLoop();
        } catch (InvalidSudokuMoveException e) {
            throw new RuntimeException(e);
        }

    }

    public static void gameLoop(){
        while (true) {
            userController.userView.boardDisplay(userController.boardController.getBoardModel());
            userController.playTurn();
        }
    }
}