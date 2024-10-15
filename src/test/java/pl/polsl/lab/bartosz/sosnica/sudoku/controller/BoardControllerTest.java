package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import org.junit.jupiter.api.Test;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

class BoardControllerTest {

    BoardController boardController = new BoardController();
    UserView userView = new UserView();

    BoardControllerTest() throws InvalidSudokuMoveException {
    }

    @Test
    void isMoveValid() {
    }

    @Test
    void placeValue() {
    }

    @Test
    void isValueIn3x3() {
    }

    @Test
    void fillingUpBoard() {

        userView.boardDisplay(boardController.getBoardModel());

    }

    @Test
    void fill() {
    }
}