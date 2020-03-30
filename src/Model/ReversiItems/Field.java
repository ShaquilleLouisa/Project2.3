package Model.ReversiItems;

class Field {
    private int x;
    private int y;
    private FieldStatus fieldStatus ;

     protected Field(int x, int y) {
        this.x = x;
        this.y = y;
        fieldStatus = FieldStatus.NONE;
    }

    public void setState(FieldStatus state) {
        this.fieldStatus = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FieldStatus getFieldStatus() {
        return fieldStatus;
    }
}
