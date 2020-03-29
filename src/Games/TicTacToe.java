package Games;

import AI.AI;
import Controller.TicTacToeController;
import Exceptions.WrongAIException;
import Model.TicTacToeModel;
import View.TicTacToeView;
import AI.TicTacToeAI;

public class TicTacToe extends Game {
    private TicTacToeView view;
    private TicTacToeModel model;
    private TicTacToeController controller;
    private TicTacToeAI ai;

    public TicTacToe() {
        controller = new TicTacToeController();
        view = new TicTacToeView(controller);
        model = new TicTacToeModel(view);
        ai = new TicTacToeAI(model);
        controller.addModel(model);
        controller.addView(view);
    }

    @Override
    public GameName getId() {
        return GameName.TICTACTOE;
    }

}
