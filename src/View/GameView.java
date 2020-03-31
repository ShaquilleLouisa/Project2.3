package View;

import Model.GameItems.FieldStatus;

public abstract class GameView extends View {
    abstract public void update(int move, FieldStatus fieldStatus);
}
