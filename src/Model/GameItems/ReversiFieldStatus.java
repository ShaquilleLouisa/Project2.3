package Model.GameItems;

public class ReversiFieldStatus extends FieldStatus {

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

    public boolean isBlack(){
        return id == 2;
    }

    public boolean isWhite(){
        return id == 2;
    }

    public String getValue(){
        return value;
    }
}