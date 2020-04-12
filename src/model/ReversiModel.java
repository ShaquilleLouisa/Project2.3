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
<<<<<<< Updated upstream
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
        System.out.println("setFirstMoves");
        setFirstMoves();
    }

    public void setFirstMoves() {
        // Random Color
        ReversiFieldStatus black = new ReversiFieldStatus();
        black.setBlack();
        ReversiFieldStatus white = new ReversiFieldStatus();
        white.setWhite();
=======
    int player = 0;
    private boolean useAi;
    private boolean online;
    private boolean doubleAi;
    private ReversiView view;
    boolean firstMovesSet = false;
    private int userMove = -1;

    public ReversiModel(ReversiView view) {
        this.view = view;
        board = new Board(boardSize, boardSize);
        ReversiFieldStatus empty = new ReversiFieldStatus();
        empty.setEmpty();
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                try {

                    board.setFieldStatus(x, y, empty);
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
            try {
                if (isPlayable(xPosition, yPosition)) {
                    board.setFieldStatus(xPosition, yPosition, status);
                }
            } catch (MoveException e) {
                throw e;
            }
=======
    @Override
    public int getPlayer() {
        return player;
    }
>>>>>>> Stashed changes

            // board.setFieldStatus(xPosition, yPosition, status);
            validMoves = setValidMoves();
        }
        view.update(move, status);
    }

    private boolean[][] setValidMoves() {
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

<<<<<<< Updated upstream
        boolean[][] playableMoves = new boolean[8][8];
        fieldStatus.setPlayable();
        // Set surrounding positions to playable.
        counter = 0;
=======
    @Override
    public int getUserMove() {
        return userMove;
    }

    @Override
    public void setUserMove(int move) {
        this.userMove = move;
    }

    @Override
    public boolean[][] calculateValidMoves(FieldStatus fieldStatus) {
        lock.lock();
        int oldId = (fieldStatus.getID());
        ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus) fieldStatus;
        reversiFieldStatus.setId(oldId);
        boolean[][] validMoves = new boolean[boardSize][boardSize];
>>>>>>> Stashed changes
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board.getFieldStatus(x, y).getID() == 0) {
<<<<<<< Updated upstream
=======
                    boolean nw = validMove(-1, -1, x, y, reversiFieldStatus);
                    boolean nn = validMove(-1, 0, x, y, reversiFieldStatus);
                    boolean ne = validMove(-1, 1, x, y, reversiFieldStatus);

                    boolean ww = validMove(0, -1, x, y, reversiFieldStatus);
                    boolean ee = validMove(0, 1, x, y, reversiFieldStatus);

                    boolean sw = validMove(1, -1, x, y, reversiFieldStatus);
                    boolean ss = validMove(1, 0, x, y, reversiFieldStatus);
                    boolean se = validMove(1, 1, x, y, reversiFieldStatus);

                    if (nw) {
                        //System.out.println("This position is playable in the line of NW : " + (8 * x + y));
                        validMoves[x][y] = true;
                    }
                    if (nn) {
                        //System.out.println("This position is playable in the line of NN : " + (8 * x + y));
                        validMoves[x][y] = true;
                    }
                    if (ne) {
                        //System.out.println("This position is playable in the line of NE : " + (8 * x + y));
                        validMoves[x][y] = true;

                    }
                    if (ww) {
//                        System.out.println("This position is playable in the line of WW : " + (8 * x + y));
                        validMoves[x][y] = true;
>>>>>>> Stashed changes

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
                        System.out.println("" + counter + " " + fieldStatus.getValue());
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

        if (IsOutOfBounds(r + dr, r + dr)) {
            return false;
        }

        if (IsOutOfBounds(c + dc, c + dc)) {
            return false;
        }

        if (board.getFieldStatus(r + dr, c + dc).getID() == 0) {
            return false;
        }

<<<<<<< Updated upstream
        if (isCurrentPlayer(r + dr, c + dc)) {
=======
        return checkLineMatch(dr, dc, r + dr, c + dc, fieldStatus);
    }

    private boolean checkLineMatch(int dr, int dc, int r, int c, ReversiFieldStatus fieldStatus) {
        if (isCurrentPlayer(r, c, fieldStatus)) {
            return true;
        }
        if (board.getFieldStatus(r, c).getID() == 0 || board.getFieldStatus(r, c).getID() == -1) {
>>>>>>> Stashed changes
            return false;
        }

        if (IsOutOfBounds(r + dr + dr, r + dr + dr))
            return false;
<<<<<<< Updated upstream

        if (IsOutOfBounds(c + dc + dc, c + dc + dc))
            return false;

        return checkLineMatch(dr, dc, r + dr + dr, c + dc + dc);
    }

    private boolean checkLineMatch(int dr, int dc, int r, int c) {
        if (isCurrentPlayer(r, c)) {
=======
        }
        return checkLineMatch(dr, dc, r + dr, c + dc, fieldStatus);
    }

    private void flipLine(int dr, int dc, int r, int c, ReversiFieldStatus fieldstatus) {
        r += dr;
        c += dc;
        int check = r * 8 + c;
        //System.out.println("Check value before loop: " + check + " The ID of the checked spot: "
        //+ board.getFieldStatus(r, c).getID());
        while (!isCurrentPlayer(r, c, fieldstatus) && board.getFieldStatus(r, c).getID() != 0
                && board.getFieldStatus(r, c).getID() != -1) {
            // System.out.println("Check value inside loop: " + check);
            try {
                if (board.getFieldStatus(r, c).getID() == 1) {
                    fieldstatus.setId(2);
                    //System.out.println("flipped position: " + r + ", " + c + " position on Board: " + ((r*8)+c) + " set to WHITE");
                } else {
                    fieldstatus.setId(1);
                    //System.out.println("flipped position: " + r + ", " + c + " position on Board: " + ((r*8)+c) + " set to BLACK");
                }
                board.setFieldStatus(r, c, fieldstatus);
                int count = (r * 8) + c;
                view.update(count, fieldstatus);
                r += dr;
                c += dc;
            } catch (MoveException e) {
            }
        }
    }

    private boolean flipLineCheckMatch(int dr, int dc, int r, int c, ReversiFieldStatus fieldStatus) {
        if (isCurrentPlayer(r, c, fieldStatus)) {
>>>>>>> Stashed changes
            return true;
        }
        if (board.getFieldStatus(r, c).getID() == 0) {
            return false;
        }

        if (IsOutOfBounds(r + dr, r + dr)) {
            return false;
        }
        if (IsOutOfBounds(c + dc, c + dc)) {
            return false;
        }
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
                        "flipped: " + (r + dr) + ", " + (c + dc) + " fieldstatus value: " + fieldstatus.getValue());
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

    public int checkEnd(Board board) {
        boolean player1 = false;
        boolean player2 = false;
        int fieldCounter1 = 0;
        int fieldCounter2 = 0;
        for (int x = 0; x < board.getFieldSize(); x++) {
            for (int y = 0; y < board.getFieldSize(); y++) {
                if (board.getFieldStatus(x, y).getID() != 0 && board.getFieldStatus(x, y).getID() != -1) {
                    if (board.getFieldStatus(x, y).getID() == 1) {
                        player1 = true;
                        fieldCounter1++;
                    } else {
                        player2 = true;
                        fieldCounter2++;
                    }
                }
            }
        }
        if(player1 && !player2) {
            System.out.println("1 dd");
            return 1;
        } else if(!player1 && player2) {
            System.out.println("2 dd");
            return 2;
        } else if(player1 && ((fieldCounter1+fieldCounter2) == 64) && fieldCounter1 > fieldCounter2) {
            System.out.println("3 dd");
            return 1;
        } else if(player1 && ((fieldCounter1+fieldCounter2) == 64) && fieldCounter1 < fieldCounter2) {
            System.out.println("4 dd");
            System.out.println(fieldCounter1+fieldCounter2);
            return 2;
        } else if(player1 && ((fieldCounter1+fieldCounter2) == 64) && fieldCounter1 == fieldCounter2) {
            System.out.println("5 dd");
            return 0;
        } else {
            return -1;
        }
    }
}
