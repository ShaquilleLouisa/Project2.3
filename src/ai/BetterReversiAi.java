package ai;

import model.GameModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BetterReversiAi extends AI implements ReversiAI {
    public BetterReversiAi(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public int calculateNextMove(int player) {
        boolean[][] validMoves = aiModel.calculateValidMove(player);
        boolean done = false;
        boolean skipped = false;
        int skippedCount = 0;
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
                if (!skipped) {
                    validMoves = aiModel.getAnotherOne();
                    System.out.print("Handled skipped turn");
                    skipped = true;
                } else {
                    //No more turns left because end of game
                    return -1;
                }
            } else {
                done = true;
            }

        }
        HashMap<Integer, Integer> points = new HashMap<>();
        for (Integer moves : validMovesArray) {
            int x = moves / 8;
            int y = moves % 8;
            int singlePoint = 1;
            if (x == 0 && y == 0) {
                singlePoint += 20;
            }

            if (x == aiModel.getBoard().getFieldSize() - 1 && y == 0) {
                singlePoint += 20;
            }

            if (x == 0 && y == aiModel.getBoard().getFieldSize() - 1) {
                singlePoint += 20;
            }

            if (x == aiModel.getBoard().getFieldSize() - 1 && y == aiModel.getBoard().getFieldSize() - 1) {
                singlePoint += 20;
            }

            if (x == 2 && y == 2) {
                singlePoint += 3;
            }

            if (x == aiModel.getBoard().getFieldSize() - 3 && y == 2) {
                singlePoint += 3;
            }

            if (x == 2 && y == aiModel.getBoard().getFieldSize() - 3) {
                singlePoint += 3;
            }

            if (x == aiModel.getBoard().getFieldSize() - 3 && y == aiModel.getBoard().getFieldSize() - 3) {
                singlePoint += 3;
            }

            if (x == 0) {
                singlePoint += 8;
            }

            if (y == 0) {
                singlePoint += 8;
            }

            if (x == aiModel.getBoard().getFieldSize() - 1) {
                singlePoint += 8;
            }

            if (y == aiModel.getBoard().getFieldSize() - 1) {
                singlePoint += 8;
            }

            if (x <= 1 && y <= 1 && !(x == 0 && y == 0) && (aiModel.getBoard().getFieldStatus(0,0).getID() != 0)) {
                singlePoint -= 10;
            }

            if (x >= aiModel.getBoard().getFieldSize() - 2 && y <= 1 && (!(x == aiModel.getBoard().getFieldSize() - 1 && y == 0)  && (aiModel.getBoard().getFieldStatus(7,0).getID() != 0))) {
                singlePoint -= 10;
            }

            if (x <= 1 && y >= aiModel.getBoard().getFieldSize() - 2 && (!(x == 0 && y == aiModel.getBoard().getFieldSize() - 1) && (aiModel.getBoard().getFieldStatus(0,7).getID() != 0))) {
                singlePoint -= 10;
            }

            if (x >= aiModel.getBoard().getFieldSize() - 2 && y >= aiModel.getBoard().getFieldSize() - 2 && (!(x == aiModel.getBoard().getFieldSize() - 1 && y == aiModel.getBoard().getFieldSize() - 1)  && (aiModel.getBoard().getFieldStatus(7,7).getID() != 0))) {
                singlePoint -= 10;
            }
            points.put(moves, singlePoint);
            System.out.println("Move " + moves + " " + singlePoint + " points");
        }

        int max = -999;
        int bestMove = -1;
        for(Map.Entry<Integer, Integer> point : points.entrySet()) {
            Integer move = point.getKey();
            Integer movePoints = point.getValue();

            if(movePoints > max) {
                bestMove = move;
                max = movePoints;
            }
        }

        return bestMove;
    }

    @Override
    public int calculateNextMove() {
        return 0;
    }
}
