package ai;


import ai.AI;
import ai.ai_items.Move;
import model.TicTacToeModel;
import model.gameitems.Board;
import model.gameitems.TicTacToeFieldStatus;

/**
 * @author Anne de Graaff
 * The TicTacToeAI is an AI for the game TicTacToe. This AI uses a min-max algorithm to calculate the best next move for a specific player.
 */
public class TicTacToeAI extends AI {
    private TicTacToeFieldStatus playerToCalc;

    public TicTacToeAI(TicTacToeModel ticTacToeModel, TicTacToeFieldStatus playerToCalc) {
        super(ticTacToeModel);
        this.playerToCalc = playerToCalc;
    }

    /**
     * Calculate next move
     * @param fieldStatus Fieldstatus contains player to calculate move for
     * @return move int
     */
    @Override
    public int calculateNextMove() {
        Board boardCopy = aiModel.getBoard();
        Move move = findBestMove(boardCopy);
        int counter = 0;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (i == move.x && j == move.y) {
                    System.out.println(counter);
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


    /**
     * Find the best move with the current gameboard
     * @param boardCopy board with current gamestatus
     * @return best move Move
     */
    private Move findBestMove(Board boardCopy) {
        Move bestMove = new Move(-1, -1);
        int bestVal = -1000;

        int counter = 0;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                try {
                    TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                    if (boardCopy.getFieldStatus(i, j).isEmpty()) {
                        if (playerToCalc.getID() == 2) {
                            fieldStatus.setCircle();
                        } else {
                            fieldStatus.setCross();
                        }
                        boardCopy.setFieldStatus(i, j, fieldStatus);
                        int moveVal = minmax(boardCopy, 0, false);
                        System.out.println(moveVal);
                        try {
                            fieldStatus.setEmpty();
                            boardCopy.setFieldStatus(i, j, fieldStatus);
                        } catch (Exception e) {
                            System.out.println("OEF");
                        }
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

    /**
     * MinMax algorithm
     * @param board board
     * @param depth depth of algorithm
     * @param isMax if it should min or max
     * @return score int
     */
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
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    try {
                        TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                        if (board.getFieldStatus(i, j).isEmpty()) {
                            if (playerToCalc.getID() == 2) {
                                fieldStatus.setCircle();
                            } else {
                                fieldStatus.setCross();
                            }
                            board.setFieldStatus(i, j, fieldStatus);
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
                            if (playerToCalc.getID() == 2) {
                                try {
                                    fieldStatus.setCross();
                                    board.setFieldStatus(i, j, fieldStatus);
                                } catch (Exception e) {
                                    System.out.println("OEF");
                                }
                            } else {
                                try {
                                    fieldStatus.setCircle();
                                    board.setFieldStatus(i, j, fieldStatus);
                                } catch (Exception e) {
                                    System.out.println("OEF");
                                }
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


    /**
     * Check if there are any moves left
     * @param boardCopy board
     * @return true or false boolean
     */
    private boolean isMovesLeft(Board boardCopy) {
        int fieldSize = boardCopy.getFieldSize();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (boardCopy.getFieldStatus(i, j).isEmpty()) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Check if the game is over.
     * @param boardCopy board
     * @return 10 if player won -10 if enemy won 0 if draw or no winner
     */
    private int evaluate(Board boardCopy) {
        for (int y = 0; y < boardCopy.getFieldSize(); y++) {
            if (boardCopy.getFieldStatus(0, y).getID() == boardCopy.getFieldStatus(1, y).getID() && boardCopy.getFieldStatus(1, y).getID() == boardCopy.getFieldStatus(2, y).getID()) {
                if (boardCopy.getFieldStatus(0, y).getID() == TicTacToeFieldStatus.CIRCLE) {
                    if (playerToCalc.getID() == 2) {
                        return +10;
                    } else {
                        return -10;
                    }
                } else if (boardCopy.getFieldStatus(0, y).getID() == TicTacToeFieldStatus.CROSS) {
                    if (playerToCalc.getID() == 1) {
                        return +10;
                    } else {
                        return -10;
                    }
                }
            }
        }

        for (int x = 0; x < boardCopy.getFieldSize(); x++) {
            if (boardCopy.getFieldStatus(x, 0).getID() == boardCopy.getFieldStatus(x, 1).getID() && boardCopy.getFieldStatus(x, 1).getID() == boardCopy.getFieldStatus(x, 2).getID()) {

                if (boardCopy.getFieldStatus(x, 0).getID() == TicTacToeFieldStatus.CIRCLE) {
                    if (playerToCalc.getID() == 2) {
                        return +10;
                    } else {
                        return -10;
                    }
                } else if (boardCopy.getFieldStatus(x, 0).getID() == TicTacToeFieldStatus.CROSS) {
                    if (playerToCalc.getID() == 1) {
                        return +10;
                    } else {
                        return -10;
                    }
                }
            }
        }

        if (boardCopy.getFieldStatus(0, 0).getID() == boardCopy.getFieldStatus(1, 1).getID() && boardCopy.getFieldStatus(1, 1).getID() == boardCopy.getFieldStatus(2, 2).getID()) {
            if (boardCopy.getFieldStatus(0, 0).getID() == TicTacToeFieldStatus.CIRCLE) {
                if (playerToCalc.getID() == 2) {
                    return +10;
                } else {
                    return -10;
                }
            } else if (boardCopy.getFieldStatus(0, 0).getID() == TicTacToeFieldStatus.CROSS) {
                if (playerToCalc.getID() == 1) {
                    return +10;
                } else {
                    return -10;
                }
            }
        }

        if (boardCopy.getFieldStatus(0, 2).getID() == boardCopy.getFieldStatus(1, 1).getID() && boardCopy.getFieldStatus(1, 1).getID() == boardCopy.getFieldStatus(2, 0).getID()) {
            if (boardCopy.getFieldStatus(0, 2).getID() == TicTacToeFieldStatus.CIRCLE) {
                if (playerToCalc.getID() == 2) {
                    return +10;
                } else {
                    return -10;
                }
            } else if (boardCopy.getFieldStatus(0, 2).getID() == TicTacToeFieldStatus.CROSS) {
                if (playerToCalc.getID() == 1) {
                    return +10;
                } else {
                    return -10;
                }
            }
        }
        return 0;
    }
}

