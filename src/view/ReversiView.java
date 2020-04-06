
        package view;

        import javafx.application.Platform;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Pos;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.image.ImageView;
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
    ImageView whiteStone = new ImageView("File:assets/boards/white.png");
    ImageView blackStone = new ImageView("File:assets/boards/black.png");
    ImageView bg = new ImageView("File:assets/boards/wood.jpg");

    public ReversiView(Controller controller) {
        this.controller = (ReversiController) controller;
    }

    public Scene getScene() {
        BorderPane rootPane = new BorderPane();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: #262626;"); // Default background color
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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button(""+counter);
                button.setMinWidth(80);
                button.setMinHeight(80);
                button.setWrapText(true);
                //button.setStyle("-fx-background-color: transparant;");
                button.setStyle(String.format("-fx-font-size: %dpx; -fx-background-color: #056005; -fx-border-color: #000000; -fx-background-radius: 0;", (int) (0.35 * 80)));
                ArrayList<Integer> location = new ArrayList<>();
                location.add(i);
                location.add(j);
                buttonLocation.put(counter, button);
                pane.add(button, j, i);
                counter++;
            }
        }
        rootPane.setTop(backButton);
        backButton.setStyle("-fx-background-color: #262626; -fx-text-fill: #FFFFFF;");
        rootPane.setCenter(pane);
        return new Scene(rootPane, 400, 400);
    }

    @Override
    public void update(int move, FieldStatus fieldStatus) {
        ReversiFieldStatus reversiFieldStatus = (ReversiFieldStatus)fieldStatus;
        Platform.runLater(() -> {
            //System.out.println("" + move + fieldStatus.getValue());
            Button button = buttonLocation.get(move);
            String value = reversiFieldStatus.getValue();
            if(reversiFieldStatus.getValue() == "B"){
                ImageView blackStone = new ImageView("File:assets/boards/black.png");
                button.setPrefWidth(80);
                button.setPrefHeight(80);
                button.setText("");
                button.setGraphic(blackStone);
            }else if(reversiFieldStatus.getValue() == "W"){
                ImageView whiteStone = new ImageView("File:assets/boards/white.png");
                button.setPrefWidth(80);
                button.setPrefHeight(80);
                button.setText("");
                button.setGraphic(whiteStone);
            }else if(reversiFieldStatus.getValue() == "*"){
                button.setStyle("-fx-background-color: #28AF28;");
                button.setPrefWidth(80);
                button.setPrefHeight(80);
                button.setText("");
            }

        });
    }

    @Override
    public void updateNotification(String notification) {
        //TODO add notification to view
    }
}