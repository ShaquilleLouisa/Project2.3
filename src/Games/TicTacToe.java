package Games;

import AI.AI;
import Controller.TicTacToeController;
import Exceptions.MoveException;
import Exceptions.WrongAIException;
import Model.TicTacToeModel;
import Model.TicTacToeItems.FieldStatus;
import View.TicTacToeView;
import AI.TicTacToeAI;
import javafx.stage.Stage;

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

    public TicTacToeModel getModel() {
        return model;
    }

    public int getNextMove() {
        return ai.calculateNextMove();
    }

    public FieldStatus getFieldStatus(int move) throws MoveException {
        try {
            return model.getFieldStatus(move);
        } catch (MoveException e) {
            throw (e);
        }
    }

    public void setFieldStatus(int move, FieldStatus fieldStatus) throws MoveException {
        try {
            model.setFieldStatus(move, fieldStatus);
        } catch (MoveException e) {
            throw (e);
        }
    }
}
