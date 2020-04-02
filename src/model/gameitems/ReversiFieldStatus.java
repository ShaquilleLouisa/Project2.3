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
        setValue("X");
    }

    public void setWhite(){
        setId(WHITE);
        setValue("O");
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

    public boolean isCurrentPlayer(int currentPlayer){
        return getID() == ((currentPlayer == 0) ? BLACK : WHITE);
    }
}