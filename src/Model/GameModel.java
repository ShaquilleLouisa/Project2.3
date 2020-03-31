package Model;

import Exceptions.MoveException;
import Model.GameItems.*;

public abstract class GameModel extends Model {
    abstract public void setFieldStatus(int move, FieldStatus status) throws MoveException;
    abstract public FieldStatus getFieldStatus(int move);
    abstract public int getFieldSize();
    abstract public Board getBoard();
}
