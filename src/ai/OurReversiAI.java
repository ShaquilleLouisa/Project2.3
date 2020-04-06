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
        boolean[][] validMoves = aiModel.getValidMoves();
        ArrayList<Integer> validMovesArray = new ArrayList<>();
        //System.out.println("number of valid moves: " + validMovesArray.size());
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
        int rnd = new Random().nextInt(validMovesArray.size());
        System.out.println("Did " + validMovesArray.get(rnd));
        for(Integer moves:validMovesArray) {
            System.out.println(moves);
        }
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return validMovesArray.get(rnd);
    }
}
