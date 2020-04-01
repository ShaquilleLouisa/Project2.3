package games;

import ai.AI;
import controller.TicTacToeController;
import exceptions.MoveException;
import exceptions.WrongAIException;
import model.TicTacToeModel;
import model.gameitems.*;
import view.TicTacToeView;
import ai.TicTacToeAI;
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

    public FieldStatus getFieldStatus(int move) {
        return model.getFieldStatus(move);
    }

    public void setFieldStatus(int move, FieldStatus fieldStatus) throws MoveException {
        try {
            model.setFieldStatus(move, fieldStatus);
        } catch (MoveException e) {
            throw (e);
        }
    }
}
