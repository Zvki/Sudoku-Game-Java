package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.UserController;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.BoardModel;
import pl.polsl.lab.bartosz.sosnica.sudoku.model.PlayerModel;

import java.util.Scanner;

public class UserView {

   public UserView(){}

    public String getUsernameInput(String[] args){

        if(args.length == 0){
            System.out.println("Enter your username: ");
            Scanner inputUser = new Scanner(System.in);
            return inputUser.nextLine();
        }else{
            return args[0];
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

    public void displayMessage(String message){
       System.out.println(message);
    }

    public int[] getUserMove(){
       Scanner inputUser = new Scanner(System.in);

       int[]move = new int[3];

        System.out.println("Enter row number (1-9): ");
        move[0] = Integer.parseInt(inputUser.nextLine());

        System.out.println("Enter column number (1-9): ");
        move[1] = Integer.parseInt(inputUser.nextLine());

        System.out.println("Enter value (1-9): ");
        move[2] = Integer.parseInt(inputUser.nextLine());

        return move;
   }

}
