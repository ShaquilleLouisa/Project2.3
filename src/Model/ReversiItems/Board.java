package Model.ReversiItems;

import Exceptions.MoveException;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    public ArrayList<Field> fields;
    private HashMap<Integer, ArrayList<Integer>> moves;

    public Board(int xSize, int ySize) {
        fields = new ArrayList<>(xSize*ySize);
        moves = new HashMap<>();

        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ArrayList<Integer> xAndY = new ArrayList<>(2);
                xAndY.add(i);
                xAndY.add(j);
                moves.put(counter,xAndY);
                counter++;
                Field newField = new Field(i, j);
                fields.add(newField);
            }
        }
    }

    public void setFieldStatus(int x, int y, FieldStatus status) throws MoveException {
        for (Field field : fields) {
            if (field.getX() == x && field.getY() == y) {
                if (field.getFieldStatus() == FieldStatus.NONE) {
                    field.setState(status);
                    return;
                } else {
                    throw new MoveException("Field already used");
                }
            }
        }
        throw new MoveException("Field does not exist");
    }

    public FieldStatus getFieldStatus(int x, int y) {
        for (Field field : fields) {
            if (field.getX() == x && field.getY() == y) {
                return field.getFieldStatus();
            }
        }
        return null;
    }

    public ArrayList<Integer> getMove(int x) {
        return moves.get(x);
    }
}
