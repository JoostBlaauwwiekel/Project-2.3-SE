package Project_SE_Periode3.GameBoard;

public class TicTacToeFinished implements WinBehaviour {
    int counter = 0;

    public boolean isFinished(char[][] board) {
        counter++;
        for (int a = 0; a < 8; a++) {
            switch (a) {
                case 0:
                    if(board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != '-') {
                        String s = String.valueOf(board[0][0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 1:
                    if(board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != '-') {
                        String s = String.valueOf(board[1][0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 2:
                    if(board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != '-') {
                        String s = String.valueOf(board[2][0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 3:
                    if(board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != '-') {
                        String s = String.valueOf(board[0][0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 4:
                    if(board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != '-') {
                        String s = String.valueOf(board[0][1]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 5:
                    if(board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != '-') {
                        String s = String.valueOf(board[0][2]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 6:
                    if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
                        String s = String.valueOf(board[0][0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 7:
                    if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
                        String s = String.valueOf(board[0][2]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
            }
        }
        if(counter == 10) {
            System.out.println("Draw!");
            return true;
        }

        return false;
    }

}