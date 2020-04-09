package ai;

import model.ReversiModel;

public class DefaultReversiAI extends AI implements ReversiAI {
    public DefaultReversiAI(ReversiModel reversiModel) {
        super(reversiModel);
    }

    @Override
    public int calculateNextMove(int player) {
        return 0;
    }

    @Override
    public int calculateNextMove() {
        return 44;
    }
}
