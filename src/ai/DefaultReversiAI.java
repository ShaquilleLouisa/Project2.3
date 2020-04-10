package ai;

import model.ReversiModel;
import model.gameitems.FieldStatus;

public class DefaultReversiAI extends AI implements ReversiAI {
    public DefaultReversiAI(ReversiModel reversiModel) {
        super(reversiModel);
    }

    @Override
    public int calculateNextMove(FieldStatus fieldStatus) {
        return 44;
    }
}
