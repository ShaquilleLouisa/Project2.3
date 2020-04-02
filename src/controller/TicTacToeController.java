package controller;

import exceptions.MoveException;
import model.Model;
import model.gameitems.*;
import model.TicTacToeModel;
import view.TicTacToeView;
import view.View;
import javafx.stage.Stage;

import java.util.Scanner;

public class TicTacToeController extends GameController {
    private TicTacToeModel model;
    private TicTacToeView view;
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
        model.switchPlayer();
    }

    //FOR OFFLINE GAME
    public void doMove(int move) throws MoveException {
        if(model.checkEnd(model.getBoard()) == -1) {
            TicTacToeFieldStatus ticTacToeFieldStatus = new TicTacToeFieldStatus();
            if (model.getPlayer() == 1) {
                ticTacToeFieldStatus.setCross();
            } else {
                ticTacToeFieldStatus.setCircle();
            }
            try {
                doMove(move, ticTacToeFieldStatus);
            } catch (MoveException e) {
                throw e;
            }
            if(model.checkEnd(model.getBoard()) != -1) {
                String winner;
                if(model.checkEnd(model.getBoard()) == 1) {
                    winner = "Cross";
                } else {
                    winner = "Circle";
                }
                view.updateNotification("Player with " + winner + " has won");
            }
        }
    }


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
}
