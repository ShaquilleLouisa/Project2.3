package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Controller;
import controller.ReversiController;
import model.gameitems.FieldStatus;
import model.gameitems.ReversiFieldStatus;

public class ReversiView extends GameView{
    HashMap<Integer, Button> buttonLocation;
    ReversiController controller;
    public ReversiView() {

    }

    public ReversiView(Controller controller) {
        this.controller = (ReversiController) controller;
        getScene();
    }

    public Scene getScene() {
        BorderPane rootPane = new BorderPane();
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #262626;"); // Default background color
        buttonLocation = new HashMap<>();
        Button backButton = new Button("Back");
        Button pauseButton = new Button("Pause");
        int counter = 0;

        EventHandler<ActionEvent> pauseHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.pauze();
                System.out.println("Pauze HAS BEEN PRESSED");
            }
        };

        EventHandler<ActionEvent> backHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.notifyDone();
                System.out.println("BACK HAS BEEN PRESSED");
            }
        };
        backButton.setOnAction(backHandler);
        pauseButton.setOnAction(pauseHandler);

        GridPane gridPane = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button(""+counter);
                button.setMinWidth(80);
                button.setMinHeight(80);
                button.setWrapText(true);
                button.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.35 * 80)));
                ArrayList<Integer> location = new ArrayList<>();
                location.add(i);
                location.add(j);
                buttonLocation.put(counter, button);
                pane.add(button, j, i);
                counter++;
            }
        }
        gridPane.add(backButton,0,0);
        gridPane.add(pauseButton,0,1);
        rootPane.setTop(gridPane);
        rootPane.setCenter(pane);
        return new Scene(rootPane, 400, 400);
    }

    @Override
    public void update(int move, FieldStatus fieldStatus) {
        ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus)fieldStatus;

        Platform.runLater(() -> {
            String bgcolor = "grey";
            int fieldId = fieldStatus.getID();
            if(fieldId == 1) {
                bgcolor = "BLACK";
            } else if(fieldId == 2) {
                bgcolor = "WHITE";
            }
            //System.out.println("" + move + fieldStatus.getValue());
            Button button = buttonLocation.get(move);
            String value = reversiFieldStatus.getValue();
            button.setText(value);
            button.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.35 * 80))+"-fx-base: "+bgcolor+";");
        });
    }

    @Override
    public void updateNotification(String notification) {
        //TODO add notification to view
    }
}
