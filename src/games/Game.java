package games;

import ai.AI;
import exceptions.WrongAIException;
import model.GameModel;

public abstract class Game {
    AI ai;
    abstract public GameName getId();
    abstract public void setAI(AI ai) throws WrongAIException;
    abstract public int getNextMove();
    abstract public void setMove(int move, boolean isOponent);
    abstract public GameModel getModel();
}
