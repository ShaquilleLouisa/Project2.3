package AI;
import Model.AIModel;
import Model.GameModel;

public abstract class AI {
    private AIModel model;
    abstract public int calculateNextMove();

    public AI(GameModel gameModel) {
        model = new AIModel(gameModel);
    }
}
