package view;

import controller.Controller;
import controller.TicTacToeController;
import exceptions.MoveException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    Text notification;

    TicTacToeController controller;

    public TicTacToeView() {

    }

    public TicTacToeView(Controller controller) {
        this.controller = (TicTacToeController) controller;
    }

    public Scene getScene() {
        BorderPane rootPane = new BorderPane();
        GridPane pane = new GridPane();
        buttonLocation = new HashMap<>();
        Button backButton = new Button("Back");
        int counter = 0;

        EventHandler<ActionEvent> backHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.notifyDone();
                System.out.println("BACK HAS BEEN PRESSED");
            }
        };
        backButton.setOnAction(backHandler);
        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button button = (Button) (event.getSource());
                try {
                    controller.doMove(Integer.parseInt(button.getText()));
<<<<<<< Updated upstream
                } catch (MoveException e) {
                    System.out.println("Move not available please try again.");
=======
                } catch (NumberFormatException nfe) {
                    updateNotification("Cant do move!");
>>>>>>> Stashed changes
                }
            }
        };
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                Button button = new Button(""+counter);
                button.setMinWidth(100);
                button.setMinHeight(100);
                button.setWrapText(true);
                button.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 100)));
                button.setOnAction(buttonHandler);
                buttonLocation.put(counter, button);
                pane.add(button, i, j);
                counter++;
            }
        }
        notification = new Text();
        notification.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        rootPane.setTop(backButton);
        rootPane.setCenter(pane);
        rootPane.setBottom(notification);
        return new Scene(rootPane, 300, 300);

    }

    public void updateNotification(String text) {
        Platform.runLater(() -> {
            notification.setText(text);
        });
    }

    public void update(int move, FieldStatus status) {
        TicTacToeFieldStatus ticTacToeFieldStatus = (TicTacToeFieldStatus)status;
        Platform.runLater(() -> {
            System.out.println("" + move + status.toString());
            Button button = buttonLocation.get(move);
<<<<<<< Updated upstream
            button.setText(ticTacToeFieldStatus.getValue());
=======
            if(ticTacToeFieldStatus.getID() == 1){
                ImageView x = new ImageView("File:assets/boards/x.png");
                button.setText("");
                button.setGraphic(x);
            }else if(ticTacToeFieldStatus.getID() == 2){
                ImageView o = new ImageView("File:assets/boards/o.png");
                button.setText("");
                button.setGraphic(o);
            }
>>>>>>> Stashed changes
        });
    }
}
