package View;

import Model.TicTacToeItems.FieldStatus;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class ReversiView extends GameView{
    HashMap<ArrayList<Integer>, Button> buttonLocation;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Reversi");
        GridPane pane = new GridPane();
        buttonLocation = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button("");
                button.setMinWidth(50);
                button.setMinHeight(50);
                button.setWrapText(true);
                button.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 100)));
                ArrayList<Integer> location = new ArrayList<>();
                location.add(i);
                location.add(j);
                buttonLocation.put(location, button);
                pane.add(button, i, j);
            }
        }

        stage.setScene(new Scene(pane, 400, 400));
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(int move, FieldStatus status) {

    }
}
