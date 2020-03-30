package Model;

import Exceptions.MoveException;
import Model.TicTacToeItems.FieldStatus;

public abstract class GameModel extends Model {
    abstract public void setFieldStatus(int move) throws MoveException; 
    abstract public <E> E getFieldStatus(int x, int y);
}
