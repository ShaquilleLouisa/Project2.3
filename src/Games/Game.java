package Games;


import AI.AI;
import Exceptions.WrongAIException;
import Model.GameModel;

public abstract class Game {
    AI ai;
    abstract public GameName getId();
    abstract public void setAI(AI ai) throws WrongAIException;
    abstract public int getNextMove();
    abstract public GameModel getModel();
}
