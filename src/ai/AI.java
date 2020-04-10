package ai;
import model.AIModel;
import model.GameModel;
import model.gameitems.FieldStatus;

public abstract class AI {
    public AIModel aiModel;
    abstract public int calculateNextMove(FieldStatus fieldStatus);

    public AI(GameModel gameModel) {
        aiModel = new AIModel(gameModel);
    }
}
