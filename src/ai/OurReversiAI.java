package ai;

import ai.ai_items.Move;
import games.Reversi;
import model.GameModel;
import model.ReversiModel;
import model.gameitems.Board;
import model.gameitems.FieldStatus;

import java.util.Random;

import java.util.ArrayList;

public class OurReversiAI extends AI implements ReversiAI {

    public OurReversiAI(ReversiModel model) {
        super(model);
    }

    @Override
    public int calculateNextMove() {
        boolean[][] validMoves = aiModel.getGameModel().calculateValidMoves();
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
        int rnd = new Random().nextInt(validMovesArray.size());
        System.out.println("Did " + validMovesArray.get(rnd));
        for (Integer moves : validMovesArray) {
            System.out.println(moves);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return validMovesArray.get(rnd);

    }
}
