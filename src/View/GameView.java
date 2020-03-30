package View;

public abstract class GameView extends View {
    abstract public <E> void update(int move, E status);
}
