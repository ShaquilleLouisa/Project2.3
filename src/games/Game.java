package games;

import ai.AI;
import controller.GameController;
import exceptions.MoveException;
import exceptions.WrongAIException;
import model.GameModel;
import view.GameView;

public abstract class Game {
    AI ai;
    abstract public GameName getId();
    abstract public void setAI(AI ai) throws WrongAIException;
<<<<<<< Updated upstream
    abstract public int getNextMove();
    abstract public void setMove(int move, boolean isOponent);
=======
    abstract public int getNextMove(FieldStatus fieldStatus);
    abstract public void setMove(int move, int player) throws MoveException;
>>>>>>> Stashed changes
    abstract public GameModel getModel();
    abstract public GameView getView();
    abstract public GameController getController();
}
