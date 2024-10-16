package pl.polsl.lab.bartosz.sosnica.sudoku.model;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.BoardController;
import pl.polsl.lab.bartosz.sosnica.sudoku.controller.UserController;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

public class GameModel {

    static UserController userController;

    public GameModel(String[] args) throws InvalidSudokuMoveException {

        try {
            userController = new UserController(new BoardController(3), new UserView());
            userController.settingUpUser(args);

        } catch (InvalidSudokuMoveException e) {
            throw new RuntimeException(e);
        }

    }

    public UserController getUserController() {return userController;}

}
