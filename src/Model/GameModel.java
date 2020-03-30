package Model;

import Exceptions.MoveException;

public abstract class GameModel extends Model {
    abstract public void setFieldStatus(int move) throws MoveException; 
    abstract public <E> E getFieldStatus(int x, int y);
}
