package Games;


import AI.AI;
import Exceptions.WrongAIException;

public abstract class Game {
    AI ai;
    abstract public GameName getId();
    abstract public void setAI(AI ai) throws WrongAIException;
}
