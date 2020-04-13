package view;

import controller.Controller;
import controller.TicTacToeController;
import exceptions.MoveException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.gameitems.*;
import model.TicTacToeModel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class TicTacToeView extends GameView {
    HashMap<Integer, Button> buttonLocation;
    Text notification;
    GridPane extraPane;

    TicTacToeController controller;
    TicTacToeModel model;
    Image tictactoe = new Image("File:assets/boards/tictactoeB.png");


    public TicTacToeView() {

    }

    public TicTacToeView(Controller controller) {
        this.controller = (TicTacToeController) controller;
    }

    public Scene getScene() {
        BorderPane rootPane = new BorderPane();
        GridPane pane = new GridPane();
        Button backButton = new Button();
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(new Background(new BackgroundImage(tictactoe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        buttonLocation = new HashMap<>();
        extraPane = new GridPane();

        extraPane.add(backButton,0,0);

        backButton.setText("Opgeven");
        backButton.setTranslateX(10);
        backButton.setTranslateY(-10);
        backButton.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30; -fx-border-color: #FFFFFF");

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
                } catch (NumberFormatException nfe) {
                    updateNotification("Cant do move!");
                }
            }
        };

        int counter = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                Button button = new Button(""+counter);
                button.setMinWidth(200);
                button.setMaxWidth(200);
                button.setMinHeight(200);
                button.setMaxHeight(200);
                button.setWrapText(true);
                button.setStyle(String.format("-fx-font-size: %dpx; -fx-background-color: transparant", (int) (0.45 * 100)));
                button.setOnAction(buttonHandler);
                buttonLocation.put(counter, button);
                pane.add(button, i, j);
                counter++;
            }
        }

        rootPane.setStyle("-fx-background-color: #262626");
        rootPane.setCenter(pane);
        rootPane.setBottom(extraPane);

        return new Scene(rootPane, 600, 600);

    }

    public void updateNotification(String text) {
        Platform.runLater(() -> {
            notification.setText(text);
        });
    }

    public void update(int move, FieldStatus status) {
        TicTacToeFieldStatus ticTacToeFieldStatus = (TicTacToeFieldStatus)status;
        Platform.runLater(() -> {
            //System.out.println("" + move + status.toString());
            Button button = buttonLocation.get(move);
            if(ticTacToeFieldStatus.getID() == 1){
                ImageView x = new ImageView("File:assets/boards/crossB.png");
                button.setText("");
                button.setGraphic(x);
            }else if(ticTacToeFieldStatus.getID() == 2){
                ImageView o = new ImageView("File:assets/boards/circleB.png");
                button.setText("");
                button.setGraphic(o);
            }

            int end = controller.getEnd();
            if(end != -1){
                Text winner = new Text("");
                winner.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 25;");
                winner.setTranslateX(250);
                winner.setTranslateY(-20);
                if(end == 1){
                    winner.setText("Cross heeft gewonnen");
                    extraPane.add(winner,1,0);
                }else{
                    winner.setText("Circle heeft gewonnen");
                    extraPane.add(winner,1,0);
                }
            }
        });
    }
}
