package model.gameitems;

public class ReversiFieldStatus extends FieldStatus {

    public static final int PLAYABLE = -1;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public void setPlayable(){
        setId(PLAYABLE);
        setValue("*");
    }

    public void setBlack(){
        setId(BLACK);
        setValue("B");
    }

    public void setWhite(){
        setId(WHITE);
        setValue("W");
    }

    public boolean isPlayable(){
        return getID() == PLAYABLE;
    }

    public boolean isBlack(){
        return getID() == BLACK;
    }

    public boolean isWhite(){
        return getID() == BLACK;
    }
}