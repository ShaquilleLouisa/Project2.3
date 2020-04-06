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

            validMoves = setValidMoves();
        }
        view.update(move, status);
    }

    private boolean[][] setValidMoves() {
        System.out.println("Starting setting valid moves for: " + getPlayer());

        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        fieldStatus.setEmpty();

        if (validMoves != null) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    try {
                        if (validMoves[x][y] && board.getFieldStatus(x, y).getID() == ReversiFieldStatus.PLAYABLE) {
                            board.setFieldStatus(x, y, fieldStatus);
                            //System.out.println("Emptied " + x+ "  " +y);
                            //view.update(8*x+y, fieldStatus);
                        }
                    } catch (MoveException e) {
                        continue;
                    }
                }
            }
        }

        boolean[][] playableMoves = new boolean[8][8];
        fieldStatus.setPlayable();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board.getFieldStatus(x, y).getID() == 0) {

                    boolean nw = validMove(-1, -1, x, y);
                    boolean nn = validMove(-1, 0, x, y);
                    boolean ne = validMove(-1, 1, x, y);

                    boolean ww = validMove(0, -1, x, y);
                    boolean ee = validMove(0, 1, x, y);

                    boolean sw = validMove(1, -1, x, y);
                    boolean ss = validMove(1, 0, x, y);
                    boolean se = validMove(1, 1, x, y);

                    if (nw) {
                        System.out.println("This position is playable in the line of NW : " + (8 * x + y));
                        playableMoves[x][y] = true;
                    }
                    if (nn) {
                        System.out.println("This position is playable in the line of NN : " + (8 * x + y));
                        playableMoves[x][y] = true;
                    }
                    if (ne) {
                        System.out.println("This position is playable in the line of NE : " + (8 * x + y));
                        playableMoves[x][y] = true;

                    }
                    if (ww) {
                        System.out.println("This position is playable in the line of WW : " + (8 * x + y));
                        playableMoves[x][y] = true;

                    }
                    if (ee) {
                        System.out.println("This position is playable in the line of EE : " + (8 * x + y));
                        playableMoves[x][y] = true;
                    }
                    if (sw) {
                        System.out.println("This position is playable in the line of SW : " + (8 * x + y));
                        playableMoves[x][y] = true;

                    }
                    if (ss) {
                        System.out.println("This position is playable in the line of SS : " + (8 * x + y));
                        playableMoves[x][y] = true;

                    }
                    if (se) {
                        System.out.println("This position is playable in the line of SE : " + (8 * x + y));
                        playableMoves[x][y] = true;
                    }

                }
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                try {
                    if (playableMoves[x][y]) {

                        board.setFieldStatus(x, y, fieldStatus);
                        //view.update((8*x+y), fieldStatus);
                    }
                } catch (MoveException e) {
                    continue;
                }
            }
        }
        validMoves = playableMoves;
        return playableMoves;

    }

    private boolean validMove(int dr, int dc, int r, int c) {

        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }

        if (board.getFieldStatus(r + dr, c + dc).getID() == 0 || board.getFieldStatus(r + dr, c + dc).getID() == -1) {
            return false;
        }

        if (isCurrentPlayer(r + dr, c + dc)) {
            return false;
        }

        if (IsOutOfBounds(r + dr + dr, c + dc + dc))
            return false;

        return checkLineMatch(dr, dc, r + dr + dr, c + dc + dc);
    }

    private boolean checkLineMatch(int dr, int dc, int r, int c) {
        if (isCurrentPlayer(r, c)) {
            return true;
        }
        if (board.getFieldStatus(r, c).getID() == 0 || board.getFieldStatus(r, c).getID() == -1) {
            return false;
        }

        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }
        return checkLineMatch(dr, dc, r + dr, c + dc);
    }

    private void flipLine(int dr, int dc, int r, int c, FieldStatus fieldstatus, int move) {
        r += dr;
        c += dc;
        int check = r * 8 + c;
        //System.out.println("Check value before loop: " + check + " The ID of the checked spot: "
                //+ board.getFieldStatus(r, c).getID());
        while (!isCurrentPlayer(r, c) && board.getFieldStatus(r, c).getID() != 0
                && board.getFieldStatus(r, c).getID() != -1) {
            // System.out.println("Check value inside loop: " + check);
            try {
                board.setFieldStatus(r, c, fieldstatus);
                int count = (r * 8) + c;
                view.update(count, fieldstatus);
                System.out.println("flipped position: " + r + ", " + c + " position on Board: " + count);
                r += dr;
                c += dc;
            } catch (MoveException e) {
            }
        }
    }

    private boolean flipLineCheckMatch(int dr, int dc, int r, int c) {
        if (isCurrentPlayer(r, c)) {
            return true;
        }
        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }
        if (board.getFieldStatus(r + dr, c + dc).getID() == 0) {
            return false;
        }
        if (board.getFieldStatus(r + dr, c + dc).getID() == -1) {
            return false;
        }
        return flipLineCheckMatch(dr, dc, r + dr, c + dc);
    }

    private boolean flipLineCheck(int dr, int dc, int r, int c, FieldStatus fieldstatus, int move) {
        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }

        if (board.getFieldStatus(r + dr, c + dc).getID() == 0) {
            return false;
        }
        if (board.getFieldStatus(r + dr, c + dc).getID() == -1) {
            return false;
        }

        if (isCurrentPlayer(r + dr, c + dc)) {
            return false;
        }



        return flipLineCheckMatch(dr, dc, r + dr, c + dc);
    }

    public void flipBoard(int move, FieldStatus fieldstatus) {
        int r = move / 8;
        int c = move % 8;
        boolean nw = flipLineCheck(-1, -1, r, c, fieldstatus, move);
        boolean nn = flipLineCheck(-1, 0, r, c, fieldstatus, move);
        boolean ne = flipLineCheck(-1, 1, r, c, fieldstatus, move);

        boolean ww = flipLineCheck(0, -1, r, c, fieldstatus, move);
        boolean ee = flipLineCheck(0, 1, r, c, fieldstatus, move);

        boolean sw = flipLineCheck(1, -1, r, c, fieldstatus, move);
        boolean ss = flipLineCheck(1, 0, r, c, fieldstatus, move);
        boolean se = flipLineCheck(1, 1, r, c, fieldstatus, move);

        if (nw) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting NW");
            flipLine(-1, -1, r, c, fieldstatus, move);
        }
        if (nn) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting NN");
            flipLine(-1, 0, r, c, fieldstatus, move);
        }
        if (ne) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting NE");
            flipLine(-1, 1, r, c, fieldstatus, move);
        }

        if (ww) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting WW");
            flipLine(0, -1, r, c, fieldstatus, move);
        }
        if (ee) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting EE");
            flipLine(0, 1, r, c, fieldstatus, move);
        }

        if (sw) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting SW");
            flipLine(1, -1, r, c, fieldstatus, move);
        }
        if (ss) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting SS");
            flipLine(1, 0, r, c, fieldstatus, move);
        }
        if (se) {
//            System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting SE");
            flipLine(1, 1, r, c, fieldstatus, move);
        }

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
        // System.out.println("switchPlayer");

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
