
package view;

import controller.ServerCommunication;
import exceptions.MoveException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Controller;
import controller.ReversiController;
import model.ReversiModel;
import model.gameitems.Board;
import model.gameitems.FieldStatus;
import model.gameitems.ReversiFieldStatus;

 /**
  * Class that is used to show the reversiboard
  * @author Anna van Rosmalen
  */
public class ReversiView extends GameView{

    /**
     * ReversieView constructor
     * @param controller ReversiController
     */
     public ReversiView(Controller controller) {
         this.controller = (ReversiController) controller;
         getScene();
     }
     ReversiController controller;
     HashMap<Integer, Button> buttonLocation;

     /** Images used for the reversiboard */
     Image bg = new Image("File:assets/boards/wood2.jpg");
     ImageView blackStone = new ImageView("File:assets/boards/blackB.png");
     ImageView whiteStone = new ImageView("File:assets/boards/whiteB.png");

     /** Panes */
     BorderPane rootPane;
     GridPane pane;
     GridPane extraPane;
     FlowPane reversi;

     /** Players */
     Label player1;
     Label player2;
     Label score1;
     Label score2;
     VBox player1Box;
     VBox player2Box;

     /** Forfeit/back button */
     Button backButton;

     /** Board/game name */
     Label reversiName;

     /**
      * Setup of the scene
      * @return scene
      */
    public Scene getScene() {
        rootPane = new BorderPane();
        pane = new GridPane();
        extraPane = new GridPane();
        reversi = new FlowPane();

        backButton = new Button();

        reversiName = new Label();

        buttonLocation = new HashMap<>();

        player1Box = new VBox();
        player2Box = new VBox();

        /** Boardgame Name setup */
        reversiName.setText("");
        reversiName.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30");
        reversi.setAlignment(Pos.TOP_CENTER);
        reversi.getChildren().add(reversiName);

        /** ReversiBoard pane setup */
        pane.setMaxWidth(600);
        pane.setMaxHeight(600);
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        /** Forfeit/back button setup */
        backButton.setText("Opgeven");
        backButton.setTranslateX(10);
        backButton.setTranslateY(-20);
        backButton.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30; -fx-border-color: #FFFFFF");

        /** Score labels setup */
        score1 = new Label();
        score1.setTranslateY(30);
        score1.setTranslateX(20);
        score1.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30");

        score2 = new Label();
        score2.setTranslateY(30);
        score2.setTranslateX(-20);
        score2.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30");

        /** Player labels setup */
        player1 = new Label("Player 1");
        player1.setMinWidth(120);
        player1.setTextAlignment(TextAlignment.RIGHT);
        player1.setTranslateX(20);
        player1.setTranslateY(10);
        player1.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30");

        player2 = new Label("Player 2");
        player2.setMinWidth(120);
        player2.setTextAlignment(TextAlignment.RIGHT);
        player2.setTranslateX(-20);
        player2.setTranslateY(10);
        player2.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30");

        /** Playerboxes setup */
        player1Box.getChildren().add(player1);
        player1Box.getChildren().add(score1);
        player1Box.getChildren().add(2, blackStone);
        player2Box.getChildren().add(player2);
        player2Box.getChildren().add(score2);
        player2Box.getChildren().add(2, whiteStone);

        extraPane.add(backButton,0,0);

        /** Rootpane layout setup */
        rootPane.setStyle("-fx-background-color: #262626");
        rootPane.setBottom(extraPane);
        rootPane.setLeft(player1Box);
        rootPane.setRight(player2Box);
        rootPane.setTop(reversi);
        rootPane.setCenter(pane);

        /** handle forfeitbutton/backbutton being pressed */
        EventHandler<ActionEvent> backHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.notifyDone();

                System.out.println("BACK HAS BEEN PRESSED");
            }
        };
        backButton.setOnAction(backHandler);


        /** handle illegal move */
        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button button = (Button) (event.getSource());
                try {
                    controller.doMove(Integer.parseInt(button.getText()));
                } catch (NumberFormatException nfe) {
                    updateNotification("Can't do move");
                }
            }
        };

        /** Setup gridpane with 8*8 buttons */
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button(""+counter);
                button.setOnAction(buttonHandler);
                button.setMinWidth(70);
                button.setMaxWidth(70);
                button.setMinHeight(70);
                button.setMaxHeight(70);
                button.setWrapText(true);
                button.setStyle(String.format("-fx-font-size: 20; -fx-background-color: transparant; -fx-border-color: #FFFFFF; -fx-background-radius: 0;", (int) (0.35 * 80)));
                ArrayList<Integer> location = new ArrayList<>();
                location.add(i);
                location.add(j);
                buttonLocation.put(counter, button);
                pane.add(button, j, i);
                counter++;
            }
        }


        return new Scene(rootPane, 400, 400);
    }

    /**
     * Updates the scene
     * @param move
     * @param fieldStatus
     */
    @Override
    public void update(int move, FieldStatus fieldStatus) {
        Platform.runLater(() -> {
            ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus)fieldStatus;
            reversiFieldStatus.setId(fieldStatus.getID());
            Button button = buttonLocation.get(move);
            int value = reversiFieldStatus.getID();

            /** Sets the right stone immage on fields after a move */
            if(value == reversiFieldStatus.BLACK){
                ImageView blackStone = new ImageView("File:assets/boards/black.png");
                button.setText("");
                button.setGraphic(blackStone);
            }else if(value == reversiFieldStatus.WHITE){
                ImageView whiteStone = new ImageView("File:assets/boards/white.png");
                button.setText("");
                button.setGraphic(whiteStone);
            }else if(value == reversiFieldStatus.PLAYABLE){
                ImageView playable = new ImageView("File:assets/boards/playable.png");
                button.setText("");
                button.setGraphic(playable);
            }

            /** Shows the current player stone and updates the score */
            if(controller.getCurrentPlayer() == reversiFieldStatus.BLACK){
                blackStone.setTranslateX(30);
                blackStone.setTranslateY(50);
                whiteStone.setVisible(false);
                blackStone.setVisible(true);
                score1.setText("Aantal stenen:\n" + controller.getScore(1));
                score2.setText("Aantal stenen:\n" + controller.getScore(2));
            }else if(controller.getCurrentPlayer() == reversiFieldStatus.WHITE){
                whiteStone.setTranslateX(-30);
                whiteStone.setTranslateY(50);
                whiteStone.setVisible(true);
                blackStone.setVisible(false);
                score1.setText("Aantal stenen:\n" + controller.getScore(1));
                score2.setText("Aantal stenen:\n" + controller.getScore(2));
            }

            /** Checks if the game has ended en shows the winner or draw*/
            int end = controller.getEnd();
            if(end >= 0){
                Text winner = new Text("");
                backButton.setText("Terug");
                winner.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 25;");
                winner.setTranslateX(250);
                winner.setTranslateY(-20);
                if(end == 0){
                    winner.setText("Het is gelijkspel");
                    extraPane.add(winner,1,0);
                }else if(end == 1){
                    winner.setText("Zwart heeft gewonnen");
                    extraPane.add(winner,1,0);
                }else if(end == 2){
                    winner.setText("Wit heeft gewonnen");
                    extraPane.add(winner,1,0);
                }
            }


        });
    }

    @Override
    public void updateNotification(String notification) {

    }


}