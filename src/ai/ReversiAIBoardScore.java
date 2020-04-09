package ai;


import model.GameModel;
import model.gameitems.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ReversiAIBoardScore extends AI implements ReversiAI {

    public ReversiAIBoardScore(GameModel gameModel) {
        super(gameModel);
    }


    @Override
    public int calculateNextMove() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean[][] validMoves = aiModel.getGameModel().calculateValidMoves();
        Board boardCopy = aiModel.getBoard();
        HashMap<Integer, Integer> points;
        ArrayList<Integer> indexes;
        int counter;
        boolean done = false;
        int key = -1;
        boolean hasSkipped = false;
        while (!done) {
            counter = 0;
            points = new HashMap<>();
            indexes = new ArrayList<>();
            for (int i = 0; i < boardCopy.getFieldSize(); i++) {
                for (int j = 0; j < boardCopy.getFieldSize(); j++) {
                    if (validMoves[i][j]) {
                        System.out.println((i * 8) + j);
                        int singlePoint = 1;
                        if (i == 0 && j == 0) {
                            singlePoint += 5;
                        }

                        if (i == boardCopy.getFieldSize() - 1 && j == 0) {
                            singlePoint += 5;
                        }

                        if (i == 0 && j == boardCopy.getFieldSize() - 1) {
                            singlePoint += 5;
                        }

                        if (i == boardCopy.getFieldSize() - 1 && j == boardCopy.getFieldSize() - 1) {
                            singlePoint += 5;
                        }

                        if (i == 2 && j == 2) {
                            singlePoint += 3;
                        }

                        if (i == boardCopy.getFieldSize() - 3 && j == 2) {
                            singlePoint += 3;
                        }

                        if (i == 2 && j == boardCopy.getFieldSize() - 3) {
                            singlePoint += 3;
                        }

                        if (i == boardCopy.getFieldSize() - 3 && j == boardCopy.getFieldSize() - 3) {
                            singlePoint += 3;
                        }

                        if (i == 0) {
                            singlePoint += 2;
                        }

                        if (j == 0) {
                            singlePoint += 2;
                        }

                        if (i == boardCopy.getFieldSize() - 1) {
                            singlePoint += 2;
                        }

                        if (j == boardCopy.getFieldSize() - 1) {
                            singlePoint += 2;
                        }

                        if (i <= 1 && j <= 1 && !(i == 0 && j == 0) && (boardCopy.getFieldStatus(0, 0).getID() != 0)) {
                            singlePoint -= 5;
                        }

                        if (i >= boardCopy.getFieldSize() - 2 && j <= 1 && (!(i == boardCopy.getFieldSize() - 1 && j == 0) && (boardCopy.getFieldStatus(7, 0).getID() != 0))) {
                            singlePoint -= 5;
                        }

                        if (i <= 1 && j >= boardCopy.getFieldSize() - 2 && (!(i == 0 && j == boardCopy.getFieldSize() - 1) && (boardCopy.getFieldStatus(0, 7).getID() != 0))) {
                            singlePoint -= 5;
                        }

                        if (i >= boardCopy.getFieldSize() - 2 && j >= boardCopy.getFieldSize() - 2 && (!(i == boardCopy.getFieldSize() - 1 && j == boardCopy.getFieldSize() - 1) && (boardCopy.getFieldStatus(7, 7).getID() != 0))) {
                            singlePoint -= 5;
                        }
                        points.put(counter, singlePoint);
                        indexes.add(counter);
                    }
                    counter++;
                }
            }
            int size = -1000;
            key = -1;
            for (int index : indexes) {
                if (points.get(index) > size) {
                    size = points.get(index);
                    key = index;
                }
            }


            if (key == -1) {
//                if(againCounter > 2) {
////                    done = true;
////                }
////                else if(againCounter > 1) {
////                    //validMoves = aiModel.getAnotherOne();
////                    againCounter++;
////                } else {
////                    validMoves = aiModel.getValidMoves();
////                    againCounter++;
////                }
                //System.out.println("WTF!!!");
                validMoves = aiModel.getGameModel().calculateValidMoves();
            } else {
                done = true;
            }
        }
        System.out.println("DOING " + key + " as " + ((aiModel.getPlayer() == 1) ? "BLACK" : "WHITE"));
        return key;
    }
}
