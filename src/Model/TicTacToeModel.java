package Model;

import Exceptions.MoveException;
import Games.TicTacToe;
import Model.TicTacToeItems.Board;
import Model.TicTacToeItems.FieldStatus;
import View.TicTacToeView;

import java.util.ArrayList;
import java.util.HashMap;

public class TicTacToeModel extends GameModel {
    private Board board;
    private int turns;
    private int player = 1;
    private TicTacToeView view;
    private int FieldSize = 3;
    public TicTacToeModel(TicTacToeView view) {
        this.view = view;
        turns = 0;
        board = new Board(FieldSize,FieldSize);
    }

    public void setFieldStatus(int move, FieldStatus fieldStatus) throws MoveException {
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

    public FieldStatus getFieldStatus(int move) throws MoveException {
        ArrayList<Integer> xAndY = board.getMove(move);
        int x = xAndY.get(0);
        int y = xAndY.get(1);
        
        try {
             return board.getFieldStatus(x, y);
        } catch (MoveException e) {
            throw e;
        }
    }

    @Override
    public int getFieldSize() {
        return FieldSize;
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
