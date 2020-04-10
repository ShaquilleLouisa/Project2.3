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
    private boolean done;

    public ReversiController() {

    }

    @Override
    public void doMove(int move, FieldStatus fieldStatus) {
        ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus) fieldStatus;
        reversiFieldStatus.setId(fieldStatus.getID());
        while (!model.isFirstMovesSet()) {
            System.out.println("Waiting on view");
        }
        model.flipBoard(move, reversiFieldStatus);
        try {
            model.setFieldStatus(move, fieldStatus);
        } catch (MoveException e) {
            e.printStackTrace();
        }

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
//        model.getValidMoves();
//        ArrayList<Integer> validMovesArray = new ArrayList<>();
//        boolean[][] validmoves = model.getValidMoves();
//        for (int x = 0; x < 8; x++) {
//            for (int y = 0; y < 8; y++) {
//
//                if (validmoves[x][y]) {
//                    int validMove = 8 * x + y;
//                    validMovesArray.add(validMove);
//                }
//            }
//        }
//        System.out.println("Size: " + validMovesArray.size());
//        System.out.println("Valid moves: ");
//        for (int validMove : validMovesArray) {
//            System.out.print(validMove + " ");
//        }
//        ReversiFieldStatus reversiFieldStatus = new ReversiFieldStatus();
//        OurReversiAI ai = new OurReversiAI(model);
//        if (model.getPlayer() != ReversiFieldStatus.BLACK) {
//            try {
//                reversiFieldStatus.setBLACK();
//                int nextMove = ai.calculateNextMove();
//                if (nextMove >= 0) {
//                    doMove(nextMove, reversiFieldStatus);
//                }
//            } catch (MoveException e) {
//                e.printStackTrace();
//            }
//        } else {
//            reversiFieldStatus.setWHITE();
//            try {
//                reversiFieldStatus.setWHITE();
//                doMove(ai.calculateNextMove(), reversiFieldStatus);
//            } catch (MoveException e) {
//                e.printStackTrace();
//            }
//        }
        //TODO fix nextturn
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
