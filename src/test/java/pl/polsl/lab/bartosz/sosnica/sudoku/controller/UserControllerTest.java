package pl.polsl.lab.bartosz.sosnica.sudoku.controller;

import org.junit.jupiter.api.Test;
import pl.polsl.lab.bartosz.sosnica.sudoku.exception.InvalidSudokuMoveException;
import pl.polsl.lab.bartosz.sosnica.sudoku.view.UserView;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {


    BoardController boardController = new BoardController();
    UserView userView = new UserView();
    UserController userController = new UserController(boardController, userView);

    UserControllerTest() throws InvalidSudokuMoveException {
    }


    @Test
    void processUserInput() throws InvalidSudokuMoveException {

        userController.processUserInput(4,5,2);
        userController.processUserInput(4,4,3);


        assertThrows(InvalidSudokuMoveException.class, () -> {
            userController.processUserInput(4, 6, 2);
        });


    }
}