package AI;
import Model.AIModel;
import Model.GameModel;

public abstract class AI {
    private AIModel aiModel;
    abstract public int calculateNextMove();

    public AI(GameModel gameModel) {
        aiModel = new AIModel(gameModel);
    }
}
