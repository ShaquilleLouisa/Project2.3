package View;

import Controller.Controller;
import Controller.TicTacToeController;
import Model.TicTacToeModel;
import Model.GameItems.TicTacToeFieldStatus;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class TicTacToeView extends GameView {
    HashMap<ArrayList<Integer>, Button> buttonLocation;
    TicTacToeController controller;

    public TicTacToeView() {

    }

    public TicTacToeView(Controller controller) {
        controller = (TicTacToeController) controller;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tic Tac Toe");
        GridPane pane = new GridPane();
        buttonLocation = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button("");
                button.setMinWidth(100);
                button.setMinHeight(100);
                button.setWrapText(true);
                button.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 100)));
                ArrayList<Integer> location = new ArrayList<>();
                location.add(i);
                location.add(j);
                buttonLocation.put(location, button);
                pane.add(button, i, j);
            }
        }

        stage.setScene(new Scene(pane, 300, 300));
        stage.show();
    }

    public <E> void update(int move, E status) {
        Platform.runLater(() -> {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            Button button = buttonLocation.get(move);
                            if (status == TicTacToeFieldStatus.CIRCLE) {
                                button.setText("O");
                            } else if (status == TicTacToeFieldStatus.CROSS) {
                                button.setText("X");
                            } else {
                                button.setText("");
                            }
                        }
                    }
                }
        );

    }

    public static void main(String[] args) {
        launch(args);
    }
}
