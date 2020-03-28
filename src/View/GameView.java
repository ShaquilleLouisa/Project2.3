package View;

import Model.TicTacToeItems.FieldStatus;

public abstract class GameView extends View {
    abstract public void update(int move, FieldStatus status);
}
