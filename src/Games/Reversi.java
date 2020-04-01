package games;

import ai.AI;
import ai.ReversiAI;
import controller.ReversiController;
import exceptions.WrongAIException;
import model.ReversiModel;
import view.ReversiView;

public class Reversi extends Game {
    private ReversiView view;
    private ReversiModel model;
    private ReversiController controller;

    public Reversi() {
        controller = new ReversiController();
        view = new ReversiView(controller);
        model = new ReversiModel(view);
        ai = new ReversiAI(model);
        controller.addModel(model);
        controller.addView(view);
    }

    public ReversiModel getModel() {
        return model;
    }

    public int getNextMove() {
        return ai.calculateNextMove();
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
