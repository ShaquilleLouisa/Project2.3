package ai;

import model.GameModel;
import model.gameitems.FieldStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BoardScoreAI extends AI implements ReversiAI {

    public HashMap<Integer, Integer> movesWithPoints = new HashMap<>();

    public BoardScoreAI(GameModel gameModel) {
        super(gameModel);
        movesWithPoints.put(0, 20);
        movesWithPoints.put(7, 20);
        movesWithPoints.put(56, 20);
        movesWithPoints.put(63, 20);

        movesWithPoints.put(2, 12);
        movesWithPoints.put(5, 12);
        movesWithPoints.put(16, 12);
        movesWithPoints.put(18, 10);
        movesWithPoints.put(21, 10);
        movesWithPoints.put(23, 12);
        movesWithPoints.put(40, 12);
        movesWithPoints.put(42, 10);
        movesWithPoints.put(45, 10);
        movesWithPoints.put(47, 12);
        movesWithPoints.put(58, 12);
        movesWithPoints.put(61, 12);

        movesWithPoints.put(1,-5);
        movesWithPoints.put(6,-5);
        movesWithPoints.put(8,-5);
        movesWithPoints.put(9,-5);
        movesWithPoints.put(14,-5);
        movesWithPoints.put(15,-5);
        movesWithPoints.put(48,-5);
        movesWithPoints.put(49,-5);
        movesWithPoints.put(54,-5);
        movesWithPoints.put(55,-5);
        movesWithPoints.put(57,-5);
        movesWithPoints.put(62,-5);
    }

    @Override
    public int calculateNextMove(FieldStatus fieldStatus) {
        boolean[][] validMoves = aiModel.calculateValidMoves(fieldStatus);
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
                if (!skipped) {
                    validMoves = aiModel.calculateValidMoves(fieldStatus);
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
        for (Integer move : validMovesArray) {
            try {
                int point = movesWithPoints.get(move);
                points.put(move, point);
            } catch (NullPointerException e) {
                points.put(move, 0);
            }


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
}
