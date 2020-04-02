package view;

import controller.Controller;
import controller.TicTacToeController;
import model.gameitems.*;
import model.TicTacToeModel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class TicTacToeView extends GameView {
    HashMap<Integer, Button> buttonLocation;
    TicTacToeController controller;
    public TicTacToeView() {

    }

    public TicTacToeView(Controller controller) {
        controller = (TicTacToeController) controller;
    }

    public void start(Stage stage) {
        stage.setTitle("Hello World!");
        GridPane pane = new GridPane();
        buttonLocation = new HashMap<>();

        int counter = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                Button button = new Button(""+counter);
                button.setMinWidth(100);
                button.setMinHeight(100);
                button.setWrapText(true);
                button.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 100)));
                buttonLocation.put(counter, button);
                pane.add(button, i, j);
                counter++;
            }
        }

        stage.setScene(new Scene(pane, 300, 300));
        stage.show();
    }

    public void update(int move, FieldStatus status) {
        TicTacToeFieldStatus ticTacToeFieldStatus = (TicTacToeFieldStatus)status;
        Platform.runLater(() -> {
            System.out.println("" + move + status.toString());
            Button button = buttonLocation.get(move);
            button.setText(ticTacToeFieldStatus.getValue());
        });

    }

}
