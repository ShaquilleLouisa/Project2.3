package Model;

import Exceptions.MoveException;
import Games.TicTacToe;
import Model.TicTacToeItems.Board;
import Model.TicTacToeItems.FieldStatus;
import View.TicTacToeView;

public class TicTacToeModel extends GameModel {
    private Board board;
    private int turns;
    private int player = 1;
    private TicTacToeView view;
    public TicTacToeModel(TicTacToeView view) {
        this.view = view;
        turns = 0;
        board = new Board();
    }

    public void setFieldStatus(int x, int y) throws MoveException {
        FieldStatus fieldStatus = FieldStatus.NONE;
        if(player == 1) {
            fieldStatus = FieldStatus.CIRCLE;
        } else {
            fieldStatus = FieldStatus.CROSS;
        }
        try {
            board.setFieldStatus(x, y, fieldStatus);
        } catch (MoveException e) {
            throw e;
        }
    }

    public FieldStatus getFieldStatus(int x, int y) {
        return board.getFieldStatus(x,y);
    }

    public int getPlayer() {
        return player;
    }

    public void switchPlayer() {
        view.update(this);
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
