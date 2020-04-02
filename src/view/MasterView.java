package view;

import controller.MasterController;
import games.GameName;
import javafx.application.Application;
import model.MasterModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    // Leaderboard - playerlist
    ListView<String> playerList = new ListView<String>();
    ObservableList<String> players = FXCollections.observableArrayList ("");
    // Leaderboard - player options list
    ListView<String> lstPlayersOptions = new ListView<String>();
    ObservableList<String> datPlayersOptions = FXCollections.observableArrayList ("Oei");
    Text headPlayersOptions = new Text("ERROR");

    // Quick play
    Button btnQuickPlay = new Button("Nu spelen!");

    public void start(Stage masterStage) {
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

        // Leaderboard - Playerslist
        pnLauncher.getChildren().add(playerList);
        playerList.setItems(players);
        playerList.setStyle("-fx-font-size:24.0;");
        playerList.setLayoutX(32);
        playerList.setLayoutY(80);

        // Leaderboard - Challenge
        pnLauncher.getChildren().add(lstPlayersOptions);
        lstPlayersOptions.setItems(datPlayersOptions);
        lstPlayersOptions.setStyle("-fx-font-size:24.0;");
        lstPlayersOptions.setVisible(false);
        lstPlayersOptions.setLayoutY(80+48);

        // Leaderboard - Challenge Head
        pnLauncher.getChildren().add(headPlayersOptions);
        headPlayersOptions.setFill(Color.WHITE);
        headPlayersOptions.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headPlayersOptions.setLayoutX(480+64);
        headPlayersOptions.setLayoutY(80+32);
        headPlayersOptions.setVisible(false);

        // Quick player
        pnLauncher.getChildren().add(btnQuickPlay);
        btnQuickPlay.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        btnQuickPlay.setVisible(false);

        // Application - Window settings
        players.clear();
        masterStage.setTitle("Epic game launcher");
        masterStage.setMinWidth(1024);
        masterStage.setMinHeight(576);
        masterStage.setMaxWidth(2560);
        masterStage.setMaxHeight(1440);
        masterStage.setScene(new Scene(pnLauncher, windowWidth, windowHeight));
        masterStage.show();

        // Update window resolution triggers
        masterStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                windowWidth = newSceneWidth.intValue();
                relocatePanes();
            }
        });
        masterStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                windowHeight = newSceneHeight.intValue();
                relocatePanes();
            }
        });

        // Trigger relocators
        masterStage.setWidth(windowWidth+1);
        masterStage.setWidth(windowWidth);
        masterStage.setHeight(windowHeight+1);
        masterStage.setHeight(windowHeight);
    }

    private void buttonActions(){
        // Player name clicked
        playerList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (playerList.getSelectionModel().getSelectedItem().equals(usernameEdit.getCharacters().toString())) {
                        headPlayersOptions.setVisible(true);
                        headPlayersOptions.setText("Je kan jezelf niet uitdagen");
                        lstPlayersOptions.setVisible(false);
                        return;
                    }
                    headPlayersOptions.setVisible(true);
                    controller.setRivalName(playerList.getSelectionModel().getSelectedItem());
                    headPlayersOptions.setText(playerList.getSelectionModel().getSelectedItem() + " uitdagen voor een potje");
                    controller.getGameList();
                    lstPlayersOptions.setVisible(true);
                } catch(Exception e) {
                    System.out.println("playerList:empty");
                }
            }
        });

        // Game name clicked
        lstPlayersOptions.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                headPlayersOptions.setText(playerList.getSelectionModel().getSelectedItem() + " is uitgedaagd voor een potje " + lstPlayersOptions.getSelectionModel().getSelectedItem());
                controller.challengeRival(controller.getRivalName(), lstPlayersOptions.getSelectionModel().getSelectedItem());
            }
        });

        // Logout button
        btnChangeName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (controller.getLoginName() == null) {
                    String loginStatus = controller.login((usernameEdit.getCharacters().toString()));
                    System.out.println(loginStatus);
                    if (loginStatus == "ok") {
                        usernameEdit.setDisable(true);
                        bgUsernameUse.setImage(bgUsernameOk);
                        controller.setLoginName(usernameEdit.getCharacters().toString());
                        controller.getPlayerList();
                        btnQuickPlay.setVisible(true);
                    } else if (loginStatus == "short") {
                        JOptionPane.showConfirmDialog(null, "Deze naam is niet lang genoeg", "Waarschuwing", JOptionPane.CLOSED_OPTION);
                    }
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Om uw naam te wijzigen moet u eerst de lobby verlaten. Wilt u de lobby nu verlaten?","Waarschuwing",JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        // Saving code here
                        controller.logout();
                        usernameEdit.setDisable(false);
                        bgUsernameUse.setImage(bgUsernameEdit);
                        controller.setLoginName(null);
                        controller.setRivalName(null);
                        players.clear();
                        playersOnline.setText("Voer rechtsboven een naam in");
                        lstPlayersOptions.setVisible(false);
                        headPlayersOptions.setVisible(false);
                    }
                }
            }
        });

        // Quickplay
        btnQuickPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                headPlayersOptions.setVisible(false);
                lstPlayersOptions.setVisible(false);

                int dialogResult = JOptionPane.showConfirmDialog (null, "Druk op Yes voor reversi. Druk op No voor tic-tac-toe","Tijdelijke popup",JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    playersOnline.setText("Subscribed to Reversi");
                    controller.subscribe(GameName.REVERSI);
                }
                if(dialogResult == JOptionPane.NO_OPTION){
                    playersOnline.setText("Subscribed to Tic-tac-toe");
                    controller.subscribe(GameName.TICTACTOE);
                }

                // If any selected disable challenging
                if (dialogResult == JOptionPane.YES_OPTION || dialogResult == JOptionPane.NO_OPTION) {
                    headPlayersOptions.setVisible(false);
                    playersOnline.setVisible(true);
                    playerList.setVisible(false);
                }
            }
        });

    }


    // Executed when client has connected
    public void connected(Boolean isConnected){
        if (isConnected == true){

        }
    }

    // Update leaderboard
    public void updatePlayerboard(ObservableList<String> newPlayerlist){
        Platform.runLater(() -> {
            players.clear();
            players.addAll(newPlayerlist);
            playersOnline.setText(players.size() + " spelers online");
        });
    }

    // Update leaderboard
    public void updatePlayerboardChallenges(ObservableList<String> newGamelist){
        Platform.runLater(() -> {
            datPlayersOptions.clear();
            datPlayersOptions.addAll(newGamelist);
            //playersOnline.setText(players.size() + "DEBUG: Gamelist");
        });
    }

    private void relocatePanes() {
        setUsernamePosition();
        setLeaderboardPosition();
        setQuickPlayPosition();
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

        // Leaderboard - Challenge
        lstPlayersOptions.setLayoutX(480+64);
        lstPlayersOptions.setPrefWidth(320);
    }

    private void setQuickPlayPosition(){
        // Quick player
        btnQuickPlay.setLayoutX(windowWidth-256);
        btnQuickPlay.setLayoutY(windowHeight-128);

    }




//    public void update() {
//        Platform.runLater(() -> {
//
//        });
//    }
}
