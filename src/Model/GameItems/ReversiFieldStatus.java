package Model.GameItems;

public class ReversiFieldStatus extends FieldStatus {

    public void setPlayable(){
        setId(1);
        setValue("*");
    }

    public void setBlack(){
        setId(2);
        setValue("X");
    }

    public void setWhite(){
        setId(3);
        setValue("O");
    }

    public boolean isPlayable(){
        return getID() == 1;
    }

    public boolean isBlack(){
        return getID() == 2;
    }

    public boolean isWhite(){
        return getID() == 3;
    }

    public boolean isCurrentPlayer(int currentPlayer){
        return getID() == ((currentPlayer == 0) ? 2 : 3);
    }
}