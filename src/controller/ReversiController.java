package controller;

import exceptions.MoveException;
import model.Model;
import model.gameitems.*;
import view.ReversiView;
import view.View;
import model.ReversiModel;

import java.util.Scanner;

public class ReversiController extends GameController {
    private ReversiModel model;
    private ReversiView view;
    private boolean done = false;

    public ReversiController() {

    }

<<<<<<< Updated upstream

//      MOVES DRAWN OUT
//    -----------------------------------------------------------------
//    |       |       |       |       |       |       |       |       |
//    |  0,0  |  0,1  |  0,2  |  0,0  |  0,1  |  0,2  |  0,1  |  0,2  |
//    |       |       |       |       |       |       |       |       |
//    -----------------------------------------------------------------
//    |       |       |       |       |       |       |       |       |
//    |  0,0  |  0,1  |  0,2  |  0,0  |  0,1  |  0,2  |  0,1  |  0,2  |
//    |       |       |       |       |       |       |       |       |
//    -----------------------------------------------------------------
// etc...


    public void doMove(int move, FieldStatus Fieldstatus) throws MoveException {
        try {
            model.setFieldStatus(move, Fieldstatus);
        } catch (MoveException e) {
            throw e;
=======
    @Override
    public void doMove(int move, FieldStatus fieldStatus) throws MoveException {
        boolean isValidMove = false;
        boolean[][] validMoves = model.calculateValidMoves(fieldStatus);
        for(int x = 0; x < model.getBoardSize(); x++) {
            for(int y = 0; y < model.getBoardSize(); y++) {
                if(validMoves[x][y]) {
                    if (move == ((x * 8) + y)) {
                        isValidMove = true;
                    }
                }
            }
        }
        if(isValidMove) {
            view.updateNotification("");
            ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus) fieldStatus;
            reversiFieldStatus.setId(fieldStatus.getID());
            while (!model.isFirstMovesSet()) {
                System.out.println("Waiting on view");
            }
            model.flipBoard(move, reversiFieldStatus);
            try {
                model.setFieldStatus(move, fieldStatus);
            } catch (MoveException e) {
                System.out.println("Cant do move: " + move);
            }
        } else {
            view.updateNotification("That is an illegal move, please try an other place");
            throw new MoveException("Illegal move");
>>>>>>> Stashed changes
        }
        model.switchPlayer();
    }

    public void doMove(int move) {
        model.setUserMove(move);
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public void notifyDone() {
        setDone(true);
    }

    //OFFLINE START GAME
    @Override
    public void nextTurn() {
<<<<<<< Updated upstream
=======
        if(model.getPlayer() == 0) {
            model.setPlayer(1);
        }
        ReversiFieldStatus reversiFieldStatus = new ReversiFieldStatus();
        OurReversiAI ai = new OurReversiAI(model);
        System.out.println(model.getPlayer());
        if (model.getPlayer() == 1) {
            reversiFieldStatus.setBLACK();
            int nextMove = ai.calculateNextMove(reversiFieldStatus);

            try {
                doMove(nextMove, reversiFieldStatus);
            } catch (MoveException e) {
                System.out.println("ILLEGAL MOVE");
            }
            model.setPlayer(2);

        } else {
            reversiFieldStatus.setWHITE();
            try {
                doMove(ai.calculateNextMove(reversiFieldStatus), reversiFieldStatus);
            } catch (MoveException e) {
                System.out.println("ILLEGAL MOVE");
            }
            model.setPlayer(1);
        }
        int end = model.checkEnd(model.getBoard());
        System.out.println(end);
        if(end >= 0) {
            if(end == 0) {
                view.updateNotification("DRAW");
            } else if(end == 1) {
                view.updateNotification("BLACK WON");
            } else if(end == 2) {
                view.updateNotification("WHITE WON");
            }
        }
    }
>>>>>>> Stashed changes

    }

    @Override
    public void addView(View view) {
        this.view = (ReversiView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (ReversiModel) model;
    }
}
