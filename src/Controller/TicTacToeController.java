package Controller;

import Exceptions.MoveException;
import Model.Model;
import Model.TicTacToeItems.FieldStatus;
import Model.TicTacToeModel;
import View.TicTacToeView;
import View.View;

import java.util.Scanner;

public class TicTacToeController extends GameController {
    private TicTacToeModel model;
    private TicTacToeView view;
    private boolean done = false;

    public TicTacToeController() {

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
    public void doMove(int move) throws MoveException {
        try {
            model.setFieldStatus(move);
        } catch (MoveException e) {
            throw e;
        }
        model.switchPlayer();
    }

    @Override
    public void addView(View view) {
        this.view = (TicTacToeView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (TicTacToeModel) model;
    }
}
