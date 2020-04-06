package controller;

import ai.OurReversiAI;
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
    public static boolean isFirstMove = true;

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
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
//        if(isFirstMove && isOponent){
//            model.switchPlayer();
//        }
//      if(isOponent){
//          model.flipBoard(move, fieldStatus);
//          model.setFieldStatus(move, fieldStatus);
//      }

        if (isFirstMove) {
            fieldStatus.setWhite();
        } else if (model.getPlayer()== 2) {
            fieldStatus.setBlack();
        }else {
            fieldStatus.setWhite();
        }
        try{
// De correcte stenen worden geflipt op het bord
            model.flipBoard(move, fieldStatus);
            model.switchPlayer();
// De zet wordt gedaan op het model
            model.setFieldStatus(move, fieldStatus);
// Het model wordt geswitched van speler
        } catch (MoveException e) {
        }
        isFirstMove = false;
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

    //OFFLINE START GAME
    @Override
    public void nextTurn() {
        ReversiFieldStatus reversiFieldStatus = new ReversiFieldStatus();

        OurReversiAI ai = new OurReversiAI(model);
        if (model.getPlayer() != ReversiFieldStatus.WHITE) {
            try {
                reversiFieldStatus.setWhite();
                int nextMove = ai.calculateNextMove();
                if(nextMove >= 0) {
                    doMove(nextMove, reversiFieldStatus);
                }
            } catch (MoveException e) {
                e.printStackTrace();
            }
        } else {
            reversiFieldStatus.setBlack();
            try {
                reversiFieldStatus.setBlack();
                doMove(ai.calculateNextMove(), reversiFieldStatus);
            } catch (MoveException e) {
                e.printStackTrace();
            }
        }
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
