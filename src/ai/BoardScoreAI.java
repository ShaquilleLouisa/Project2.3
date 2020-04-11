package ai;

import exceptions.MoveException;
import model.GameModel;
import model.gameitems.Board;
import model.gameitems.FieldStatus;
import model.gameitems.ReversiFieldStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BoardScoreAI extends AI implements ReversiAI {

    Board board;
    public HashMap<Integer, Integer> movesWithPoints = new HashMap<>();

    public BoardScoreAI(GameModel gameModel) {
        super(gameModel);
        movesWithPoints.put(0, 40);
        movesWithPoints.put(7, 40);
        movesWithPoints.put(24, 8);
        movesWithPoints.put(39, 8);
        movesWithPoints.put(4, 8);
        movesWithPoints.put(3, 8);
        movesWithPoints.put(31, 8);
        movesWithPoints.put(56, 40);
        movesWithPoints.put(63, 40);
        movesWithPoints.put(5, 15);
        movesWithPoints.put(16, 15);
        movesWithPoints.put(18, 12);
        movesWithPoints.put(21, 12);
        movesWithPoints.put(23, 15);
        movesWithPoints.put(40, 15);
        movesWithPoints.put(42, 12);
        movesWithPoints.put(45, 12);
        movesWithPoints.put(47, 15);
        movesWithPoints.put(58, 15);
        movesWithPoints.put(61, 15);
        movesWithPoints.put(60, 8);
        movesWithPoints.put(1, -15);
        movesWithPoints.put(6, -15);
        movesWithPoints.put(8, -15);
        movesWithPoints.put(9, -15);
        movesWithPoints.put(14, -15);
        movesWithPoints.put(15, -15);
        movesWithPoints.put(48, -15);
        movesWithPoints.put(49, -15);
        movesWithPoints.put(54, -15);
        movesWithPoints.put(55, -15);
        movesWithPoints.put(59, 8);
        movesWithPoints.put(57, -15);
        movesWithPoints.put(62, -15);
    }

    private int flipLine(int dr, int dc, int r, int c, ReversiFieldStatus fieldstatus) {
        int counter = 0;
        r += dr;
        c += dc;
        int check = r * 8 + c;
        //System.out.println("Check value before loop: " + check + " The ID of the checked spot: "
        //+ board.getFieldStatus(r, c).getID());
        while (!isCurrentPlayer(r, c, fieldstatus) && board.getFieldStatus(r, c).getID() != 0
                && board.getFieldStatus(r, c).getID() != -1) {
            // System.out.println("Check value inside loop: " + check);
            r += dr;
            c += dc;
            counter++;
        }
        return counter;
    }

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

    public int calculateMoveValue(int move, ReversiFieldStatus fieldstatus) {
        int totalValue = 0;

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
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting NW");
            totalValue += flipLine(-1, -1, r, c, fieldstatus);
        }
        if (nn) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting NN");
            totalValue += flipLine(-1, 0, r, c, fieldstatus);
        }
        if (ne) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting NE");
            totalValue += flipLine(-1, 1, r, c, fieldstatus);
        }

        if (ww) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting WW");
            totalValue += flipLine(0, -1, r, c, fieldstatus);
        }
        if (ee) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting EE");
            totalValue += flipLine(0, 1, r, c, fieldstatus);
        }

        if (sw) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting SW");
            totalValue += flipLine(1, -1, r, c, fieldstatus);
        }
        if (ss) {
            //System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting SS");
            totalValue += flipLine(1, 0, r, c, fieldstatus);
        }
        if (se) {
//            System.out.println("flipping for position: " + r + ", " + c + " fieldstatus value: "
//                    + fieldstatus.getValue() + " in de richting SE");
            totalValue += flipLine(1, 1, r, c, fieldstatus);
        }
        return totalValue;
    }

    public boolean IsOutOfBounds(int x, int y) {
        if ((x > 7 || x < 0) || (y > 7 || y < 0))
            return true;
        return false;
    }

    public boolean isCurrentPlayer(int x, int y, ReversiFieldStatus fieldStatus) {
        if (board.getFieldStatus(x, y).getID() == fieldStatus.getID() && fieldStatus.getID() != 0) {
            return true;
        }
        return false;
    }


    public Board copyBoard(Board board) {
        Board returnBoard = new Board(board.getFieldSize(), board.getFieldSize());
        ReversiFieldStatus reversiFieldStatus = new ReversiFieldStatus();
        for (int x = 0; x < board.getFieldSize(); x++) {
            for (int y = 0; y < board.getFieldSize(); y++) {
                reversiFieldStatus.setId(board.getFieldStatus(x, y).getID());
                try {
                    returnBoard.setFieldStatus(x, y, reversiFieldStatus);
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnBoard;
    }


    @Override
    public int calculateNextMove(FieldStatus fieldStatus) {
        boolean[][] validMoves = aiModel.calculateValidMoves(fieldStatus);
        board = copyBoard(aiModel.getBoard());
        ReversiFieldStatus reversiFieldStatus = new ReversiFieldStatus();
        reversiFieldStatus.setId(fieldStatus.getID());
        boolean done = false;
        boolean skipped = false;
        ArrayList<Integer> validMovesArray = new ArrayList<>();
        //System.out.println("number of valid moves: " + validMovesArray.size());
        while (!done) {
            if (validMoves != null) {
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (validMoves[x][y]) {
                            int validMove = 8 * x + y;
                            validMovesArray.add(validMove);
                        }
                    }
                }
            }
            if (validMovesArray.size() == 0) {
                validMoves = aiModel.calculateValidMoves(fieldStatus);
            } else {
                done = true;
            }

        }
        HashMap<Integer, Integer> points = new HashMap<>();
        for (Integer move : validMovesArray) {
            int movePoints = calculateMoveValue(move, reversiFieldStatus);
            try {
                int point = movesWithPoints.get(move);
                points.put(move, (point + movePoints));
            } catch (NullPointerException e) {
                points.put(move, movePoints);
            }


        }

        int max = -999;
        int bestMove = -1;
        for (Map.Entry<Integer, Integer> point : points.entrySet()) {
            Integer move = point.getKey();
            Integer movePoints = point.getValue();

            if (movePoints > max) {
                bestMove = move;
                max = movePoints;
            }
        }

        return bestMove;
    }
}
