package project.gamemodules.tictactoegame;

import project.gameframework.GameLogic;
import project.gamemodules.tictactoegame.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeGameLogic extends GameLogic {
    @Override
    public ArrayList<Integer> getMoves(int player) {
        ArrayList<Integer> result = new ArrayList<>();

        for(int pos = 0; pos<getBoard().getBoard().length; pos++){
            if(getBoard().getBoardPos(pos) == 0){
                result.add(pos);
            }
        }

        return result;
    }

    @Override
    public void doMove(int pos, int player) {
        getBoard().setBoardPos(pos, player);
    }

    @Override
    public int gameOver() {
        TicTacToeBoardLogic b = (TicTacToeBoardLogic) getBoard();
        int[] board = b.getBoard();

        // search for a winner
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    if(board[0] == board[1] && board[1] == board[2] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
                case 1:
                    if(board[3] == board[4] && board[4] == board[5] && board[4] != 0) {
                        String s = String.valueOf(board[4]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
                case 2:
                    if(board[6] == board[7] && board[7] == board[8] && board[6] != 0) {
                        String s = String.valueOf(board[6]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
                case 3:
                    if(board[0] == board[3] && board[3] == board[6] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
                case 4:
                    if(board[1] == board[4] && board[4] == board[7] && board[4] != 0) {
                        String s = String.valueOf(board[4]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
                case 5:
                    if(board[2] == board[5] && board[5] == board[8] && board[2] != 0) {
                        String s = String.valueOf(board[2]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
                case 6:
                    if(board[0] == board[4] && board[4] == board[8] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
                case 7:
                    if(board[2] == board[4] && board[4] == board[6] && board[2] != 0) {
                        String s = String.valueOf(board[2]);
                        System.out.println(s + " won!");
                        return Integer.parseInt(s);
                    }
                    break;
            }
        }

        // check for a draw
        for(int value : board) {
            if(value == 0) {
                return 0;
            }
        }

        // when the game is not finished yet
        return 3;
    }
}
