package model.gameitems;

/**
 * The Field class always has the same type of fieldStatus and the x and y positions also stay the same
 */
class Field {
    final int x, y;
    final FieldStatus fieldStatus;

    /**
     * 
     * @param x The x position
     * @param y The y position
     * @param FieldStatus The fieldStatus
     * 
     */
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