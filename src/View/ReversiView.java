package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.Controller;
import Controller.ReversiController;
import Model.GameItems.FieldStatus;

public class ReversiView extends GameView{
    HashMap<ArrayList<Integer>, Button> buttonLocation;

    public ReversiView() {

    }

    public ReversiView(Controller controller) {
        controller = (ReversiController) controller;
    }

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
    public <T extends FieldStatus> void update(int move, T fieldStatus) {

    }
}
