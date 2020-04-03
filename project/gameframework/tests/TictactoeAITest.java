package project.gameframework.tests;

import javafx.scene.layout.Pane;
import project.gamemodules.tictactoegame.TicTacToeMinimaxStrategy;
import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.gamemodules.tictactoegame.TicTacToeGameLogic;

public class TictactoeAITest {
    public static void main(String[] args){

        TicTacToeBoardLogic board = new TicTacToeBoardLogic();
        TicTacToeGameLogic logic = new TicTacToeGameLogic();
        logic.setBoard(board);
        TicTacToeMinimaxStrategy ai = new TicTacToeMinimaxStrategy();

        int turn = 1;
        while(logic.gameOver() == 0){
            int move = ai.getBestMove(board, turn);
            logic.doMove(move, turn);
            board.printBoard();
            turn = 3 - turn;
        }

        switch(logic.gameOver()){
            case 1:
                System.out.println("Player 1 won!");
                break;
            case 2:
                System.out.println("Player 2 won!");
                break;
            case 3:
                System.out.println("It's a draw!");
                break;
        }
    }
}
