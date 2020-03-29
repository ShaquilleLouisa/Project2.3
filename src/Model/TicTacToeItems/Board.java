package Model.TicTacToeItems;

import Exceptions.MoveException;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private ArrayList<Field> fields;
    private HashMap<Integer, ArrayList<Integer>> moves;
    private int fieldSize;
    public Board(int xSize, int ySize) {
        if(xSize == ySize) {
            fieldSize = xSize;
        }
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
                Field newField = new Field(i, j);
                fields.add(newField);
            }
        }
    }

    public void setFieldStatus(int x, int y, FieldStatus status) throws MoveException {
        for (Field field : fields) {
            if (field.getX() == x && field.getY() == y) {
                    field.setState(status);
                    return;
            }
        }
        throw new MoveException("Field does not exist");
    }

    public FieldStatus getFieldStatus(int x, int y) throws MoveException {
        for (Field field : fields) {
            if (field.getX() == x && field.getY() == y) {
                return field.getFieldStatus();
            }
        }
        throw new MoveException("Field does not exist");
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
