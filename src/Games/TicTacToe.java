package Games;

import Controller.TicTacToeController;
import Exceptions.MoveException;
import Model.TicTacToeModel;
import View.TicTacToeView;

public class TicTacToe {
    private TicTacToeView view;
    private TicTacToeModel model;
    private TicTacToeController controller;
    public TicTacToe() {
        view = new TicTacToeView();
        model = new TicTacToeModel(view);
        controller = new TicTacToeController(model);
    }

    public void move(int movex, int movey) {
        try {
            controller.doMove(movex, movey);
        } catch (MoveException moveException) {
            System.out.println("move not accepted");
        }

    }
}
