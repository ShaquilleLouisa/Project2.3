package model;

import exceptions.MoveException;
import games.Reversi;
import model.gameitems.*;
import view.ReversiView;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReversiModel extends GameModel {
    private final Lock lock = new ReentrantLock();
    private Board board;
    private int boardSize = 8;
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

                } catch (MoveException e) {
                    e.printStackTrace();
                }
            }
        }
        ReversiFieldStatus black = new ReversiFieldStatus();
        black.setBLACK();

        //First player is always black
        player = black.getID();
    }

    @Override
    public void setFieldStatus(int move, FieldStatus status) throws MoveException {
        lock.lock();
        int x = move / 8;
        int y = move % 8;
        board.setFieldStatus(x, y, status);
        view.update(move, status);
        lock.unlock();
    }

    @Override
    public FieldStatus getFieldStatus(int move) {
        int x = move / 8;
        int y = move % 8;
        return board.getFieldStatus(x, y);
    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void setAiUse(boolean useAi) {
        this.useAi = useAi;
    }

    @Override
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
    public int getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(int player) {
        this.player = player;
    }

    @Override
    public void setFirstMoves() {
        ReversiFieldStatus WHITE = new ReversiFieldStatus();
        WHITE.setWHITE();
        ReversiFieldStatus BLACK = new ReversiFieldStatus();
        BLACK.setBLACK();

        try {
            setFieldStatus(27, WHITE);
            setFieldStatus(28, BLACK);
            setFieldStatus(36, WHITE);
            setFieldStatus(35, BLACK);
            System.out.println("Board starting positions done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        firstMovesSet = true;
    }

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
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                //System.out.println((x*8) + y + " | " + board.getFieldStatus(x, y).getID() + " DIT IS " + reversiFieldStatus.getID());
                if (board.getFieldStatus(x, y).getID() == 0) {
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

                    }
                    if (ee) {
//                        System.out.println("This position is playable in the line of EE : " + (8 * x + y));
                        validMoves[x][y] = true;
                    }
                    if (sw) {
//                        System.out.println("This position is playable in the line of SW : " + (8 * x + y));
                        validMoves[x][y] = true;

                    }
                    if (ss) {
//                        System.out.println("This position is playable in the line of SS : " + (8 * x + y));
                        validMoves[x][y] = true;

                    }
                    if (se) {
//                        System.out.println("This position is playable in the line of SE : " + (8 * x + y));
                        validMoves[x][y] = true;
                    }

                }
            }
        }
        lock.unlock();
        return validMoves;
    }

    private boolean validMove(int dr, int dc, int r, int c, ReversiFieldStatus fieldStatus) {
        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }

        if (board.getFieldStatus(r + dr, c + dc).getID() == 0 || board.getFieldStatus(r + dr, c + dc).getID() == -1) {
            return false;
        }

        if (isCurrentPlayer(r + dr, c + dc, fieldStatus)) {
            return false;
        }

        return checkLineMatch(dr, dc, r + dr, c + dc, fieldStatus);
    }

    private boolean checkLineMatch(int dr, int dc, int r, int c, ReversiFieldStatus fieldStatus) {
        if (isCurrentPlayer(r, c, fieldStatus)) {
            return true;
        }
        if (board.getFieldStatus(r, c).getID() == 0 || board.getFieldStatus(r, c).getID() == -1) {
            return false;
        }

        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }
        return checkLineMatch(dr, dc, r + dr, c + dc, fieldStatus);
    }

    /**
     * Flips the fieldstatuses of the line that have to be flipped
     * @param dr is the X-axis direction
     * @param dc is the Y-axis direction
     * @param r is the X-axis position
     * @param c is the Y-axis position
     * @param fieldStatus is the fieldstatus of the current player
     */
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

    /**
     *
     * @param dr is the X-axis direction
     * @param dc is the Y-axis direction
     * @param r is the X-axis position
     * @param c is the Y-axis position
     * @param fieldStatus is the fieldstatus of the current player
     * @return returns the recursive function to traverse the board
     */
    private boolean flipLineCheckMatch(int dr, int dc, int r, int c, ReversiFieldStatus fieldStatus) {
        if (isCurrentPlayer(r, c, fieldStatus)) {
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
        return flipLineCheckMatch(dr, dc, r + dr, c + dc, fieldStatus);
    }

    /**
     *
     * @param dr is the X-axis direction
     * @param dc is the Y-axis direction
     * @param r is the X-axis position
     * @param c is the Y-axis position
     * @param fieldStatus is the fieldstatus of the current player
     * @return flipLineCheckMatch to traverse the rest of the line that has to be flipped
     */
    private boolean flipLineCheck(int dr, int dc, int r, int c, ReversiFieldStatus fieldStatus) {
        if (IsOutOfBounds(r + dr, c + dc)) {
            return false;
        }

        if (board.getFieldStatus(r + dr, c + dc).getID() == 0) {
            return false;
        }
        if (board.getFieldStatus(r + dr, c + dc).getID() == -1) {
            return false;
        }

        if (isCurrentPlayer(r + dr, c + dc, fieldStatus)) {
            return false;
        }

        return flipLineCheckMatch(dr, dc, r + dr, c + dc, fieldStatus);
    }

    /**
     * This method flips the board accordingly with the move that is given to the method
     * @param move is the move that has been set
     * @param fieldstatus is the fieldstatus that the stones have to be flipped to
     */
    public synchronized void flipBoard(int move, ReversiFieldStatus fieldstatus) {
        //System.out.println("In flip board with " + fieldstatus.getID());
        int r = move / 8;
        int c = move % 8;
        boolean nw = flipLineCheck(-1, -1, r, c, fieldstatus);
        boolean nn = flipLineCheck(-1, 0, r, c, fieldstatus);
        boolean ne = flipLineCheck(-1, 1, r, c, fieldstatus);

        boolean ww = flipLineCheck(0, -1, r, c, fieldstatus);
        boolean ee = flipLineCheck(0, 1, r, c, fieldstatus);

        boolean sw = flipLineCheck(1, -1, r, c, fieldstatus);
        boolean ss = flipLineCheck(1, 0, r, c, fieldstatus);
        boolean se = flipLineCheck(1, 1, r, c, fieldstatus);

        if (nw) {
            flipLine(-1, -1, r, c, fieldstatus);
        }
        if (nn) {
            flipLine(-1, 0, r, c, fieldstatus);
        }
        if (ne) {
            flipLine(-1, 1, r, c, fieldstatus);
        }

        if (ww) {
            flipLine(0, -1, r, c, fieldstatus);
        }
        if (ee) {
            flipLine(0, 1, r, c, fieldstatus);
        }
        if (sw) {
            flipLine(1, -1, r, c, fieldstatus);
        }
        if (ss) {
            flipLine(1, 0, r, c, fieldstatus);
        }
        if (se) {
            flipLine(1, 1, r, c, fieldstatus);
        }
    }

    /**
     *
     * @param x is the position on the X-axis
     * @param y is the position on the Y-axis
     * @return returns true if the X and Y position are in bounds of the board. It returns false if the X or Y positions are out of bounds.
     */
    public boolean IsOutOfBounds(int x, int y) {
        if ((x > 7 || x < 0) || (y > 7 || y < 0))
            return true;
        return false;
    }

    /**
     * Checks if the given position on the board is owned by the current player
     * @param x is the position on the X-axis
     * @param y is the position on the Y-axis
     * @param fieldStatus is the fieldstatus of the current player
     * @return returns true if the position is owned by the current player or else it is false
     */
    public boolean isCurrentPlayer(int x, int y, ReversiFieldStatus fieldStatus) {
        if (board.getFieldStatus(x, y).getID() == fieldStatus.getID() && fieldStatus.getID() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a position on the board is playable
     * @param x is the position on the X-axis
     * @param y is the position on the Y-axis
     * @return returns true if the position is playable otherwise it returns false
     */
    public boolean isPlayable(int x, int y) {
        if (board.getFieldStatus(x, y).getID() == ReversiFieldStatus.PLAYABLE)
            return true;
        return false;
    }

    /**
     * returns whether the first move has been set
     * @return firstMovesSet
     */
    public boolean isFirstMovesSet() {
        return firstMovesSet;
    }

    public void setFirstMovesSet(boolean firstMovesSet) {
        this.firstMovesSet = firstMovesSet;
    }

    /**
     * Checks if all positions on the board are owned by a player
     * @param board is the board of the game
     * @return
     */
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

    public int checkScore(Board board, int playerID){
        int fieldCounter1 = 0;
        int fieldCounter2 = 0;
        for (int x = 0; x < board.getFieldSize(); x++) {
            for (int y = 0; y < board.getFieldSize(); y++) {
                if (board.getFieldStatus(x, y).getID() != 0 && board.getFieldStatus(x, y).getID() != -1) {
                    if (board.getFieldStatus(x, y).getID() == 1) {
                        fieldCounter1++;
                    } else {
                        fieldCounter2++;
                    }
                }
            }
        }
        if(playerID == 1){
            return fieldCounter1;
        }else if(playerID == 2){
            return fieldCounter2;
        }else{
            return 0;
        }
    }
}
