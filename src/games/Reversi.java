package games;

import ai.*;
import controller.GameController;
import controller.ReversiController;
import exceptions.MoveException;
import exceptions.WrongAIException;
import model.ReversiModel;
import model.gameitems.ReversiFieldStatus;
import view.GameView;
import view.ReversiView;

public class Reversi extends Game {
    private ReversiView view;
    private ReversiModel model;
    private ReversiController controller;
    private AI ai;
    private boolean useAi = false;
    private boolean doubleai = false;
    private boolean online = false;
    public static boolean isFirstMove = true;

    public Reversi() {
        controller = new ReversiController();
        view = new ReversiView(controller);
        model = new ReversiModel(view);
        controller.addModel(model);
        controller.addView(view);
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

    @Override
    public void setGameSettings(boolean online, boolean ai, boolean doubleai) {
        model.setAiUse(ai);
        model.setDoubleAi(doubleai);
        model.setOnlineUse(online);
        this.online = online;
        this.useAi = ai;
        this.doubleai = doubleai;
    }


    public int getNextMove() {
        return ai.calculateNextMove();
    }

    public void setMove(int move, boolean isOponent) {
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
        if (isFirstMove) {
            fieldStatus.setWhite();
        } else {
            fieldStatus.setBlack();
        }
        try {
            if(isFirstMove && isOponent){
                model.switchPlayer();
            }
            model.setFieldStatus(move, fieldStatus);
            model.flipBoard(move, fieldStatus);
        } catch (MoveException e) {
        }
        isFirstMove = false;
    }

    @Override
    public GameName getId() {
        return GameName.REVERSI;
    }

    @Override
    public void setAI(AI ai) throws WrongAIException {
        if(useAi) {
            if (ai instanceof TicTacToeAI) {
                this.ai = ai;
            } else {
                throw new WrongAIException("Tic-tac-toe AI required");
            }
        } else {
            throw new WrongAIException("No AI chosen on startup");
        }
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }
}
