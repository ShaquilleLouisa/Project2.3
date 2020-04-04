package games;

import ai.AI;
import ai.DefaultReversiAI;
import ai.OurReversiAI;
import ai.ReversiAI;
import controller.GameController;
import controller.ReversiController;
import exceptions.MoveException;
import exceptions.WrongAIException;
import model.ReversiModel;
import model.gameitems.ReversiFieldStatus;
import view.GameView;
import view.ReversiView;

public class Reversi extends Game {
    private ReversiView view;
    private ReversiModel model;
    private ReversiController controller;
    private AI ai;
    public static boolean isFirstMove = true;

    public Reversi() {
        controller = new ReversiController();
        view = new ReversiView(controller);
        model = new ReversiModel(view);
        ai = new OurReversiAI(model);
        controller.addModel(model);
        controller.addView(view);
    }

    public ReversiModel getModel() {
        return model;
    }

    @Override
    public GameView getView() {
        return view;
    }

    @Override
    public GameController getController() {
        return controller;
    }

    public int getNextMove() {
        return ai.calculateNextMove();
    }

    public void setMove(int move, boolean isOponent) {
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
    public GameName getId() {
        return GameName.REVERSI;
    }

    @Override
    public void setAI(AI ai) throws WrongAIException {
        if (ai instanceof ReversiAI) {
            this.ai = ai;
        } else {
            throw new WrongAIException("Reversi AI required");
        }
    }
}
