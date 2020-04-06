package model;

import exceptions.MoveException;
import model.gameitems.*;
import view.ReversiView;

import java.util.ArrayList;

public class ReversiModel extends GameModel {
    private Board board;
    private int turns;
    private int player = ReversiFieldStatus.WHITE;
    private ReversiView view;
    private int boardSize = 8;
    private boolean[][] validMoves;
    private boolean useAi;
    private boolean online;
    private boolean doubleAi;

    public ReversiModel(ReversiView view) {
        this.view = view;
        turns = 0;
        useAi = false;
        online = false;
        board = new Board(boardSize, boardSize, new ReversiFieldStatus());
        //System.out.println("setFirstMoves");
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
        validMoves = setValidMoves();

    }

    public void setFieldStatus(int move, FieldStatus status) throws MoveException {
        // boolean[][] playableMoves = setValidMoves();
        // setValidMoves();
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

            // board.setFieldStatus(xPosition, yPosition, status);
            validMoves = setValidMoves();
        }
        view.update(move, status);
    }

    private boolean[][] setValidMoves() {
        System.out.println("Starting setting valid moves for: "+getPlayer());

        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        fieldStatus.setEmpty();
        int counter = 0;

        if (validMoves != null) {
            //System.out.println("Clear " + validMoves);
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    try {
                        if (validMoves[x][y] && board.getFieldStatus(x, y).getID() == ReversiFieldStatus.PLAYABLE) {
                            //System.out.println("Clear PlayableMoves" + counter + " - " + fieldStatus.getValue());
                            board.setFieldStatus(x, y, fieldStatus);
                            view.update(counter, fieldStatus);
                        }
                    } catch (MoveException e) {
                        continue;
                    }
                    counter++;
                }
            }
        }

        boolean[][] playableMoves = new boolean[8][8];
        fieldStatus.setPlayable();
        // Set surrounding positions to playable.
        counter = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board.getFieldStatus(x, y).getID() == 0) {

                    // boolean isValid = false;
                    // for (int xx = -1; xx < 2; xx++) {
                    // if (xx != 0 && y != 0) {
                    // isValid = validMove(xx, y, x, y);
                    // }
                    // }
                    // for (int yy = -1; yy < 2; yy++) {
                    // if (x != 0 && yy != 0) {
                    // isValid = validMove(x, yy, x, y);
                    // }
                    // }
                    boolean nw = validMove(-1, -1, x, y);
                    boolean nn = validMove(-1, 0, x, y);
                    boolean ne = validMove(-1, 1, x, y);

                    boolean ww = validMove(0, -1, x, y);
                    boolean ee = validMove(0, 1, x, y);

                    boolean sw = validMove(1, -1, x, y);
                    boolean ss = validMove(1, 0, x, y);
                    boolean se = validMove(1, 1, x, y);

                    // isValid = validMove(0, -1, x, y);
                    // isValid = validMove(0, 1, x, y);

                    if (nw || nn || ne || ww || ee || sw || ss || se) {
                        // try {
                        // board.setFieldStatus(x, y, fieldStatus);
                        // view.update(counter, fieldStatus);
                        playableMoves[x][y] = true;

                        // } catch (MoveException e) {
                        // continue;
                        // }
                    }
                }
                counter++;
            }
        }
        counter = 0;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                try {
                    if (playableMoves[x][y]) {
                        System.out.println("This position is playable: " + counter);
                        board.setFieldStatus(x, y, fieldStatus);
                        view.update(counter, fieldStatus);
                    }
                } catch (MoveException e) {
                    continue;
                }
                counter++;
            }
        }

        return playableMoves;
    }

    private boolean validMove(int dr, int dc, int r, int c) {

        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }

//        if (IsOutOfBounds(c + dc, c + dc)) {
//            return false;
//        }

        if (board.getFieldStatus(r + dr, c + dc).getID() == 0) {
            return false;
        }

        if (isCurrentPlayer(r + dr, c + dc)) {
            return false;
        }

        if (IsOutOfBounds(r + dr + dr, c + dc + dc))
            return false;

