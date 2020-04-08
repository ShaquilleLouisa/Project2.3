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
        boolean[][] validMoves = aiModel.getGameModel().calculateValidMoves();
        Board boardCopy = aiModel.getBoard();
        HashMap<Integer, Integer> points = new HashMap<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        int counter = 0;
        for(int i = 0; i < boardCopy.getFieldSize(); i++) {
            for(int j = 0; j < boardCopy.getFieldSize(); j++) {
                if(validMoves[i][j]) {
                    System.out.println("VALID MOVE GEVONDEN!!!!");
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

                    if (i <= 1 && j <= 1 && !(i == 0 && j == 0) && (boardCopy.getFieldStatus(0,0).getID() != 0)) {
                        singlePoint -= 5;
                    }

                    if (i >= boardCopy.getFieldSize() - 2 && j <= 1 && (!(i == boardCopy.getFieldSize() - 1 && j == 0)  && (boardCopy.getFieldStatus(7,0).getID() != 0))) {
                        singlePoint -= 5;
                    }

                    if (i <= 1 && j >= boardCopy.getFieldSize() - 2 && (!(i == 0 && j == boardCopy.getFieldSize() - 1) && (boardCopy.getFieldStatus(0,7).getID() != 0))) {
                        singlePoint -= 5;
                    }

                    if (i >= boardCopy.getFieldSize() - 2 && j >= boardCopy.getFieldSize() - 2 && (!(i == boardCopy.getFieldSize() - 1 && j == boardCopy.getFieldSize() - 1)  && (boardCopy.getFieldStatus(7,7).getID() != 0))) {
                        singlePoint -= 5;
                    }
                    points.put(counter, singlePoint);
                    indexes.add(counter);
                }
                counter++;
            }
        }
        System.out.println("hier ben ik");
        int size = -1000;
        int key = -1;
        for(int index:indexes) {
            if(points.get(index) > size) {
                size = points.get(index);
                key = index;
            }
        }

        if(key == -1) {
            System.out.println("GEEN MOVES GEVONDEN");
            boolean[][] moreValidMove = aiModel.getAnotherOne();
            int counterM = 0;
            for (int i = 0; i < boardCopy.getFieldSize(); i++) {
                for (int j = 0; j < boardCopy.getFieldSize(); j++) {
                    if (moreValidMove[i][j]) {
                        return counterM;
                    } else {
                        counterM++;
                    }
                }
            }
        }

        return key;
    }
}
