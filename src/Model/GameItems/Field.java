package Model.GameItems;

class Field {
    private int x;
    private int y;
    private Object fieldStatus;

     protected Field(int x, int y) {
        this.x = x;
        this.y = y;
        //fieldStatus = FieldStatus.NONE;
    }

    public <E> void setState(E state) {
        this.fieldStatus = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public <E> E getFieldStatus() {
        return (E)fieldStatus;
    }
}
