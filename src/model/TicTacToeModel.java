package model;

import exceptions.MoveException;
import games.TicTacToe;
import model.gameitems.*;
import view.TicTacToeView;

import java.util.ArrayList;
import java.util.HashMap;

public class TicTacToeModel extends GameModel {
    private Board board;
    private int turns;
    private int player = 1;
    private TicTacToeView view;
    private int FieldSize = 3;
    private boolean useAi;
    private boolean online;
    private boolean doubleAi;
    private int userMove;
    public TicTacToeModel(TicTacToeView view) {
        this.view = view;
        turns = 0;
        board = new Board(FieldSize, FieldSize, new TicTacToeFieldStatus());
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

    public FieldStatus getFieldStatus(int move) {
        ArrayList<Integer> xAndY = board.getMove(move);
        int x = xAndY.get(0);
        int y = xAndY.get(1);

        return board.getFieldStatus(x, y);

    }

    @Override
    public int getFieldSize() {
        return FieldSize;
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

    public Board getBoard() {
        return board;
    }

    @Override
    public boolean isPlayable(int x, int y) {
        return board.getFieldStatus(x, y).getID() != 0;
    }

    @Override
    public boolean[][] getValidMoves() {
        return new boolean[0][];
    }


    //FOR OFFLINE GAME
    public int checkEnd(Board board) {
        for (int y = 0; y < board.getFieldSize(); y++) {
            if (board.getFieldStatus(0, y).getID() == board.getFieldStatus(1, y).getID() && board.getFieldStatus(1, y).getID() == board.getFieldStatus(2, y).getID()) {
                if (board.getFieldStatus(0, y).getID() == TicTacToeFieldStatus.CIRCLE) {
                    return 2;
                } else if (board.getFieldStatus(0, y).getID() == TicTacToeFieldStatus.CROSS) {
                    return 1;
                }
            }
        }

        for (int x = 0; x < board.getFieldSize(); x++) {
            if (board.getFieldStatus(x, 0).getID() == board.getFieldStatus(x, 1).getID() && board.getFieldStatus(x, 1).getID() == board.getFieldStatus(x, 2).getID()) {

                if (board.getFieldStatus(x, 0).getID() == TicTacToeFieldStatus.CIRCLE) {
                    return 2;
                } else if (board.getFieldStatus(x, 0).getID() == TicTacToeFieldStatus.CROSS) {
                    return 1;
                }
            }
        }

        if (board.getFieldStatus(0, 0).getID() == board.getFieldStatus(1, 1).getID() && board.getFieldStatus(1, 1).getID() == board.getFieldStatus(2, 2).getID()) {
            if (board.getFieldStatus(0, 0).getID() == TicTacToeFieldStatus.CIRCLE) {
                return 2;
            } else if (board.getFieldStatus(0, 0).getID() == TicTacToeFieldStatus.CROSS) {
                return 1;
            }
        }

        if (board.getFieldStatus(0, 2).getID() == board.getFieldStatus(1, 1).getID() && board.getFieldStatus(1, 1).getID() == board.getFieldStatus(2, 0).getID()) {
            if (board.getFieldStatus(0, 2).getID() == TicTacToeFieldStatus.CIRCLE) {
                return 2;
            } else if (board.getFieldStatus(0, 2).getID() == TicTacToeFieldStatus.CROSS) {
                return 1;
            }
        }
        return -1;
    }


    public boolean isUseAi() {
        return useAi;
    }

    @Override
    public void setOnlineUse(boolean online) {
        this.online = online;
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    @Override
    public void setAiUse(boolean useAi) {
        this.useAi = useAi;
    }

    @Override
    public boolean isDoubleAi() {
        return doubleAi;
    }

    @Override
    public void setDoubleAi(boolean doubleAi) {
        this.doubleAi = doubleAi;
    }

    public void setUserMove(int userMove) {
        this.userMove = userMove;
    }

    public int getUserMove() {
        return userMove;
    }
}
