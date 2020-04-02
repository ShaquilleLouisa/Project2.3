package ai;

import model.ReversiModel;

public class ReversiAI extends AI {

    public ReversiAI(ReversiModel reversiModel) {
        super(reversiModel);
    }

    @Override
    public int calculateNextMove() {
        return 44;
    }
}
