package AI;

import Games.TicTacToe;
import Model.AIModel;
import Model.GameItems.*;
import Model.TicTacToeModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class TicTacToeAI extends AI {
    private Board boardCopy;
    public TicTacToeAI(TicTacToeModel ticTacToeModel) {
        super(ticTacToeModel);
    }

    @Override
    public int calculateNextMove() {
        boardCopy = aiModel.getBoard();
        Move move = findBestMove();
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
        System.out.println("Counter " + counter + " :-1probleem");
        return -1;
    }

    int fieldSize = aiModel.getFieldSize();

    static class Move {
        int x, y;
    }

    private Move findBestMove() {
        Move bestMove = new Move();
        bestMove.x = -1;
        bestMove.y = -1;
        int bestVal = -1000;

        int counter = 0;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                try {
                    TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();

                    // System.out.println("ImHere :" + boardCopy.getFieldStatus(i , j).getID() + " " + counter);
                    // if (boardCopy.getFieldStatus(i, j).isEmpty()) {

                    System.out.println("ImHere :" + aiModel.getFieldStatus(counter).getID() + " " + counter);
                    if (aiModel.getFieldStatus(counter).isEmpty()) {
                        try {
                            fieldStatus.setCircle();
                            boardCopy.setFieldStatus(i, j, fieldStatus);
                        } catch (Exception e) {
                            System.out.println("OEF");
                        }

                        int moveVal = minmax(boardCopy, 0, false);
                        System.out.println(moveVal);
                        try {
                            fieldStatus.setEmpty();
                            boardCopy.setFieldStatus(i, j, fieldStatus);
                        } catch (Exception e) {
                            System.out.println("OEF");
                        }
                        //System.out.println("ImHere (2) :" + aiModel.getFieldStatus(counter).getID());
                        if (moveVal > bestVal) {
                            System.out.println(i + " " + j);
                            bestMove.x = i;
                            bestMove.y = j;
                            bestVal = moveVal;
                        }
                    }
                    counter++;
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                    System.out.println("OEF");
                }
            }
        }
        return bestMove;
    }

    private int minmax(Board board, int depth, boolean isMax) {
        int score = evaluate();
        if (score == 10) {
            return score;
        }

        if (score == -10) {
            return score;
        }

        if (!isMovesLeft()) {
            return 0;
        }

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    try {
                        TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                        if (board.getFieldStatus(i, j).isEmpty()) {
                            try {
                                fieldStatus.setCircle();
                                board.setFieldStatus(i, j, fieldStatus);
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                            best = Math.max(best, minmax(board, depth + 1, false));
                            try {
                                fieldStatus.setEmpty();
                                board.setFieldStatus(i, j, fieldStatus);
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                        System.out.println("OEF");
                    }

                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    try {
                        TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                        if (board.getFieldStatus(i, j).isEmpty()) {
                            try {
                                fieldStatus.setCross();
                                board.setFieldStatus(i, j, fieldStatus);
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                            best = Math.min(best, minmax(board, depth + 1, true));

                            try {
                                fieldStatus.setEmpty();
                                board.setFieldStatus(i, j, fieldStatus);
                            } catch (Exception e) {
                                System.out.println("OEF");
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("Message");
                    }
                }
            }
            return best;
        }
    }

    private boolean isMovesLeft() {
        int fieldSize = boardCopy.getFieldSize();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                try {
                    if(boardCopy.getFieldStatus(i,j).isEmpty()) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    private int evaluate() {
//        Check horizontal
        ArrayList<FieldStatus> fieldStatuses = new ArrayList<>();
        for (int i = 0; i < boardCopy.getFieldSize(); i++) {
            for (int j = 0; j < boardCopy.getFieldSize(); j++) {
                try {
                    fieldStatuses.add(boardCopy.getFieldStatus(i, j));
                } catch (Exception e) {
                    System.out.println("Field not found");
                }
            }
        }
        if (!fieldStatuses.get(0).isEmpty() && fieldStatuses.get(0) == fieldStatuses.get(1) && fieldStatuses.get(0) == fieldStatuses.get(2)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)fieldStatuses.get(0);
            if (fieldStatus.isCircle())
                if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
        }
        if (!fieldStatuses.get(3).isEmpty() && fieldStatuses.get(3) == fieldStatuses.get(4) && fieldStatuses.get(3) == fieldStatuses.get(5)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)fieldStatuses.get(3);
            if (fieldStatus.isCircle())
                if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
        }
        if (!fieldStatuses.get(6).isEmpty() && fieldStatuses.get(6) == fieldStatuses.get(7) && fieldStatuses.get(6) == fieldStatuses.get(8)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)fieldStatuses.get(6);
            if (fieldStatus.isCircle())
                if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
        }


        fieldStatuses = new ArrayList<>();
        for (int i = 0; i < boardCopy.getFieldSize(); i++) {
            for (int j = 0; j < boardCopy.getFieldSize(); j++) {
                try {
                    fieldStatuses.add(boardCopy.getFieldStatus(j, i));
                } catch (Exception e) {
                    System.out.println("Field not found");
                }
            }
        }
        if (!fieldStatuses.get(0).isEmpty() && fieldStatuses.get(0) == fieldStatuses.get(1) && fieldStatuses.get(0) == fieldStatuses.get(2)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)fieldStatuses.get(0);
            if (fieldStatus.isCircle())
                if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
        }
        if (!fieldStatuses.get(3).isEmpty() && fieldStatuses.get(3) == fieldStatuses.get(4) && fieldStatuses.get(3) == fieldStatuses.get(5)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)fieldStatuses.get(3);
            if (fieldStatus.isCircle())
                if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
        }
        if (!fieldStatuses.get(6).isEmpty() && fieldStatuses.get(6) == fieldStatuses.get(7) && fieldStatuses.get(6) == fieldStatuses.get(8)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)fieldStatuses.get(6);
            if (fieldStatus.isCircle())
                if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
        }

        // Check other
        try {
            if (!boardCopy.getFieldStatus(0, 0).isEmpty() && boardCopy.getFieldStatus(0, 0) == boardCopy.getFieldStatus(1, 1)
                    && boardCopy.getFieldStatus(0, 0) == boardCopy.getFieldStatus(2, 2)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)boardCopy.getFieldStatus(0, 0);
                if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
            }
        } catch (Exception e) {
            System.out.println("Field not found");
        }

        try {
            if (!boardCopy.getFieldStatus(2, 0).isEmpty() && boardCopy.getFieldStatus(2, 0) == boardCopy.getFieldStatus(1, 1)
                    && boardCopy.getFieldStatus(2, 0) == boardCopy.getFieldStatus(0, 2)) {
            TicTacToeFieldStatus fieldStatus = (TicTacToeFieldStatus)boardCopy.getFieldStatus(0, 2);
            if (fieldStatus.isCircle())
                    return 10;
                else
                    return -10;
            }
        } catch (Exception e) {
            System.out.println("Field not found");
        }
        return 0;

    }
}

