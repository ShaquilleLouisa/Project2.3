package model.gameitems;

class Field {
    final int x, y;
    final FieldStatus fieldStatus;

    protected Field(int x, int y, FieldStatus fieldStatus) {
        this.x = x;
        this.y = y;
        this.fieldStatus = fieldStatus;
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