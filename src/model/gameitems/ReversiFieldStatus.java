package model.gameitems;

/**
 * This fieldStatus class can be used for Reversi games
 */
public class ReversiFieldStatus extends FieldStatus {
    public static final int PLAYABLE = -1;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public void setPlayable(){
        setId(PLAYABLE);
        setValue("*");
    }

    public void setBLACK(){
        setId(BLACK);
        setValue("B");
    }

    public void setWHITE(){
        setId(WHITE);
        setValue("W");
    }
}