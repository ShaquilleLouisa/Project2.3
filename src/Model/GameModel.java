package model;

import exceptions.MoveException;
import model.gameitems.*;

public abstract class GameModel extends Model {
    abstract public void setFieldStatus(int move, FieldStatus status) throws MoveException;
    abstract public FieldStatus getFieldStatus(int move);
    abstract public int getFieldSize();
    abstract public Board getBoard();
}
