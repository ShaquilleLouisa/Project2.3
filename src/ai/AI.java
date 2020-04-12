package ai;
import model.AIModel;
import model.GameModel;
import model.gameitems.FieldStatus;

/**
 * Abstract class AI
 */
public abstract class AI {
    public AIModel aiModel;

    /**
     * Calculate the next move
     * @param fieldStatus Fieldstatus contains player to calculate move for
     * @return move that was calcualted int
     */
    abstract public int calculateNextMove(FieldStatus fieldStatus);

    /**
     * @param gameModel model of game
     */
    public AI(GameModel gameModel) {
        aiModel = new AIModel(gameModel);
    }
}
