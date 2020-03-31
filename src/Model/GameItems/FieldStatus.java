package Model.GameItems;

public abstract class FieldStatus {
    protected int id;
    protected String value;

    public boolean isEmpty() {
        return (id == 0);
    }

    public int getID() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}