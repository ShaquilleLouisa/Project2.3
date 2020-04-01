package controller;

import exceptions.MoveException;
import model.gameitems.*;

public abstract class GameController extends Controller {
    abstract public void doMove(int move, FieldStatus fieldStatus) throws MoveException;
}
