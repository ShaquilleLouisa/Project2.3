package Controller;

import Exceptions.MoveException;
import Model.TicTacToeItems.FieldStatus;
import Model.TicTacToeModel;
import View.TicTacToeView;

import java.util.Scanner;

public class TicTacToeController extends GameController {
    private TicTacToeModel model;
    private boolean done = false;

    public TicTacToeController(TicTacToeModel model) {
        this.model = model;

    }


//      MOVES DRAWN OUT
//    -------------------------
//    |       |       |       |
//    |  0,0  |  0,1  |  0,2  |
//    |       |       |       |
//    -------------------------
//    |       |       |       |
//    |  1,0  |  1,1  |  1,2  |
//    |       |       |       |
//    -------------------------
//    |       |       |       |
//    |  2,0  |  2,1  |  2,2  |
//    |       |       |       |
//    -------------------------
    public void doMove(int moveX, int moveY) throws MoveException {
        try {
            model.setFieldStatus(moveX, moveY);
        } catch (MoveException e) {
            throw e;
        }
        model.switchPlayer();
    }

}
