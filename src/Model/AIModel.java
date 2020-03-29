package Model;

import Exceptions.MoveException;
import Model.TicTacToeItems.Board;
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

    public FieldStatus getFieldStatus(int move) throws MoveException {
        try {
            return gameModel.getFieldStatus(move);
        } catch (MoveException e) {
            throw e;
        }
    }

    public Board getBoard() {
        return gameModel.getBoard();
    }
}
