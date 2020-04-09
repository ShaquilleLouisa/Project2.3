package controller;

import ai.OurReversiAI;
import exceptions.MoveException;
import model.Model;
import model.gameitems.*;
import view.ReversiView;
import view.View;
import model.ReversiModel;

import java.util.ArrayList;
import java.util.Scanner;

public class ReversiController extends GameController {
    private ReversiModel model;
    private ReversiView view;
    private boolean done = false;
    boolean pauze = false;
    public static boolean isFirstMove = true;

    public ReversiController() {

    }


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


    public void doMove(int move, FieldStatus Fieldstatus, int player) throws MoveException {
        ReversiFieldStatus fieldStatus = new ReversiFieldStatus();
//        if(isFirstMove && isOponent){
//            model.switchPlayer();
//        }
//      if(isOponent){
//          model.flipBoard(move, fieldStatus);
//          model.setFieldStatus(move, fieldStatus);
//      }

        if (player == 1) {
            fieldStatus.setBLACK();
        } else {
            fieldStatus.setWHITE();
        }
        try {
// De correcte stenen worden geflipt op het bord
            model.setFieldStatus(move, fieldStatus);
            model.flipBoard(move, fieldStatus, player);
// De zet wordt gedaan op het model
// Het model wordt geswitched van speler
        } catch (MoveException e) {
        }
        isFirstMove = false;
    }

    @Override
    public void doMove(int move, FieldStatus fieldStatus) throws MoveException {

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
        model.getValidMoves();
        ArrayList<Integer> validMovesArray = new ArrayList<>();
        boolean[][] validmoves = model.getValidMoves();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                if (validmoves[x][y]) {
                    int validMove = 8 * x + y;
                    validMovesArray.add(validMove);
                }
            }
        }
        System.out.println("Size: " + validMovesArray.size());
        System.out.println("Valid moves: ");
        for (int validMove : validMovesArray) {
            System.out.print(validMove + " ");
        }
        if (!pauze) {
            ReversiFieldStatus reversiFieldStatus = new ReversiFieldStatus();
            OurReversiAI ai = new OurReversiAI(model);
            if (model.getPlayer() != ReversiFieldStatus.BLACK) {
                try {
                    reversiFieldStatus.setBLACK();
                    int nextMove = ai.calculateNextMove();
                    if (nextMove >= 0) {
                        doMove(nextMove, reversiFieldStatus);
                    }
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            } else {
                reversiFieldStatus.setWHITE();
                try {
                    reversiFieldStatus.setWHITE();
                    doMove(ai.calculateNextMove(), reversiFieldStatus);
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addView(View view) {
        this.view = (ReversiView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (ReversiModel) model;
    }

    public void pauze() {
        if (!pauze) {
            pauze = true;
        } else {
            pauze = false;
        }
    }
}
