package AI;
import Model.AIModel;
import Model.GameModel;

public abstract class AI {
    public AIModel aiModel;
    abstract public int calculateNextMove();

    public AI(GameModel gameModel) {
        aiModel = new AIModel(gameModel);
    }
}
