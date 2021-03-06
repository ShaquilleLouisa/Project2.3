package controller;

import ai.AI;
import ai.TicTacToeAI;
import exceptions.MoveException;
import model.AIModel;
import model.Model;
import model.gameitems.*;
import model.TicTacToeModel;
import view.TicTacToeView;
import view.View;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.Timer;

public class TicTacToeController extends GameController {
    private TicTacToeModel model;
    private TicTacToeView view;
    private ServerCommunication serverCommunication;
    private boolean done = false;

    public TicTacToeController() {

    }


    //      MOVES DRAWN OUT
//    -------------------------
//    |       |       |       |
//    |  0,0  |  0,1  |  0,2  |
//    |       |       |       |
//    -------------------------
//    |       |       |       |
//    |  1,0  |  1,1  |  1,2  |
//    |       |       |       |
//    -------------------------
//    |       |       |       |
//    |  2,0  |  2,1  |  2,2  |
//    |       |       |       |
//    -------------------------
    public void doMove(int move, FieldStatus status) throws MoveException {
        try {
            model.setFieldStatus(move, status);
        } catch (MoveException e) {
            throw e;
        }
    }

    public void doMove(int move)  {
        TicTacToeFieldStatus ticTacToeFieldStatus = new TicTacToeFieldStatus();
        if (!model.isDoubleAi()) {
            if (!model.isOnline() && !model.isUseAi()) {
                System.out.println("no ai");
                if (model.checkEnd(model.getBoard()) == -1) {
                    if (model.getPlayer() == 1) {
                        ticTacToeFieldStatus.setCross();
                    } else {
                        ticTacToeFieldStatus.setCircle();
                    }
                    try {
                        doMove(move, ticTacToeFieldStatus);
                        model.setPlayer((model.getPlayer() == 1 ? 2 : 1));
                    } catch (MoveException e) {
                        System.out.println("Illegal move");
                    }

                }
            } else if(model.isOnline()) {
                ticTacToeFieldStatus.setCircle();
                try {
                    doMove(move, ticTacToeFieldStatus);
                } catch (MoveException e) {
                    System.out.println("ILLEGAL MOVE");
                }
                model.setUserMove(move);
            } else if(!model.isOnline() && model.isUseAi()) {
                model.setUserMove(move);
            }
        }
    }

    public int getEnd(){ return model.checkEnd(model.getBoard());}




    @Override
    public void addView(View view) {
        this.view = (TicTacToeView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (TicTacToeModel) model;
    }


    public void notifyDone() {
        done = true;
        System.out.println("NOTIFIED");
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void setDone(boolean done) {
        this.done = done;
    }

    public void nextTurn() {
        TicTacToeFieldStatus ticTacToeFieldStatus = new TicTacToeFieldStatus();
        ticTacToeFieldStatus.setCircle();
        TicTacToeAI ticTacToeAICircle = new TicTacToeAI(model, ticTacToeFieldStatus);
        ticTacToeFieldStatus.setCross();
        TicTacToeAI ticTacToeAICross = new TicTacToeAI(model, ticTacToeFieldStatus);
        if (model.getPlayer() == 1) {
            ticTacToeFieldStatus.setCircle();
            try {
                doMove(ticTacToeAICircle.calculateNextMove(ticTacToeFieldStatus), ticTacToeFieldStatus);
            } catch (MoveException e) {
                e.printStackTrace();
            }
            model.setPlayer(2);
        } else {
            ticTacToeFieldStatus.setCross();
            try {
                doMove(ticTacToeAICross.calculateNextMove(ticTacToeFieldStatus), ticTacToeFieldStatus);
            } catch (MoveException e) {
                e.printStackTrace();
            }
            model.setPlayer(1);
        }
    }


}

