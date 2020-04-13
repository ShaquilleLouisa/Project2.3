package model.gameitems;

/**
 * Abstract fieldStatus class.
 */
public abstract class FieldStatus {
    private int id;
    private String value;

    /**
     * Empty this fieldStatus.
     * @param id direction for the x-axis.
     */
    public void setEmpty() {
         setId(0);
         setValue(" ");
    }

    public boolean isEmpty() {
        return id == 0;
    }

    public int getID() {
        return id;
    }

    public String getValue() {
        return value;
    }

    /**
     * Set the state of this fieldStatus.
     * @param id direction for the x-axis.
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}