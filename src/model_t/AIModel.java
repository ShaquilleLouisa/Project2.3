package model;

import exceptions.MoveException;
import model.gameitems.*;

import java.lang.reflect.Field;

public class AIModel extends Model {
    private GameModel gameModel; // Proxy pattern.

    public AIModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void doMove(int move, FieldStatus fieldStatus) throws MoveException {
        try {
            gameModel.setFieldStatus(move, fieldStatus);
        } catch (MoveException e) {
            throw e;
        }
    }

    public int getFieldSize() {
        return gameModel.getFieldSize();
    }

    public FieldStatus getFieldStatus(int move) throws MoveException {
        return gameModel.getFieldStatus(move);
    }

    public Board getBoard() {
        return gameModel.getBoard();
    }
}
