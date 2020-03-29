package AI;

import Games.TicTacToe;
import Model.AIModel;
import Model.TicTacToeItems.Board;
import Model.TicTacToeItems.FieldStatus;
import Model.TicTacToeModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static Model.TicTacToeItems.FieldStatus.*;

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
                    if (aiModel.getFieldStatus(counter) == NONE) {
                        try {
                            boardCopy.setFieldStatus(i, j, CIRCLE);
                        } catch (Exception e) {
                            System.out.println("OEF");
                        }

                        int moveVal = minmax(boardCopy, 0, false);

                        try {
                            boardCopy.setFieldStatus(i, j, NONE);
                        } catch (Exception e) {
                            System.out.println("OEF");
                        }

                        if (moveVal > bestVal) {
                            bestMove.x = i;
                            bestMove.y = j;
                            bestVal = moveVal;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                    System.out.println("OEF");
                }
                counter++;
            }
        }

        System.out.println("Best move is " + bestVal);
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
            for (int i = 0; i < board.getFieldSize(); i++) {
                for (int j = 0; j < board.getFieldSize(); j++) {
                    try {
                        if (board.getFieldStatus(i, j) == FieldStatus.NONE) {
                            try {
                                board.setFieldStatus(i, j, FieldStatus.CIRCLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                            best = Math.max(best, minmax(board, depth + 1, false));

                            try {
                                board.setFieldStatus(i, j, FieldStatus.NONE);
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
            for (int i = 0; i < board.getFieldSize(); i++) {
                for (int j = 0; j < board.getFieldSize(); j++) {
                    try {
                        if (board.getFieldStatus(i, j) == NONE) {
                            try {
                                board.setFieldStatus(i, j, CROSS);
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                            best = Math.min(best, minmax(board, depth + 1, true));

                            try {
                                board.setFieldStatus(i, j, NONE);
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
                    if(boardCopy.getFieldStatus(i,j) == NONE) {
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
        if (fieldStatuses.get(0) != NONE && fieldStatuses.get(0) == fieldStatuses.get(1) && fieldStatuses.get(0) == fieldStatuses.get(2)) {
            if (fieldStatuses.get(0) == CIRCLE)
                if (fieldStatuses.get(0) == CIRCLE)
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
        if (fieldStatuses.get(0) != NONE && fieldStatuses.get(0) == fieldStatuses.get(1) && fieldStatuses.get(0) == fieldStatuses.get(2)) {
            if (fieldStatuses.get(0) == CIRCLE)
                if (fieldStatuses.get(0) == CIRCLE)
                    return 10;
                else
                    return -10;
        }

        // Check other
        try {
            if (boardCopy.getFieldStatus(0, 0) != NONE && boardCopy.getFieldStatus(0, 0) == boardCopy.getFieldStatus(1, 1)
                    && boardCopy.getFieldStatus(0, 0) == boardCopy.getFieldStatus(2, 2)) {
                if (boardCopy.getFieldStatus(0, 0) == CIRCLE)
                    return 10;
                else
                    return -10;
            }
        } catch (Exception e) {
            System.out.println("Field not found");
        }

        try {
            if (boardCopy.getFieldStatus(2, 0) != NONE && boardCopy.getFieldStatus(2, 0) == boardCopy.getFieldStatus(1, 1)
                    && boardCopy.getFieldStatus(2, 0) == boardCopy.getFieldStatus(0, 2)) {
                if (boardCopy.getFieldStatus(2, 0) == CIRCLE)
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

