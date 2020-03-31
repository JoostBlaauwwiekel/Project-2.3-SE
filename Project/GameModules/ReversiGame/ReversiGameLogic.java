package Project.GameModules.ReversiGame;

import Project.GameFramework.GameLogic;

import java.util.ArrayList;

public class ReversiGameLogic extends GameLogic {

    @Override
    public ArrayList<Integer> getMoves(int player){
        ArrayList<Integer> result = new ArrayList<>();

        for(int pos = 0; pos < getBoard().getBoard().length; pos++){
            if(getBoard().getBoardPos(pos) == 0){
                for(int dir = 0; dir < 8; dir++){
                    if(checkDir(dir, pos, player)){
                        result.add(pos);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void doMove(int pos, int player) {
        getBoard().setBoardPos(pos, player);
        for(int dir=0; dir < 8; dir++){
            if(checkDir(dir, pos, player)){
                flipDir(pos, dir, player);
            }
        }
    }

    /**
     * This method checks if the game is over and then returns the winner.
     * @return int representing the winner of the game, 3 means draw.
     */
    @Override
    public int gameOver (){
        ReversiBoard board = (ReversiBoard)getBoard();
        if(board.getDiscCount(0) == 0 || getMoves(1).size() == 0 || getMoves(2).size() == 0){
            if(board.getDiscCount(1) > board.getDiscCount(2)){
                return 1;
            } else if(board.getDiscCount(1) < board.getDiscCount(2)){
                return 2;
            } else {
                return 3;
            }
        }
        return 0;
    }

    private int getTarget(int dir, int pos){
        // Calculate position of target.
        int target = 0;
        switch(dir){
            case 0:
                target = pos - 9;
                break;
            case 1:
                target = pos - 8;
                break;
            case 2:
                target = pos - 7;
                break;
            case 3:
                target = pos + 1;
                break;
            case 4:
                target = pos + 9;
                break;
            case 5:
                target = pos + 8;
                break;
            case 6:
                target = pos + 7;
                break;
            case 7:
                target = pos - 1;
                break;
        }
        return target;
    }

    private void flipDir(int pos, int dir, int player){
        int target = getTarget(pos, dir);
        int targetState = getBoard().getBoardPos(target);
        if(targetState == 3 - player){
            getBoard().setBoardPos(target, player);
            flipDir(target, dir, player);
        }
    }

    /**
     * Method used to call checkDir function without having to pass a boolean.
     * See next method for more.
     */
    public boolean checkDir(int dir, int pos, int color){
        return checkDir(dir, pos, color, false);
    }

    /**
     * This is a recursive method that checks if there are any flippable discs in a specific direction of a position.
     * This is used to determine if a move is valid.
     * @param dir a integer representing a direction that needs to be checked.
     * @param pos integer of the position that needs to be checked.
     * @param color integer representing the color of the player we want to check for.
     * @param flipped boolean indicating if a flippable disc has been found.
     * @return a boolean, true = there is a flippable disc in this direction,
     *                    false = there is no flippable disc in this direction.
     */
    public boolean checkDir(int dir, int pos, int player, boolean flipped){
        // Filtering out vertical edge cases.
        int vEdge = pos % 8;
        switch(vEdge){
            // Left edge
            case 0:
                if(dir == 6 || dir == 7 || dir == 0){
                    return false;
                }
                break;
            // Right edge
            case 7:
                if(dir == 2 || dir == 3 || dir == 4){
                    return false;
                }
                break;
        }

        // Filtering out horizontal edge cases.
        if(pos < 8){
            if(dir == 0 || dir == 1 || dir == 2){
                return false;
            }
        } else if(pos > 55){
            if(dir == 4 || dir == 5 || dir == 6){
                return false;
            }
        }

        // Recursive part that checks if there is a flippable disc in direction.
        int target = getTarget(dir, pos);
        int targetState = getBoard().getBoardPos(target);
        if(targetState == 3 - player){
            if(checkDir(dir, target, player, true)){
                return true;
            }
        } else if(targetState == player && flipped){
            return true;
        }

        return false;
    }
}
