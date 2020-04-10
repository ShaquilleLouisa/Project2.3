package games;

import ai.AI;
import controller.GameController;
import controller.ServerCommunication;
import controller.TicTacToeController;
import exceptions.MoveException;
import exceptions.WrongAIException;
import model.TicTacToeModel;
import model.gameitems.*;
import view.GameView;
import view.TicTacToeView;
import ai.TicTacToeAI;
import javafx.stage.Stage;

public class TicTacToe extends Game {
    private TicTacToeView view;
    private TicTacToeModel model;
    private TicTacToeController controller;
    private AI ai;
    private boolean useAi;
    private boolean doubleai;
    private boolean online;

    public TicTacToe(boolean online, boolean useAi, boolean doubleai) {
        controller = new TicTacToeController();
        view = new TicTacToeView(controller);
        model = new TicTacToeModel(view);
        controller.addModel(model);
        controller.addView(view);
        this.doubleai = doubleai;
        this.useAi = useAi;
        this.online = online;
        model.setOnlineUse(online);
        model.setAiUse(useAi);
        model.setDoubleAi(doubleai);
        if (useAi && !doubleai) {
            TicTacToeFieldStatus ticTacToeFieldStatus = new TicTacToeFieldStatus();
            ticTacToeFieldStatus.setCircle();
            ai = new TicTacToeAI(model, ticTacToeFieldStatus);
        }
        //System.out.println(online + "" + useAi + "" + doubleai);
    }

    @Override
    public GameName getId() {
        return GameName.TICTACTOE;
    }

    @Override
    public void setAI(AI ai) throws WrongAIException {
        if (useAi) {
            if (ai instanceof TicTacToeAI) {
                this.ai = ai;
            } else {
                throw new WrongAIException("Tic-tac-toe AI required");
            }
        } else {
            throw new WrongAIException("No AI chosen on startup");
        }
    }

    public TicTacToeModel getModel() {
        return model;
    }

    public int getNextMove(FieldStatus fieldStatus) {
        return ai.calculateNextMove(fieldStatus);
    }

    public void setMove(int move, int player) {
        TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
        if (player == 1) {
            fieldStatus.setCross();
        } else {
            fieldStatus.setCircle();
        }
        try {
            model.setFieldStatus(move, fieldStatus);
        } catch (MoveException e) {
            e.printStackTrace();
        }

    }

    @Override
    public GameView getView() {
        return view;
    }

    @Override
    public GameController getController() {
        return controller;
    }


    // public FieldStatus getFieldStatus(int move) {
    // return model.getFieldStatus(move);
    // }

    // public void setFieldStatus(int move, FieldStatus fieldStatus) throws
    // MoveException {
    // try {
    // model.setFieldStatus(move, fieldStatus);
    // } catch (MoveException e) {
    // throw (e);
    // }
    // }


    public boolean isUseAi() {
        return useAi;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isDoubleai() {
        return doubleai;
    }
}
