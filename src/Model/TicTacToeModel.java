package Model;

import Exceptions.MoveException;
import Model.GameItems.TicTacToeFieldStatus;
import Model.GameItems.Board;
import Model.GameItems.FieldStatus;
import View.TicTacToeView;

import java.util.ArrayList;
import java.util.HashMap;

public class TicTacToeModel extends GameModel {
    private Board board;
    private int turns;
    private int player = 1;
    private TicTacToeView view;
    public TicTacToeModel(TicTacToeView view) {
        this.view = view;
        turns = 0;
        board = new Board(3,3, new TicTacToeFieldStatus());
    }

    public void setFieldStatus(int move) throws MoveException {
        TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
        if(player == 1) {
            fieldStatus.setCircle();
        } else {
            fieldStatus.setCross();
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

    public TicTacToeFieldStatus getFieldStatus(int x, int y) {
        return (TicTacToeFieldStatus)board.getFieldStatus(x,y);
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