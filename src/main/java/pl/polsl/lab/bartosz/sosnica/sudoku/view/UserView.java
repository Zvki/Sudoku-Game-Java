package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.PlayerModel;

import java.util.Scanner;

public class UserView {

    public PlayerModel usernameInput(String[] args){

        if(args.length == 0){
            System.out.println("Enter your username: ");
            Scanner inputUser = new Scanner(System.in);
            String username = inputUser.nextLine();
            return new PlayerModel(username);
        }else{
            return new PlayerModel(args[0]);
        }
    }

    public void boardDisplay(BoardModel boardModel){
        for(int i = 0; i< boardModel.getBoard().length; i++){
            for(int j = 0; j< boardModel.getBoard()[i].length; j++){
                System.out.print(boardModel.getBoard()[i][j]);
            }
            System.out.println();
        }
    }

}
