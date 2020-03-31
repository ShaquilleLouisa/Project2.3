package Model;

import Exceptions.MoveException;
import Model.GameItems.*;
import View.ReversiView;

import java.util.ArrayList;

public class ReversiModel extends GameModel {
    private Board board;
    private int turns;
    private int player = 1;
    private ReversiView view;

    public ReversiModel(ReversiView view) {
        this.view = view;
        turns = 0;
        board = new Board(8, 8, new ReversiFieldStatus());
    }

    public void setFieldStatus(int move) throws MoveException {
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        if (player == 1) {
            fieldStatus.setBlack();
        } else {
            fieldStatus.setWhite();
        }

        ArrayList<Integer> xAndY = board.getMove(move);
        int x = xAndY.get(0);
        int y = xAndY.get(1);

        try {
            board.setFieldStatus(x, y, fieldStatus);
        } catch (MoveException e) {
            throw e;
        }

        //Set surrounding positions to empty a.k.a. playable.
        fieldStatus.setEmpty();
        for (int a = -1; a < 3; a++) {
            for (int b = -1; b < 3; b++) {
                if (a != 0 && b != 0 && getFieldStatus(x + a, y + b).isUnPlayable()) {
                    try {
                        board.setFieldStatus(x + a, y + b, fieldStatus);
                    } catch (MoveException e) {
                        throw e;
                    }
                }
            }
        }

        view.update(move, fieldStatus);
    }

    public ReversiFieldStatus getFieldStatus(int x, int y) {
        return (ReversiFieldStatus) board.getFieldStatus(x, y);
    }

    public int getPlayer() {
        return player;
    }

    public void switchPlayer() {
        if (player == 1) {
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
