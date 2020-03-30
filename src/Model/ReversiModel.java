package Model;

import Exceptions.MoveException;
import Model.TicTacToeItems.Board;
import Model.TicTacToeItems.FieldStatus;
import View.ReversiView;

import java.util.ArrayList;
import java.util.HashMap;

public class ReversiModel extends GameModel {
    private Board board;
    private int turns;
    private int player = 1;
    private ReversiView view;
    public ReversiModel(ReversiView view) {
        this.view = view;
        turns = 0;
        board = new Board(3,3);
    }

    public void setFieldStatus(int move) throws MoveException {
        FieldStatus fieldStatus;
        if(player == 1) {
            fieldStatus = FieldStatus.CIRCLE;
        } else {
            fieldStatus = FieldStatus.CROSS;
        }

        ArrayList<Integer> xAndY = board.getMove(move);
        int x = xAndY.get(0);
        int y = xAndY.get(1);

        try {
            board.setFieldStatus(x, y, fieldStatus);
        } catch (MoveException e) {
            throw e;
        }
        view.update(move, fieldStatus);
    }

    public FieldStatus getFieldStatus(int x, int y) {
        return board.getFieldStatus(x,y);
    }

    public int getPlayer() {
        return player;
    }

    public void switchPlayer() {
        if(player == 1) {
            player = 2;
        } else {
            player = 1;
        }
    }

    public int getTurns() {
        return turns;
    }

    public void increaseTurns() {
        turns++;
    }
}
