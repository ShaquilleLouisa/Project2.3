package View;

import Controller.Controller;
import Controller.TicTacToeController;
import Model.GameItems.FieldStatus;
import Model.GameItems.TicTacToeFieldStatus;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

    public <T extends FieldStatus>void update(int move, T fieldStatus) {
        Platform.runLater(() -> {
            TicTacToeFieldStatus ticTacToeFieldStatus = (TicTacToeFieldStatus)fieldStatus;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            Button button = buttonLocation.get(move);
                            button.setText(ticTacToeFieldStatus.getValue());
                        }
                    }
                }
        );

    }
}
