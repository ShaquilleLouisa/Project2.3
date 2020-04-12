package model;

import exceptions.MoveException;
import model.gameitems.*;

public abstract class GameModel extends Model {
    abstract public void setFieldStatus(int move, FieldStatus status) throws MoveException;
    abstract public FieldStatus getFieldStatus(int move);
    abstract public int getFieldSize();
    abstract public Board getBoard();
    abstract public boolean isPlayable(int x, int y);
    abstract public boolean[][] getValidMoves();

    abstract public void setAiUse(boolean useAi);
    abstract public boolean isUseAi();

    abstract public void setOnlineUse(boolean online);
    abstract public boolean isOnline();

    abstract public void setDoubleAi(boolean ai);
    abstract public boolean isDoubleAi();

<<<<<<< Updated upstream
    abstract public int getUserMove();
    abstract public void setUserMove(int move);
=======
    abstract public int getPlayer();
    abstract public void setPlayer(int player);

    abstract public boolean[][] calculateValidMoves(FieldStatus fieldStatus);

    abstract public void setFirstMoves();

    abstract public int getUserMove();
    abstract public void setUserMove(int move);

    abstract public int checkEnd(Board board);
>>>>>>> Stashed changes
}
