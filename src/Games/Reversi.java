package Games;

import AI.AI;
import AI.ReversiAI;
import Controller.ReversiController;
import Exceptions.WrongAIException;
import Model.ReversiModel;
import View.ReversiView;

public class Reversi extends Game {
    private ReversiView view;
    private ReversiModel model;
    private ReversiController controller;
    private ReversiAI ai;

    public Reversi() {
        controller = new ReversiController();
        view = new ReversiView(controller);
        model = new ReversiModel(view);
        ai = new ReversiAI(model);
        controller.addModel(model);
        controller.addView(view);
    }

    @Override
    public GameName getId() {
        return GameName.REVERSI;
    }

}
