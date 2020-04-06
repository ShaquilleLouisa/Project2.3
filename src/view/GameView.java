package view;

import javafx.scene.Scene;
import model.gameitems.FieldStatus;

public abstract class GameView extends View {
    abstract public void update(int move, FieldStatus fieldStatus);
    abstract public void updateNotification(String notification);
    abstract public Scene getScene();
}
