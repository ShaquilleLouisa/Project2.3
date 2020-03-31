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
        int xPosition = xAndY.get(0);
        int yPosition = xAndY.get(1);

        try {
            if (IsPlayable(xPosition, yPosition)) {
                board.setFieldStatus(xPosition, yPosition, fieldStatus);
            }
        } catch (MoveException e) {
            throw e;
        }
        SetValidMoves();
        view.update(move, fieldStatus);
    }

    private void SetValidMoves(){
        // Set surrounding positions to playable.
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        fieldStatus.isPlayable();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boolean isValid = false;
                for (int xx = -1; xx < 2; xx++) {
                    for (int yy = -1; yy < 2; yy++) {
                        if (xx != 0 && yy != 0) {
                            isValid = validMove(xx, yy, x, y);
                        }
                    }
                }
                if (isValid) {
                    try {
                        board.setFieldStatus(x, y, fieldStatus);
                    } catch (MoveException e) {
                        continue;
                    }
                }
            }
        }
    }

    private boolean validMove(int dr, int dc, int r, int c) {
        if (IsOutOfBounds(r + dr, c + dc))
            return false;
        if (getFieldStatus(r + dr, c + dc).isCurrentPlayer(player))
            return false;
        if (IsOutOfBounds(r + dr + dr, c + dc + dc))
            return false;
        return checkLineMatch(dr, dc, r + dr + dr, c + dc + dc);
    }

    private boolean checkLineMatch(int dr, int dc, int r, int c) {
        if (getFieldStatus(r, c).isCurrentPlayer(player)) {
            return true;
        }
        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }
        return checkLineMatch(dr, dc, r, c);
    }

    public boolean IsOutOfBounds(int x, int y) {
        if (x > 7 || x < 0 || y > 7 || y < 0)
            return true;
        return false;
    }

    public boolean IsPlayable(int x, int y) throws MoveException {
        if (getFieldStatus(x, y).isPlayable())
            return true;
        throw new MoveException("Field is not Playable");
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
