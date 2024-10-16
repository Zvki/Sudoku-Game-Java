package pl.polsl.lab.bartosz.sosnica.sudoku;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.BoardController;
import pl.polsl.lab.bartosz.sosnica.sudoku.controller.GameController;
import pl.polsl.lab.bartosz.sosnica.sudoku.controller.UserController;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.GameModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

public class Main {

    static GameController gameController;

    public static void main(String[] args) {

        gameController = new GameController(args);
        gameController.gameLoop();

    }

}