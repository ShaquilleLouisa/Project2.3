package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.HashMap;
=======
        import exceptions.MoveException;
        import javafx.application.Platform;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Pos;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.*;
        import javafx.scene.text.*;
        import javafx.stage.Stage;
>>>>>>> Stashed changes

import controller.Controller;
import controller.ReversiController;
import model.gameitems.FieldStatus;
import model.gameitems.ReversiFieldStatus;

public class ReversiView extends GameView{
    HashMap<Integer, Button> buttonLocation;
    ReversiController controller;
<<<<<<< Updated upstream
    public ReversiView() {

    }

=======
    ImageView whiteStone = new ImageView("File:assets/boards/white.png");
    ImageView blackStone = new ImageView("File:assets/boards/black.png");
    Image bg = new Image("File:assets/boards/wood2.jpg");
    Text notification;
>>>>>>> Stashed changes
    public ReversiView(Controller controller) {
        this.controller = (ReversiController) controller;
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
                } catch (NumberFormatException nfe) {
                    updateNotification("Cant do move!");
                }
            }
        };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button(""+counter);
<<<<<<< Updated upstream
                button.setMinWidth(80);
                button.setMinHeight(80);
=======
                button.setOnAction(buttonHandler);
                button.setMinWidth(70);
                button.setMaxWidth(70);
                button.setMinHeight(70);
                button.setMaxHeight(70);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        rootPane.setTop(backButton);
=======

        player1Box.getChildren().add(player1);
        player2Box.getChildren().addAll(player2);
        notification = new Text();
        notification.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        GridPane extraPane = new GridPane();
        extraPane.add(backButton,0,0);
        extraPane.add(notification,1,0);
        rootPane.setStyle("-fx-background-color: #262626");
        rootPane.setBottom(extraPane);
        rootPane.setLeft(player1Box);
        rootPane.setRight(player2Box);
        rootPane.setTop(reversi);
>>>>>>> Stashed changes
        rootPane.setCenter(pane);
        return new Scene(rootPane, 400, 400);
    }

    @Override
    public void update(int move, FieldStatus fieldStatus) {
<<<<<<< Updated upstream
        ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus)fieldStatus;
=======
>>>>>>> Stashed changes
        Platform.runLater(() -> {
            ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus)fieldStatus;
            reversiFieldStatus.setId(fieldStatus.getID());
            //System.out.println("" + move + fieldStatus.getValue());
            Button button = buttonLocation.get(move);
<<<<<<< Updated upstream
            String value = reversiFieldStatus.getValue();
            button.setText(value);
=======
            int value = reversiFieldStatus.getID();
            if(value == 1){
                ImageView blackStone = new ImageView("File:assets/boards/black.png");
                button.setText("");
                button.setGraphic(blackStone);
            }else if(value == 2){
                ImageView whiteStone = new ImageView("File:assets/boards/white.png");
                button.setText("");
                button.setGraphic(whiteStone);
            }else if(value == -1){
                ImageView playable = new ImageView("File:assets/boards/playable.png");
                button.setText("");
                button.setGraphic(playable);
            }
>>>>>>> Stashed changes
        });
    }

    @Override
    public void updateNotification(String notification) {
<<<<<<< Updated upstream
        //TODO add notification to view
=======
        Platform.runLater(() -> {
            this.notification.setText(notification);
        });
>>>>>>> Stashed changes
    }
}
