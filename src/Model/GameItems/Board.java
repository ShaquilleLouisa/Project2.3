package Model.GameItems;

import Exceptions.MoveException;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    public ArrayList<Field> fields;
    private HashMap<Integer, ArrayList<Integer>> moves;
    private int fieldSize;

    public <T extends FieldStatus> Board(int xSize, int ySize, T defaultFieldStatus) {
        if(xSize == ySize) {
            fieldSize = xSize;
        }
        fields = new ArrayList<>(xSize * ySize);
        // set fieldsstatus in fields.
        moves = new HashMap<>();

        int counter = 0;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                ArrayList<Integer> xAndY = new ArrayList<>(2);
                xAndY.add(i);
                xAndY.add(j);
                moves.put(counter, xAndY);
                fields.add(new Field(i, j, defaultFieldStatus));
                counter++;
            }
        }
    }

    public void setFieldStatus(int x, int y, FieldStatus fieldStatus) throws MoveException {
        for (Field field : fields) {
            if (field.getX() == x && field.getY() == y) {
                field.getFieldStatus().setId(fieldStatus.getID());
            }
        }
        throw new MoveException("Field does not exist");
    }

    public FieldStatus getFieldStatus(int x, int y)  {
        for (Field field : fields) {
            if (field.getX() == x && field.getY() == y) {
                return field.getFieldStatus();
            }
        }
        return null;
        //throw new MoveException("Field does not exist");
    }

    public ArrayList<Integer> getMove(int x) {
        return moves.get(x);
    }

    public int getFieldSize() {
        if(fieldSize > 0) {
            return fieldSize;
        }
        return 0;
    }
}