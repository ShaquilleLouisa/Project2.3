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
    private boolean useAi;
    private boolean doubleai;
    private boolean online;
    public static boolean isFirstMove = true;

    public Reversi(boolean online, boolean useAi, boolean doubleai) {
        controller = new ReversiController();
        view = new ReversiView(controller);
        model = new ReversiModel(view);
        controller.addModel(model);
        controller.addView(view);
        this.doubleai = doubleai;
        this.useAi = useAi;
        this.online = online;

        model.setOnlineUse(online);
        model.setAiUse(useAi);
        model.setDoubleAi(doubleai);
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

    public int getNextMove() {
        return ai.calculateNextMove();
    }

    public void setMove(int move, boolean isOponent) {
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
//        if(isFirstMove && isOponent){
//            model.switchPlayer();
//        }
//      if(isOponent){
//          model.flipBoard(move, fieldStatus);
//          model.setFieldStatus(move, fieldStatus);
//      }

        if (isFirstMove) {
            fieldStatus.setWhite();
        } else if (model.getPlayer()== 2) {
            fieldStatus.setBlack();
        }else {
            fieldStatus.setWhite();
        }
        try{
// De correcte stenen worden geflipt op het bord
        model.flipBoard(move, fieldStatus);
        model.switchPlayer();
// De zet wordt gedaan op het model
        model.setFieldStatus(move, fieldStatus);
// Het model wordt geswitched van speler
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
