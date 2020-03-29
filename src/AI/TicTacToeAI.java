package AI;

import Games.TicTacToe;
import Model.AIModel;
import Model.TicTacToeItems.Board;
import Model.TicTacToeItems.FieldStatus;
import Model.TicTacToeModel;

import java.util.ArrayList;
import java.util.HashMap;

import static Model.TicTacToeItems.FieldStatus.*;

public class TicTacToeAI extends AI {
    Board board;
    public TicTacToeAI(TicTacToeModel ticTacToeModel) {
        super(ticTacToeModel);
    }


    @Override
    public int calculateNextMove() {
        board = aiModel.getBoard();
        Move move = findBestMove(board);
        System.out.println(move.x);
        System.out.println(move.y);

        int counter = 0;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (i == move.x && j == move.y) {
                    return counter;
                } else {
                    counter++;
                }
            }
        }
        return -1;
    }

    int fieldSize = aiModel.getFieldSize();

    static class Move {
        int x, y;
    }

    private boolean isMovesLeft(Board board) {
        int fieldSize = board.getFieldSize();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (aiModel.fieldStatus(i, j) == NONE) {
                    return true;
                }
            }
        }
        System.out.println("No moves left");
        return false;
    }

    private int evaluate(Board board) {
        //Check horizontal
        for (int i = 0; i < 3; i++) {
            ArrayList<FieldStatus> fieldStatuses = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                fieldStatuses.add(board.getFieldStatus(i, j));
            }
            if (fieldStatuses.get(0) != NONE && fieldStatuses.get(0) == fieldStatuses.get(1) && fieldStatuses.get(0) == fieldStatuses.get(2)) {
                if (fieldStatuses.get(0) == CIRCLE)
                    if (fieldStatuses.get(0) == CIRCLE)
                        return 10;
                    else
                        return -10;
            }
        }

        //Check vertical
        for (int j = 0; j < 3; j++) {
            ArrayList<FieldStatus> fieldStatuses = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                fieldStatuses.add(board.getFieldStatus(i, j));
            }
            if (fieldStatuses.get(0) != NONE && fieldStatuses.get(0) == fieldStatuses.get(1) && fieldStatuses.get(0) == fieldStatuses.get(2)) {
                if (fieldStatuses.get(0) == CIRCLE)
                    return 10;
                else
                    return -10;
            }
        }

        //Check other
        if (board.getFieldStatus(0, 0) != NONE && board.getFieldStatus(0, 0) == board.getFieldStatus(1, 1) && board.getFieldStatus(0, 0) == board.getFieldStatus(2, 2)) {
            if (board.getFieldStatus(0, 0) == CIRCLE)
                return 10;
            else
                return -10;
        }
        if (board.getFieldStatus(2, 0) != NONE && board.getFieldStatus(2, 0) == board.getFieldStatus(1, 1) && board.getFieldStatus(2, 0) == board.getFieldStatus(0, 2)) {
            if (board.getFieldStatus(2, 0) == CIRCLE)
                return 10;
            else
                return -10;
        }
        return 0;

    }

    private int minmax(Board copyBoard, int depth, boolean isMax) {
        int score = evaluate(copyBoard);

        if (score == 10) {
            return score;
        }

        if (score == -10) {
            return score;
        }

        if (!isMovesLeft(copyBoard)) {
            return 0;
        }

        if (isMax) {
            int best = -1000;

            for (int i = 0; i < copyBoard.getFieldSize(); i++) {
                for (int j = 0; j < copyBoard.getFieldSize(); j++) {
                    if (copyBoard.getFieldStatus(i, j) == FieldStatus.NONE) {
                        try {
                            copyBoard.setFieldStatus(i, j, FieldStatus.CIRCLE);
                        } catch (Exception e) {
                            System.out.println("OEF");
                            e.printStackTrace();
                            e.getMessage();
                            System.out.println("OEF");
                        }
                        best = Math.max(best, minmax(copyBoard, depth + 1, false));
                        System.out.println("Minmaxshit");
                        try {
                            copyBoard.setFieldStatus(i, j, FieldStatus.NONE);
                        } catch (Exception e) {
                            System.out.println("OEF");
                            e.printStackTrace();
                            e.getMessage();
                            System.out.println("OEF");
                        }
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < copyBoard.getFieldSize(); i++) {
                for (int j = 0; j < copyBoard.getFieldSize(); j++) {
                    if (copyBoard.getFieldStatus(i, j) == NONE) {
                        try {
                            copyBoard.setFieldStatus(i, j, CROSS);
                        } catch (Exception e) {
                            e.printStackTrace();
                            e.getMessage();
                            System.out.println("OEF");
                        }
                        best = Math.min(best, minmax(copyBoard, depth + 1, true));

                        try {
                            copyBoard.setFieldStatus(i, j, NONE);
                        } catch (Exception e) {
                            System.out.println("OEF");
                            e.printStackTrace();
                            e.getMessage();
                            System.out.println("OEF");
                        }
                    }
                }
            }
            return best;
        }
    }

    private Move findBestMove(Board board) {
        Board copyBoard = board;
        Move bestMove = new Move();
        bestMove.x = -1;
        bestMove.y = -1;
        int bestVal = -1000;


        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (copyBoard.getFieldStatus(i, j) == NONE) {
                    try {
                        copyBoard.setFieldStatus(i, j, CIRCLE);
                    } catch (Exception e) {
                        System.out.println("OEF");
                    }

                    int moveVal = minmax(copyBoard, 0, false);

                    try {
                        copyBoard.setFieldStatus(i, j, NONE);
                    } catch (Exception e) {
                        System.out.println("OEF");
                    }

                    if (moveVal > bestVal) {
                        bestMove.x = i;
                        bestMove.y = j;
                        bestVal = moveVal;
                        System.out.println("asdf");
                    }
                }
            }
        }
        System.out.println("Best move is " + bestVal);
        return bestMove;
    }


}
