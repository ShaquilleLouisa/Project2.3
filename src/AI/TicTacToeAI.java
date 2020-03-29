package AI;

import Games.TicTacToe;
import Model.AIModel;
import Model.TicTacToeItems.Board;
import Model.TicTacToeItems.FieldStatus;
import Model.TicTacToeModel;

import java.util.ArrayList;
import java.util.HashMap;

import static Model.TicTacToeItems.FieldStatus.CIRCLE;
import static Model.TicTacToeItems.FieldStatus.NONE;

public class TicTacToeAI extends AI {
    public TicTacToeAI(TicTacToeModel ticTacToeModel) {
        super(ticTacToeModel);
    }

    @Override
    public int calculateNextMove() {
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
        Board board = new Board(3, 3);
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
                            board.setFieldStatus(i, j, CIRCLE);
                        } catch (Exception e) {
                            System.out.println("OEF");
                        }

                        int moveVal = minmax(board, 0, false);

                        try {
                            board.setFieldStatus(i, j, NONE);
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
            }
        }

        System.out.println("Best move is " + bestVal);
        return bestMove;
    }

    private int minmax(Board board, int depth, boolean isMax) {
        int score = evaluate(board);
        if (score == 10) {
            return score;
        }

        if (score == -10) {
            return score;
        }

        if (!isMovesLeft(board)) {
            return 0;
        }

        if (isMax) {
            int best = -1000;

            for (int i = 0; i < aiModel.getFieldSize(); i++) {
                for (int j = 0; j < aiModel.getFieldSize(); j++) {
                    try {
                        if (board.getFieldStatus(i, j) == FieldStatus.NONE) {
                            try {
                                board.setFieldStatus(i, j, FieldStatus.CIRCLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                            best = Math.max(best, minmax(board, depth + 1, !isMax));

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
            for (int i = 0; i < aiModel.getFieldSize(); i++) {
                for (int j = 0; j < aiModel.getFieldSize(); j++) {
                    try {
                        if (board.getFieldStatus(i, j) == NONE) {
                            try {
                                board.setFieldStatus(i, j, CIRCLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getMessage();
                                System.out.println("OEF");
                            }
                            best = Math.min(best, minmax(board, depth + 1, !isMax));

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

    private boolean isMovesLeft(Board board) {
        int fieldSize = board.getFieldSize();
        int count;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                try {
                    return (aiModel.getFieldStatus(count) == NONE);
                    // if (aiModel.fieldStatus(i, j) == NONE) {
                    //     return true;
                } catch (Exception e) {
                }
            }
        }
        count++;
        return false;
    }

    private int evaluate(Board board) {
        // Check horizontal
        for (int i = 0; i < 3; i++) {
            ArrayList<FieldStatus> fieldStatuses = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                try {
                    fieldStatuses.add(board.getFieldStatus(i, j));
                } catch (Exception e) {
                }
                if (fieldStatuses.get(0) != NONE && fieldStatuses.get(0) == fieldStatuses.get(1)
                        && fieldStatuses.get(0) == fieldStatuses.get(2)) {
                    if (fieldStatuses.get(0) == CIRCLE)
                        if (fieldStatuses.get(0) == CIRCLE)
                            return 10;
                        else
                            return -10;
                }
            }

            // Check vertical
            for (int j = 0; j < 3; j++) {
                ArrayList<FieldStatus> fieldStatuses = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    try {
                        fieldStatuses.add(board.getFieldStatus(i, j));
                catch(Exception e){
                        }
                    }
                    if (fieldStatuses.get(0) != NONE && fieldStatuses.get(0) == fieldStatuses.get(1)
                            && fieldStatuses.get(0) == fieldStatuses.get(2)) {
                        if (fieldStatuses.get(0) == CIRCLE)
                            return 10;
                        else
                            return -10;
                    }
                }

                // Check other
                if (board.getFieldStatus(0, 0) != NONE && board.getFieldStatus(0, 0) == board.getFieldStatus(1, 1)
                        && board.getFieldStatus(0, 0) == board.getFieldStatus(2, 2)) {
                    if (board.getFieldStatus(0, 0) == CIRCLE)
                        return 10;
                    else
                        return -10;
                }
                if (board.getFieldStatus(2, 0) != NONE && board.getFieldStatus(2, 0) == board.getFieldStatus(1, 1)
                        && board.getFieldStatus(2, 0) == board.getFieldStatus(0, 2)) {
                    if (board.getFieldStatus(2, 0) == CIRCLE)
                        return 10;
                    else
                        return -10;
                }

                return 0;

            }
        }
