package games;

import ai.AI;
import controller.GameController;
import exceptions.WrongAIException;
import model.GameModel;
import model.gameitems.FieldStatus;
import view.GameView;

public abstract class Game {
    AI ai;
    abstract public GameName getId();
    abstract public void setAI(AI ai) throws WrongAIException;
    abstract public int getNextMove(FieldStatus fieldStatus);
    abstract public void setMove(int move, int player);
    abstract public GameModel getModel();
    abstract public GameView getView();
    abstract public GameController getController();
}
