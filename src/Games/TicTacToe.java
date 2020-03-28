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


    public TicTacToe() {
        controller = new TicTacToeController();
        view = new TicTacToeView(controller);
        model = new TicTacToeModel(view);
        controller.addModel(model);
        controller.addView(view);
    }

    @Override
    public GameName getId() {
        return GameName.TICTACTOE;
    }

    @Override
    public void setAI(AI ai) throws WrongAIException {
        if (ai instanceof TicTacToeAI) {
            this.ai = ai;
        } else {
            throw new WrongAIException("Tic-tac-toe AI required");
        }
    }
}
