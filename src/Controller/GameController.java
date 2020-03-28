package Controller;

import Exceptions.MoveException;

public abstract class GameController extends Controller {
    abstract public void doMove(int move) throws MoveException;
}
