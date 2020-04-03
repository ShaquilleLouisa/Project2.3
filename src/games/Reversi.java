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
    private boolean isFirstMove = true;

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
        if (isFirstMove) {
            fieldStatus.setWhite();
        } else {
            fieldStatus.setBlack();
        }
        try {
            model.setFieldStatus(move, fieldStatus);
            model.flipBoard(move, fieldStatus);
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
