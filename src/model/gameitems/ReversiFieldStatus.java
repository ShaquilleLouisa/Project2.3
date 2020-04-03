package model.gameitems;

public class ReversiFieldStatus extends FieldStatus {

    public static final int PLAYABLE = -1;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public void setPlayable(){
        setId(PLAYABLE);
        setValue("*");
    }

    public void setWhite(){
        setId(WHITE);
        setValue("W");
    }

    public void setBlack(){
        setId(BLACK);
        setValue("B");
    }
}