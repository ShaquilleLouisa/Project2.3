package Model;

import Exceptions.MoveException;
import Model.TicTacToeItems.FieldStatus;

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

    public FieldStatus fieldStatus(int x, int y) {
        return gameModel.getFieldStatus(x, y);
    }
}
