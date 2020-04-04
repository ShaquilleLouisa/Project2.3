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
        int counter = 0;
        if (validMoves != null) {


            int rnd = new Random().nextInt(validMoves.length);
            System.out.println("All valid moves: "+ rnd+" number of valid moves: "+validMoves.length);
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y< 8; y++) {
                    //System.out.print(validMoves[x][y]+" ");
                    if(validMoves[x][y]){
                        return counter;
                    }
//                    if (move) {
//                        System.out.println(counter + " is a valid move!");
//
//                    }
                   counter++;

                }
                //System.out.println();

            }

        }
//        if (Reversi.isFirstMove) {
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            return 44;
//        }
        return counter;
    }
}
