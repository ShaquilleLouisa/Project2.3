package controller;

import exceptions.MoveException;
import model.Model;
import model.gameitems.*;
import view.ReversiView;
import view.View;
import model.ReversiModel;

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
    public boolean isDone() {
        return done;
    }

    @Override
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public void notifyDone() {
        setDone(true);
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
