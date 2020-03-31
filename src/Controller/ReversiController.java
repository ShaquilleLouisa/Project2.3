package Controller;

import Exceptions.MoveException;
import Model.Model;
import Model.GameItems.*;
import Model.ReversiModel;
import View.ReversiView;
import View.View;

import java.util.Scanner;

public class ReversiController extends GameController {
    private ReversiModel model;
    private ReversiView view;
    private boolean done = false;

    public ReversiController() {

    }


//      MOVES DRAWN OUT
//    -----------------------------------------------------------------
//    |       |       |       |       |       |       |       |       |
//    |  0,0  |  0,1  |  0,2  |  0,0  |  0,1  |  0,2  |  0,1  |  0,2  |
//    |       |       |       |       |       |       |       |       |
//    -----------------------------------------------------------------
//    |       |       |       |       |       |       |       |       |
//    |  0,0  |  0,1  |  0,2  |  0,0  |  0,1  |  0,2  |  0,1  |  0,2  |
//    |       |       |       |       |       |       |       |       |
//    -----------------------------------------------------------------
// etc...


    public void doMove(int move, FieldStatus Fieldstatus) throws MoveException {
        try {
            model.setFieldStatus(move, Fieldstatus);
        } catch (MoveException e) {
            throw e;
        }
        model.switchPlayer();
    }

    @Override
    public void addView(View view) {
        this.view = (ReversiView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (ReversiModel) model;
    }
}
