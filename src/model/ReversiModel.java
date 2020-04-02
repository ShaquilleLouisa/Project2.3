package model;

import exceptions.MoveException;
import model.gameitems.*;
import view.ReversiView;

import java.util.ArrayList;

public class ReversiModel extends GameModel {
    private Board board;
    private int turns;
    private int player = 1;
    private ReversiView view;
    private int boardSize = 8;
    private boolean[][] validMoves;

    public ReversiModel(ReversiView view) {
        this.view = view;
        turns = 0;
        board = new Board(boardSize, boardSize, new ReversiFieldStatus());
        System.out.println("setFirstMoves");
        setFirstMoves();
    }

    public void setFirstMoves() {
        // Random Color
        ReversiFieldStatus black = new ReversiFieldStatus();
        black.setBlack();
        ReversiFieldStatus white = new ReversiFieldStatus();
        white.setWhite();

        try {
            setFieldStatus(27, black);
            setFieldStatus(28, white);
            setFieldStatus(36, black);
            setFieldStatus(35, white);
            System.out.println("Board starting positions done!");
        } catch (MoveException e) {
            System.out.println(e);
            return;
        }
    }

    public void setFieldStatus(int move, FieldStatus status) throws MoveException {
        boolean[][] playableMoves = setValidMoves();
        validMoves = playableMoves;// setValidMoves();
        // ReversiFieldStatus fieldStatus = new ReversiFieldStatus();

        ArrayList<Integer> xAndY = board.getMove(move);
        int xPosition = xAndY.get(0);
        int yPosition = xAndY.get(1);

        if (move == 27 || move == 28 || move == 35 || move == 36) {
            board.setFieldStatus(xPosition, yPosition, status);
        } else {

            try {
                if (isPlayable(xPosition, yPosition)) {
                    board.setFieldStatus(xPosition, yPosition, status);
                }
            } catch (MoveException e) {
                throw e;
            }

            board.setFieldStatus(xPosition, yPosition, status);

            // // fieldStatus.setEmpty();
            // for (int x = 0; x < 8; x++) {
            // for (int y = 0; y < 8; y++) {
            // if (playableMoves[x][y] && x != xPosition && y != yPosition)
            // board.setFieldStatus(x, y, fieldStatus);
            // }
            // }
        }
        view.update(move, status);
    }

    private boolean[][] setValidMoves() {

        boolean[][] playableMoves = new boolean[8][8];
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        fieldStatus.setPlayable();
        // Set surrounding positions to playable.
        int counter = 0;
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
                        view.update(counter, fieldStatus);
                        playableMoves[x][y] = true;
                    } catch (MoveException e) {
                        continue;
                    }
                }
                counter++;
            }
        }
        return playableMoves;
    }

    private boolean validMove(int dr, int dc, int r, int c) {
        if (IsOutOfBounds(r + dr, c + dc))
            return false;
        if (isCurrentPlayer(r + dr, c + dc))
            return false;
        if (IsOutOfBounds(r + dr + dr, c + dc + dc))
            return false;
        return checkLineMatch(dr, dc, r + dr + dr, c + dc + dc);
    }

    private boolean checkLineMatch(int dr, int dc, int r, int c) {
        if (isCurrentPlayer(r, c)) {
            return true;
        }
        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }
        return checkLineMatch(dr, dc, r + dr, c + dc);
    }

    public boolean IsOutOfBounds(int x, int y) {
        if (x > 7 || x < 0 || y > 7 || y < 0)
            return true;
        return false;
    }

    public boolean isCurrentPlayer(int x, int y) {
        if (board.getFieldStatus(x, y).getID() == player)
            return true;
        return false;
    }

    public boolean isPlayable(int x, int y) {
        if (board.getFieldStatus(x, y).getID() == ReversiFieldStatus.PLAYABLE)
            return true;
        return false;
    }

    public boolean[][] getValidMoves() {
        return validMoves;
    }

    @Override
    public FieldStatus getFieldStatus(int move) {
        ArrayList<Integer> xAndY = board.getMove(move);
        int x = xAndY.get(0);
        int y = xAndY.get(1);
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

    @Override
    public int getFieldSize() {
        return boardSize;
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
