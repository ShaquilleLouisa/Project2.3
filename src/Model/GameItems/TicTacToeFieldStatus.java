package Model.GameItems;

public class TicTacToeFieldStatus extends FieldStatus {

    public void setCross(){
        setId(1);
        setValue("X");
    }

    public void setCircle(){
        setId(2);
        setValue("O");
    }

    public boolean isCross(){
        return getID() == 1;
    }

    public boolean isCircle(){
        return getID() == 2;
    }
}