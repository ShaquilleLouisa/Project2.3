package Games;

import Controller.TicTacToeController;
import Model.TicTacToeModel;
import View.TicTacToeView;

public class TicTacToe implements Game {
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
}
