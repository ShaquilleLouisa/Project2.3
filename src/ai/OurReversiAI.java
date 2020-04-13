package ai;

import ai.ai_items.Move;
import games.Reversi;
import model.GameModel;
import model.ReversiModel;
import model.gameitems.Board;
import model.gameitems.FieldStatus;

import java.util.Random;

import java.util.ArrayList;

/**
 * An AI that is mostly used for testing purpouses. It will choose a random valid move.
 */
public class OurReversiAI extends AI implements ReversiAI {

    public OurReversiAI(ReversiModel model) {
        super(model);
    }

    /**
     * Calculate next move for player
     * @param fieldStatus Fieldstatus contains player to calculate move for
     * @return move int
     */
    @Override
    public int calculateNextMove(FieldStatus fieldStatus) {
        boolean[][] validMoves = aiModel.calculateValidMoves(fieldStatus);
        boolean done = false;
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
        int rnd = new Random().nextInt(validMovesArray.size());
        //System.out.println("Did " + validMovesArray.get(rnd));
        for (Integer moves : validMovesArray) {
            //System.out.println(moves);
        }
        return validMovesArray.get(rnd);

    }
}
