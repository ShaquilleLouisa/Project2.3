package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Controller;
import controller.ReversiController;
import model.gameitems.FieldStatus;

public class ReversiView extends GameView{
    HashMap<ArrayList<Integer>, Button> buttonLocation;
    ReversiController controller;
    Stage stage;
    public ReversiView() {

    }

    public ReversiView(Controller controller) {
        this.controller = (ReversiController) controller;
        stage = new Stage();
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void update(int move, FieldStatus fieldStatus) {

    }
}
