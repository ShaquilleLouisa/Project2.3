package ai;

import ai.ai_items.Move;
import games.Reversi;
import model.GameModel;
import model.ReversiModel;
import model.gameitems.Board;
import model.gameitems.FieldStatus;

import java.util.ArrayList;

public class OurReversiAI extends AI implements ReversiAI {

    public OurReversiAI(ReversiModel model) {
        super(model);
    }

    @Override
    public int calculateNextMove() {
        boolean[][] validMoves = aiModel.getValidMoves();
        if (validMoves != null) {
            System.out.println("All valid moves");
            int counter = 0;
            for (boolean[] moveRow : validMoves) {
                for (boolean move : moveRow) {
                    if (move) {
                        System.out.println(counter + "");
                    }
                    counter++;
                }

            }
        }
        if (Reversi.isFirstMove) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return 44;
        }
        return 29;
    }
}
