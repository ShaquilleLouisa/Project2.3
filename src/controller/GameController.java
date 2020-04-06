package controller;

import exceptions.MoveException;
import model.gameitems.*;

public abstract class GameController extends Controller {
    private boolean done;

    abstract public void doMove(int move, FieldStatus fieldStatus) throws MoveException;
    abstract public boolean isDone();
    abstract public void setDone(boolean done);
    abstract public void notifyDone();
    abstract public void nextTurn();
}
