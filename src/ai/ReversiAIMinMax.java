package ai;

import ai.ai_items.Move;
import exceptions.MoveException;
import games.Reversi;
import model.AIModel;
import model.GameModel;
import model.ReversiModel;
import model.gameitems.Board;
import model.gameitems.ReversiFieldStatus;

public class ReversiAIMinMax extends AI implements ReversiAI {
    private int fieldSize = aiModel.getFieldSize();

    public ReversiAIMinMax(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public int calculateNextMove() {
        ReversiModel copyGameModel = (ReversiModel) aiModel.getGameModel();
        try {
            copyGameModel = copyGameModel.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Move move = findBestMove(copyGameModel);
        int counter = 0;

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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


    private Move findBestMove(ReversiModel copyGameModel) {
        Move bestMove = new Move(-1, -1);
        int bestVal = -1000;

        int counter = 0;

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(" "+copyGameModel.getBoard().getFieldStatus(i,j).getID()+" ");
            }
            System.out.println(" ");
        }
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                try {
                    ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
                    if (copyGameModel.getBoard().getFieldStatus(i, j).getID() == -1) {

                        Board copyBoard = new Board(copyGameModel.getBoard().getFieldSize(), copyGameModel.getBoard().getFieldSize(), new ReversiFieldStatus());
                        for (int x = 0; x < fieldSize; x++)
                            for (int y = 0; y < fieldSize; y++)
                                copyBoard.setFieldStatus(i,j,copyGameModel.getBoard().getFieldStatus(i,j));

                        if (aiModel.getPlayer() == 1) {
                            fieldStatus.setBLACK();
                        } else {
                            fieldStatus.setWHITE();
                        }

                        try {
                            copyGameModel.getBoard().setFieldStatus(i, j, fieldStatus);
                        } catch (MoveException e) {
                            e.printStackTrace();
                        }
                        copyGameModel.flipBoard(counter,fieldStatus);
                        int moveVal = minmax(copyGameModel, 0, false);
                        copyGameModel.setBoard(copyBoard);
                        System.out.println(copyGameModel.getBoard().equals(copyBoard) + "KLOPT DIT");
                        if (moveVal > bestVal) {
                            if(aiModel.getValidMoves()[i][j]) {
                                System.out.println(i + " " + j);
                                bestMove.x = i;
                                bestMove.y = j;
                                bestVal = moveVal;
                            }
                        }
                    }
                    counter++;
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                    System.out.println("OEF");
                }
                counter++;
            }
        }
        return bestMove;
    }

    private int minmax(ReversiModel copyGameModel, int depth, boolean isMax) {
        int score = evaluate(copyGameModel.getBoard());

        if (!isMovesLeft(copyGameModel.getBoard())) {
            return score;
        }

        if(depth == 5) {
            return score;
        }

        if (isMax) {
            int best = -1000;
            int counter = 0;
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    try {
                        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
                        if (copyGameModel.getBoard().getFieldStatus(i, j).getID() == -1) {
                            Board copyBoard = new Board(copyGameModel.getBoard().getFieldSize(), copyGameModel.getBoard().getFieldSize(), new ReversiFieldStatus());
                            for (int x = 0; x < fieldSize; x++)
                                for (int y = 0; y < fieldSize; y++)
                                    copyBoard.setFieldStatus(i,j,copyGameModel.getBoard().getFieldStatus(i,j));
                            if (aiModel.getPlayer() == 1) {
                                fieldStatus.setBLACK();
                            } else {
                                fieldStatus.setWHITE();
                            }
                            copyGameModel.getBoard().setFieldStatus(i, j, fieldStatus);
                            copyGameModel.flipBoard(counter,fieldStatus);
                            best = Math.max(best, minmax(copyGameModel, depth + 1, false));
                            copyGameModel.setBoard(copyBoard);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                        System.out.println("OEF");
                    }
                    counter++;
                }
            }
            return best;
        } else {
            int counter = 0;
            int best = 1000;
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    try {
                        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
                        if (copyGameModel.getBoard().getFieldStatus(i, j).getID() == -1) {
                            Board copyBoard = new Board(copyGameModel.getBoard().getFieldSize(), copyGameModel.getBoard().getFieldSize(), new ReversiFieldStatus());
                            for (int x = 0; x < fieldSize; x++)
                                for (int y = 0; y < fieldSize; y++)
                                    copyBoard.setFieldStatus(i,j,copyGameModel.getBoard().getFieldStatus(i,j));
                            if (aiModel.getPlayer() == 1) {
                                fieldStatus.setWHITE();
                            } else {
                                fieldStatus.setBLACK();
                            }
                            copyGameModel.getBoard().setFieldStatus(i,j,fieldStatus);
                            copyGameModel.flipBoard(counter,fieldStatus);
                            best = Math.min(best, minmax(copyGameModel, depth + 1, true));
                            copyGameModel.setBoard(copyBoard);
                        }
                    } catch (Exception e) {
                        System.out.println("Message");
                    }
                    counter++;
                }
            }
            return best;
        }
    }

    private boolean isMovesLeft(Board boardCopy) {
        int fieldSize = boardCopy.getFieldSize();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (boardCopy.getFieldStatus(i, j).getID() == -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private int evaluate(Board boardCopy) {
        int points = 0;
        for(int i = 0; i < boardCopy.getFieldSize(); i++) {
            for(int j = 0; j < boardCopy.getFieldSize(); j++) {
                int fieldStatus = boardCopy.getFieldStatus(i,j).getID();
                if(fieldStatus != -1 && fieldStatus != 0) {
                    if(boardCopy.getFieldStatus(i,j).getID() == aiModel.getPlayer()) {
                        if (i == 0 && j == 0) {
                            points += 10;
                        }

                        if (i == boardCopy.getFieldSize() - 1 && j == 0) {
                            points += 10;
                        }

                        if (i == 0 && j == boardCopy.getFieldSize() - 1) {
                            points += 10;
                        }

                        if (i == boardCopy.getFieldSize() - 1 && j == boardCopy.getFieldSize() - 1) {
                            points += 10;
                        }

                        if (i == 2 && j == 2) {
                            points += 5;
                        }

                        if (i == boardCopy.getFieldSize() - 3 && j == 2) {
                            points += 5;
                        }

                        if (i == 2 && j == boardCopy.getFieldSize() - 3) {
                            points += 5;
                        }

                        if (i == boardCopy.getFieldSize() - 3 && j == boardCopy.getFieldSize() - 3) {
                            points += 5;
                        }

                        if (i == 0) {
                            points += 2;
                        }

                        if (j == 0) {
                            points += 2;
                        }

                        if (i == boardCopy.getFieldSize() - 1) {
                            points += 2;
                        }

                        if (j == boardCopy.getFieldSize() - 1) {
                            points += 2;
                        }

                        if (i <= 1 && j <= 1 && !(i == 0 && j == 0)) {
                            points -= 20;
                        }

                        if (i >= boardCopy.getFieldSize() - 2 && j <= 1 && !(i == boardCopy.getFieldSize() - 1 && j == 0)) {
                            points -= 20;
                        }

                        if (i <= 1 && j >= boardCopy.getFieldSize() - 2 && !(i == 0 && j == boardCopy.getFieldSize() - 1)) {
                            points -= 20;
                        }

                        if (i >= boardCopy.getFieldSize() - 2 && j >= boardCopy.getFieldSize() - 2 && !(i == boardCopy.getFieldSize() - 1 && j == boardCopy.getFieldSize() - 1)) {
                            points -= 20;
                        }
                    } else {
                        if(boardCopy.getFieldStatus(i,j).getID() != aiModel.getPlayer()) {
                            if (i == 0 && j == 0) {
                                points -= 10;
                            }

                            if (i == boardCopy.getFieldSize() - 1 && j == 0) {
                                points -= 10;
                            }

                            if (i == 0 && j == boardCopy.getFieldSize() - 1) {
                                points -= 10;
                            }

                            if (i == boardCopy.getFieldSize() - 1 && j == boardCopy.getFieldSize() - 1) {
                                points -= 10;
                            }

                            if (i == 2 && j == 2) {
                                points -= 5;
                            }

                            if (i == boardCopy.getFieldSize() - 3 && j == 2) {
                                points -= 5;
                            }

                            if (i == 2 && j == boardCopy.getFieldSize() - 3) {
                                points -= 5;
                            }

                            if (i == boardCopy.getFieldSize() - 3 && j == boardCopy.getFieldSize() - 3) {
                                points -= 5;
                            }

                            if (i == 0) {
                                points -= 2;
                            }

                            if (j == 0) {
                                points -= 2;
                            }

                            if (i == boardCopy.getFieldSize() - 1) {
                                points -= 2;
                            }

                            if (j == boardCopy.getFieldSize() - 1) {
                                points -= 2;
                            }

                            if (i <= 1 && j <= 1 && !(i == 0 && j == 0)) {
                                points += 20;
                            }

                            if (i >= boardCopy.getFieldSize() - 2 && j <= 1 && !(i == boardCopy.getFieldSize() - 1 && j == 0)) {
                                points += 20;
                            }

                            if (i <= 1 && j >= boardCopy.getFieldSize() - 2 && !(i == 0 && j == boardCopy.getFieldSize() - 1)) {
                                points += 20;
                            }

                            if (i >= boardCopy.getFieldSize() - 2 && j >= boardCopy.getFieldSize() - 2 && !(i == boardCopy.getFieldSize() - 1 && j == boardCopy.getFieldSize() - 1)) {
                                points += 20;
                            }
                        }
                    }
                }
            }
        }
        return points;
    }
}
