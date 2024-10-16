package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.GameModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

public class GameController {

    GameModel gameModel;

    public GameController(String[] args) {

        try {
            gameModel = new GameModel(args);

        } catch (InvalidSudokuMoveException e) {
            throw new RuntimeException(e);
        }
    }

    public void gameLoop(){

        while(!gameModel.getUserController().boardController.isGameCompleted()){

            gameModel.getUserController().userView.clearScreen();
            gameModel.getUserController().userView.boardDisplay(gameModel.getUserController().boardController.getBoardModel());
            gameModel.getUserController().playTurn();

        }

    }

}
