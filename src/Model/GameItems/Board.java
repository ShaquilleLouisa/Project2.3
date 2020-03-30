package Model.GameItems;

import Exceptions.MoveException;

import java.util.ArrayList;
import java.util.HashMap;

public class Board<E> {
    public ArrayList<Field<E>> fields;
    private HashMap<Integer, ArrayList<Integer>> moves;

    public Board(int xSize, int ySize) {
        fields = new ArrayList<>(xSize*ySize);
        moves = new HashMap<>();

        int counter = 0;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                ArrayList<Integer> xAndY = new ArrayList<>(2);
                xAndY.add(i);
                xAndY.add(j);
                moves.put(counter,xAndY);
                counter++;
                Field<E> newField = new Field<E>(i, j);
                fields.add(newField);
            }
        }
    }

    public void setFieldStatus(int x, int y, E status) throws MoveException {
        for (Field<E> field : fields) {
            if (field.getX() == x && field.getY() == y) {
                if (field.getFieldStatus() == null) {
                    field.setState(status);
                    return;
                } else {
                    throw new MoveException("Field already used");
                }
            }
        }
        throw new MoveException("Field does not exist");
    }

    public E getFieldStatus(int x, int y) {
        for (Field<E> field : fields) {
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
