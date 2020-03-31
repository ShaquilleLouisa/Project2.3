package Model;

import Exceptions.MoveException;
import Model.GameItems.FieldStatus;

public class AIModel extends Model {
    private GameModel gameModel; // Proxy pattern.

    public AIModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void doMove(int move) throws MoveException {
        try {
            gameModel.setFieldStatus(move);
        } catch (MoveException e) {
            throw e;
        }
    }

    public FieldStatus getFieldStatus(int x, int y) {
        return gameModel.getFieldStatus(x, y);
    }
}
