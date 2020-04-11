
        package view;

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

        import java.util.ArrayList;
        import java.util.HashMap;

        import controller.Controller;
        import controller.ReversiController;
        import model.gameitems.Board;
        import model.gameitems.FieldStatus;
        import model.gameitems.ReversiFieldStatus;

public class ReversiView extends GameView{
    HashMap<Integer, Button> buttonLocation;
    ReversiController controller;
    ImageView whiteStone = new ImageView("File:assets/boards/white.png");
    ImageView blackStone = new ImageView("File:assets/boards/black.png");
    Image bg = new Image("File:assets/boards/wood2.jpg");
    Text notification;
    public ReversiView(Controller controller) {
        this.controller = (ReversiController) controller;
        getScene();
    }

    public Scene getScene() {
        BorderPane rootPane = new BorderPane();
        GridPane pane = new GridPane();
        FlowPane reversi = new FlowPane();
        VBox player1Box = new VBox();
        VBox player2Box = new VBox();
        Button backButton = new Button();
        Label reversiName = new Label();

        reversiName.setText("");
        reversiName.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30");
        reversi.setAlignment(Pos.TOP_CENTER);
        reversi.getChildren().add(reversiName);

        pane.setMaxWidth(600);
        pane.setMaxHeight(600);
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        buttonLocation = new HashMap<>();

        backButton.setText("Opgeven");
        backButton.setTranslateX(10);
        backButton.setTranslateY(-10);
        backButton.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 30; -fx-border-color: #FFFFFF");

        Label player1;
        Label player2;

        player1 = new Label("Player 1");
        player1.setMinWidth(120);
        player1.setTextAlignment(TextAlignment.RIGHT);
        player1.setTranslateX(65);
        player1.setTranslateY(10);
        player1.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10,10,10,10; -fx-font-size: 30");

        player2 = new Label("Player 2");
        player2.setMinWidth(120);
        player2.setTextAlignment(TextAlignment.RIGHT);
        player2.setTranslateX(-65);
        player2.setTranslateY(10);
        player2.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;  -fx-padding: 10,10,10,10; -fx-font-size: 30");

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
                button.setOnAction(buttonHandler);
                button.setMinWidth(70);
                button.setMaxWidth(70);
                button.setMinHeight(70);
                button.setMaxHeight(70);
                button.setWrapText(true);
                //button.setStyle("-fx-background-color: transparant;");
                button.setStyle(String.format("-fx-font-size: 20; -fx-background-color: transparant; -fx-border-color: #FFFFFF; -fx-background-radius: 0;", (int) (0.35 * 80)));
                ArrayList<Integer> location = new ArrayList<>();
                location.add(i);
                location.add(j);
                buttonLocation.put(counter, button);
                pane.add(button, j, i);
                counter++;
            }
        }

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
        rootPane.setCenter(pane);
        return new Scene(rootPane, 400, 400);
    }

    @Override
    public void update(int move, FieldStatus fieldStatus) {
        Platform.runLater(() -> {
            ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus)fieldStatus;
            reversiFieldStatus.setId(fieldStatus.getID());
            //System.out.println("" + move + fieldStatus.getValue());
            Button button = buttonLocation.get(move);
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
        });
    }

    @Override
    public void updateNotification(String notification) {
        Platform.runLater(() -> {
            this.notification.setText(notification);
        });
    }

}