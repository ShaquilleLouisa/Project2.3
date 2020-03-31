package Controller;

import Exceptions.MoveException;
import Model.GameItems.*;

public abstract class GameController extends Controller {
    abstract public void doMove(int move, FieldStatus fieldStatus) throws MoveException;
}