//        if (IsOutOfBounds(c + dc + dc, c + dc + dc))
//            return false;

        return checkLineMatch(dr, dc, r + dr + dr, c + dc + dc);
    }

    private boolean checkLineMatch(int dr, int dc, int r, int c) {
        if (isCurrentPlayer(r, c)) {
            return true;
        }
        if (board.getFieldStatus(r, c).getID() == 0) {
            return false;
        }

        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }
//        if (IsOutOfBounds(c + dc, c + dc)) {
//            return false;
//        }
        return checkLineMatch(dr, dc, r + dr, c + dc);
    }

    private boolean flipLine(int dr, int dc, int r, int c, FieldStatus fieldstatus, int move) {
        // ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        if (r + dr < 0 || r + dr > 7)
            return false;
        if (c + dc < 0 || c + dc > 7)
            return false;
        if (board.getFieldStatus(r + dr, c  + dc).getID() == 0) {
            return false;
        }
        if (board.getFieldStatus(r + dr, c  + dc).getID() == -1) {
            return false;
        }
        if (isCurrentPlayer(r + dr, c + dc)) {
            try {
                System.out.println(
                        "flipped: " + (r + dr) + ", " + (c + dc) + " fieldstatus value: " + fieldstatus.getValue()+ " de huidige speler is: "+ getPlayer());
                board.setFieldStatus(r + dr, c + dc, fieldstatus);
                int count = 0;
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (x == r + dr && y == c + dc) {
                            view.update(count, fieldstatus);
                        }
                        count++;
                    }
                }
                return true;
            } catch (MoveException e) {
                return false;
            }
        } else if (flipLine(dr, dc, r + dr, c + dc, fieldstatus, move)) {
            try {
                System.out.println("flipped: uiteindelijk " + (r + dr) + ", " + (c + dc) + " fieldstatus ID: "
                        + fieldstatus.getID() + " fieldstatus value: " + fieldstatus.getValue());
                board.setFieldStatus(r + dr, c + dc, fieldstatus);
                int count = 0;
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (x == r + dr && y == c + dc) {
                            view.update(count, fieldstatus);
                        }
                        count++;
                    }
                }
                return true;
            } catch (MoveException e) {
                return false;
            }
        }
        // System.out.println("HIER MOET JE NOOIT KUNNEN KOMEN");
        return true;
    }

    public void flipBoard(int move, FieldStatus fieldstatus) {
        int r = move / 8;
        int c = move % 8;
        boolean nw = flipLine(-1, -1, r, c, fieldstatus, move);
        boolean nn = flipLine(-1, 0, r, c, fieldstatus, move);
        boolean ne = flipLine(-1, 1, r, c, fieldstatus, move);

        boolean ww = flipLine(0, -1, r, c, fieldstatus, move);
        boolean ee = flipLine(0, 1, r, c, fieldstatus, move);

        boolean sw = flipLine(1, -1, r, c, fieldstatus, move);
        boolean ss = flipLine(1, 0, r, c, fieldstatus, move);
        boolean se = flipLine(1, 1, r, c, fieldstatus, move);

    }

    public boolean IsOutOfBounds(int x, int y) {
        if ((x > 7 || x < 0) || (y > 7 || y < 0))
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
        System.out.println("switchPlayer");

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
    public void setDoubleAi(boolean ai) {
        this.doubleAi = ai;
    }

    @Override
    public boolean isDoubleAi() {
        return doubleAi;
    }

    @Override
    public int getUserMove() {
        //todo fix usermoves for reversi
        return 0;
    }

    @Override
    public void setUserMove(int move) {
        //todo fix usermoves for reversi
    }

    @Override
    public void setAiUse(boolean useAi) {
        this.useAi = useAi;
    }
}
