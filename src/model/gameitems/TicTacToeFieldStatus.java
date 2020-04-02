package model.gameitems;

public class TicTacToeFieldStatus extends FieldStatus {

    public static final int CROSS = 1;
    public static final int CIRCLE = 2;

    public void setCross(){
        setId(1);
        setValue("X");
    }

    public void setCircle(){
        setId(2);
        setValue("O");
    }

    public boolean isCross(){
        return getID() == CROSS;
    }

    public boolean isCircle(){
        return getID() == CIRCLE;
    }
}