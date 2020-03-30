package Model.GameItems;

class Field<E> {
    private int x;
    private int y;
    private E fieldStatus;

     protected Field(int x, int y) {
        this.x = x;
        this.y = y;
        //fieldStatus = FieldStatus.NONE;
    }

    public void setState(E state) {
        this.fieldStatus = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public E getFieldStatus() {
        return fieldStatus;
    }
}
