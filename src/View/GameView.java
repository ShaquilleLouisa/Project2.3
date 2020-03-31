package View;

import Model.GameItems.FieldStatus;

public abstract class GameView extends View {
    abstract public <T extends FieldStatus> void update(int move, T fieldStatus);
}
