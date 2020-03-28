package View;

import Controller.MasterController;
import Model.MasterModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;


public class MasterView extends View {
    MasterController controller;

    public MasterView() {

    }

    public MasterView(MasterController controller) {
        this.controller = controller;
    }

    // Username - buttons
    Button btnChangeName = new Button("");

    // Username - Text fields
    TextField usernameEdit = new TextField();

    // Application - window settings
    int windowWidth = 1280; // Default screen size
    int windowHeight = 720;
    Pane pnLauncher = new Pane(); // Main pane

    // Username backgrounds
    Image bgUsernameOk = new Image("File:assets/launcher/bgUsernameOk.png");
    Image bgUsernameEdit = new Image("File:assets/launcher/bgUsernameEdit.png");
    ImageView bgUsernameUse = new ImageView("File:assets/launcher/bgUsernameEdit.png");

    // Leaderboard - Textfields
    Text playersOnline = new Text("Voer rechtsboven een naam in");

    // Leaderboard - list
    ListView<String> playerList = new ListView<String>();

    @Override
    public void start(Stage stage) {
        ObservableList<String> players = controller.getPlayerList(); //FXCollections.observableArrayList ("Geen connectie :(");
        // Define button actions
        buttonActions();

        // Pane - background color
        pnLauncher.setStyle("-fx-background-color: #262626"); // Default background color

        // Username - Background
        pnLauncher.getChildren().add(bgUsernameUse);

        // Username - edit field
        usernameEdit.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        pnLauncher.getChildren().add(usernameEdit);

        // Username - edit name button
        pnLauncher.getChildren().add(btnChangeName);
        ImageView btnUsernameEdit = new ImageView("File:assets/launcher/nameEdit.png");
        btnChangeName.setStyle("-fx-background-color: #262626;");
        btnChangeName.setGraphic(btnUsernameEdit);

        // Leaderboard - players online
        pnLauncher.getChildren().add(playersOnline);
        playersOnline.setFill(Color.WHITE);
        playersOnline.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        playersOnline.setLayoutX(32);
        playersOnline.setLayoutY(64);

        // Leaderboard - Players
        pnLauncher.getChildren().add(playerList);
        playerList.setItems(players);
        playerList.setStyle("-fx-font-size:24.0;");
        playerList.setLayoutX(32);
        playerList.setLayoutY(80);


        // Application - Window settings
        stage.setTitle("Epic game launcher");
        stage.setMinWidth(1024);
        stage.setMinHeight(576);
        stage.setMaxWidth(2560);
        stage.setMaxHeight(1440);
        stage.setScene(new Scene(pnLauncher, windowWidth, windowHeight));
        stage.show();

        // Testcode
        playersOnline.setText(players.size() + " spelers online");

        // Update window resolution triggers
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                windowWidth = newSceneWidth.intValue();
                relocatePanes();
            }
        });
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                windowHeight = newSceneHeight.intValue();
                relocatePanes();
            }
        });

        // Trigger relocators
        stage.setWidth(windowWidth+1);
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight+1);
        stage.setHeight(windowHeight);
    }

    private void buttonActions(){
        // Logout button
        btnChangeName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (controller.getLoginName() == null) {
                    String loginStatus = controller.login((usernameEdit.getCharacters().toString()));
                    if (loginStatus == "ok") {
                        usernameEdit.setDisable(true);
                        bgUsernameUse.setImage(bgUsernameOk);
                        controller.setLoginName(usernameEdit.getCharacters().toString());
                    } else if (loginStatus == "short") {
                        JOptionPane.showConfirmDialog(null, "Deze naam is te kort.", "Waarschuwing", JOptionPane.CLOSED_OPTION);
                    }
                } else {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Om uw naam te wijzigen moet u eerst de lobby verlaten. Wilt u de lobby nu verlaten?","Waarschuwing",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        // Saving code here
                        controller.logout();
                        usernameEdit.setDisable(false);
                        bgUsernameUse.setImage(bgUsernameEdit);
                        controller.setLoginName(null);
                        //nameSet = false;
                    }
                }
            }
        });
    }


    // Executed when client has connected
    public void connected(Boolean isConnected){
        if (isConnected == true){

        }
    }

    private void relocatePanes() {
        setUsernamePosition();
        setLeaderboardPosition();
    }

    private void setUsernamePosition(){
        // Username - background
        bgUsernameUse.setLayoutX(-3300+windowWidth); // Top right

        // Username - change name
        btnChangeName.setLayoutX(windowWidth-80);
        btnChangeName.setMinHeight(50);
        btnChangeName.setLayoutY(8);

        // Username - edit field
        usernameEdit.setLayoutY(8);
        usernameEdit.setLayoutX(windowWidth-400);
        usernameEdit.setMaxWidth(312);

    }

    private void setLeaderboardPosition(){
        // Leaderboard - playerList
        playerList.setPrefHeight(windowHeight-(80+64));
        playerList.setPrefWidth(480);
    }



    public static void main(String[] args) {
        launch(args);
    }

//    public void update() {
//        Platform.runLater(() -> {
//
//        });
//    }
}
