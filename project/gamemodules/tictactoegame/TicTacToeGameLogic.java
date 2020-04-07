package project.gamemodules.tictactoegame;

import project.gameframework.GameLogic;
import project.gamemodules.tictactoegame.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeGameLogic extends GameLogic {

    private TicTacToeBoardLogic ticTacToeBoardLogic;

    public TicTacToeGameLogic() {
        this.ticTacToeBoardLogic = new TicTacToeBoardLogic();
        setBoard(this.ticTacToeBoardLogic);
    }

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
                        return Integer.parseInt(s);
                    }
                    break;
                case 1:
                    if(board[3] == board[4] && board[4] == board[5] && board[4] != 0) {
                        String s = String.valueOf(board[4]);
                        return Integer.parseInt(s);
                    }
                    break;
                case 2:
                    if(board[6] == board[7] && board[7] == board[8] && board[6] != 0) {
                        String s = String.valueOf(board[6]);
                        return Integer.parseInt(s);
                    }
                    break;
                case 3:
                    if(board[0] == board[3] && board[3] == board[6] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        return Integer.parseInt(s);
                    }
                    break;
                case 4:
                    if(board[1] == board[4] && board[4] == board[7] && board[4] != 0) {
                        String s = String.valueOf(board[4]);
                        return Integer.parseInt(s);
                    }
                    break;
                case 5:
                    if(board[2] == board[5] && board[5] == board[8] && board[2] != 0) {
                        String s = String.valueOf(board[2]);
                        return Integer.parseInt(s);
                    }
                    break;
                case 6:
                    if(board[0] == board[4] && board[4] == board[8] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        return Integer.parseInt(s);
                    }
                    break;
                case 7:
                    if(board[2] == board[4] && board[4] == board[6] && board[2] != 0) {
                        String s = String.valueOf(board[2]);
                        return Integer.parseInt(s);
                    }
                    break;
            }
        }

        // check if game is not over yet
        for(int value : board) {
            if(value == 0) {
                return 0;
            }
        }

        // when the game ended in a draw
        return 3;
    }
}
