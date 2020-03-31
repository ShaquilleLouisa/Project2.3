package Model.GameItems;

public class ReversiFieldStatus extends FieldStatus {

    public ReversiFieldStatus(){
        setUnPlayable();
    }

    public void setUnPlayable(){
        setId(1);
        setValue(" ");
    }

    public void setBlack(){
        setId(2);
        setValue("X");
    }

    public void setWhite(){
        setId(3);
        setValue("O");
    }

    public boolean isUnPlayable(){
        return getID() == 1;
    }

    public boolean isBlack(){
        return getID() == 2;
    }

    public boolean isWhite(){
        return getID() == 3;
    }
}