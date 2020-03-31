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
        return id == 1;
    }

    public boolean isBlack(){
        return id == 2;
    }

    public boolean isWhite(){
        return id == 3;
    }

    public String getValue(){
        return value;
    }
}