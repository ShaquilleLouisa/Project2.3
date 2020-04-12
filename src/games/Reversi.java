package games;

import ai.*;
import ai.ai_items.Move;
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
<<<<<<< Updated upstream
=======

        if (useAi) {
            ai = new BoardScoreAI(model);
        }
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public void setMove(int move, boolean isOponent) {
=======
    @Override
    public void setMove(int move, int player) throws MoveException {
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        isFirstMove = false;
=======
        try {
            controller.doMove(move, fieldStatus);
        } catch (MoveException e) {
            throw e;
        }
>>>>>>> Stashed changes
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
