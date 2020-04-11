package games;

import ai.*;
import ai.ai_items.Move;
import controller.GameController;
import controller.ReversiController;
import exceptions.MoveException;
import exceptions.WrongAIException;
import model.GameModel;
import model.ReversiModel;
import model.gameitems.FieldStatus;
import model.gameitems.ReversiFieldStatus;
import view.GameView;
import view.ReversiView;

public class Reversi extends Game {
    private ReversiView view;
    private ReversiModel model;
    private ReversiController controller;
    private AI ai;
    private boolean useAi;
    private boolean online;


    public Reversi(boolean online, boolean useAi, boolean doubleai) {
        controller = new ReversiController();
        view = new ReversiView(controller);
        model = new ReversiModel(view);
        controller.addModel(model);
        controller.addView(view);
        this.useAi = useAi;
        this.online = online;

        model.setOnlineUse(online);
        model.setAiUse(useAi);
        model.setDoubleAi(doubleai);

        if (useAi) {
            ai = new BoardScoreAI(model);
        }
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

    public int getNextMove(FieldStatus fieldStatus) {
        return ai.calculateNextMove(fieldStatus);
    }

    @Override
    public void setMove(int move, int player) throws MoveException {
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        if (player == 1) {
            fieldStatus.setBLACK();
        } else {
            fieldStatus.setWHITE();
        }
        try {
            controller.doMove(move, fieldStatus);
        } catch (MoveException e) {
            throw e;
        }
    }

    @Override
    public GameName getId() {
        return GameName.REVERSI;
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
}
