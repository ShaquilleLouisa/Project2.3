package view;

import controller.MasterController;
import games.GameName;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;


public class MasterView extends View {
    MasterController controller;

    Scene scene;
    public MasterView() {

    }

    public MasterView(MasterController controller) {
        this.controller = controller;
    }

    // Username - buttons
    Button btnChangeName = new Button("");

    // Username - Text fields
    TextField usernameEdit = new TextField();

    // Username - Edit name button
    ImageView imgUsernameEdit = new ImageView("File:assets/launcher/nameEdit.png");
    ImageView imgUsernameLogin = new ImageView("File:assets/launcher/nameLogin.png");

    // Application - window settings
    int windowWidth = 1280; // Default screen size
    int windowHeight = 720;
    Pane pnLauncher = new Pane(); // Main pane

    // Username backgrounds
    Image bgUsernameOk = new Image("File:assets/launcher/bgUsernameOk.png");
    Image bgUsernameEdit = new Image("File:assets/launcher/bgUsernameEdit.png");
    Image bgUsernameError = new Image("File:assets/launcher/bgUsernameError.png");
    ImageView bgUsernameUse = new ImageView("File:assets/launcher/bgUsernameError.png");
    Rectangle bgUsernameLine = new Rectangle(0, 0,   windowWidth,   12);


    // Leaderboard - Textfields
    Text playersOnline = new Text("Server niet gevonden"); // Lange versie Er kan geen verbinding worden gemaakt met de server

    // Leaderboard - playerlist
    ListView<String> playerList = new ListView<String>();
    ObservableList<String> players = FXCollections.observableArrayList ("");
    // Leaderboard - player options list
    ListView<String> lstPlayersOptions = new ListView<String>();
    ObservableList<String> datPlayersOptions = FXCollections.observableArrayList ("");
    Text headPlayersOptions = new Text("ERROR");

    // Quick play
    Button btnQuickPlay = new Button("Nu spelen!");

    // Gamemode selection screen - Online
    Text txtOnline = new Text("Online spelen");

    // Gamemode selection screen - Online
    Text txtSpellen = new Text("Spellen");

    // P1 VS P2 - online
    Button btnP1Online = new Button("");
    ImageView imgP1Online = new ImageView("File:assets/launcherStart/modeP1Online.png");

    // AI VS P2 - online
    Button btnAIOnline = new Button("");
    ImageView imgAIOnline = new ImageView("File:assets/launcherStart/modeAIOnline.png");

    // Gamemode selection screen - Offline
    Text txtOffline = new Text("Offline spelen");

    // P1 VS P2 - offline
    Button btnP1vsP2Offline = new Button("");
    ImageView imgP1vsP2Offline = new ImageView("File:assets/launcherStart/modeP1vsP2Offline.png");

    // P1 VS COM - offline
    Button btnP1vsAIOffline = new Button("");
    ImageView imgP1vsAIOffline = new ImageView("File:assets/launcherStart/modeP1vsAIOffline.png");

    // COM VS COM - offline
    Button btnAIvsAIOffline = new Button("");
    ImageView imgAIvsAIOffline = new ImageView("File:assets/launcherStart/modeAIvsAIOffline.png");

    ListView<String> lstGameSelectOptions = new ListView<String>();

    public void start(Stage masterStage) {
        buttonActions();
        // Pane - background color
        pnLauncher.setStyle("-fx-background-color: #262626;"); // Default background color

        // Username - Background
        pnLauncher.getChildren().add(bgUsernameUse);
        pnLauncher.getChildren().add(bgUsernameLine);
        bgUsernameLine.setFill(Color.rgb(183, 64, 31));

        // Username - edit field
        pnLauncher.getChildren().add(usernameEdit);
        usernameEdit.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        usernameEdit.setDisable(true);

        // Username - edit name button
        pnLauncher.getChildren().add(btnChangeName);
        btnChangeName.setDisable(true);
        btnChangeName.setId("btnChangeName");
        //ImageView btnUsernameEdit = new ImageView("File:assets/launcher/nameEdit.png");
        btnChangeName.setStyle("-fx-background-color: #262626;");
        btnChangeName.setGraphic(imgUsernameLogin);

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
        btnQuickPlay.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btnQuickPlay.setId("btnQuickPlay");
        btnQuickPlay.setMinWidth(192);

        // Gamemode selection screen - Online
        pnLauncher.getChildren().add(txtOnline);
        txtOnline.setVisible(false);
        txtOnline.setFill(Color.WHITE);
        txtOnline.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        txtOnline.setLayoutX(128);
        txtOnline.setLayoutY(96);

        // Text spellen - Online
        pnLauncher.getChildren().add(txtSpellen);
        txtSpellen.setVisible(false);
        txtSpellen.setFill(Color.WHITE);
        txtSpellen.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        txtSpellen.setLayoutX(64);
        txtSpellen.setLayoutY(96);

        pnLauncher.getChildren().add(btnP1Online);
        btnP1Online.setVisible(false);
        btnP1Online.setLayoutY(128);
        btnP1Online.setGraphic(imgP1Online);
        btnP1Online.setId("btnP1Online");

        pnLauncher.getChildren().add(btnAIOnline);
        btnAIOnline.setVisible(false);
        btnAIOnline.setLayoutY(128);
        btnAIOnline.setGraphic(imgAIOnline);
        btnAIOnline.setId("btnP1Online");

        // Gamemode selection screen - Offline
        pnLauncher.getChildren().add(txtOffline);
        txtOffline.setVisible(false);
        txtOffline.setFill(Color.WHITE);
        txtOffline.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        txtOffline.setLayoutX(128);

        // btnP1vsP2Offline - offline
        pnLauncher.getChildren().add(btnP1vsP2Offline);
        btnP1vsP2Offline.setVisible(false);
        btnP1vsP2Offline.setGraphic(imgP1vsP2Offline);
        btnP1vsP2Offline.setId("btnP1Online");

        // btnP1vsAIOffline - offline
        pnLauncher.getChildren().add(btnP1vsAIOffline);
        btnP1vsAIOffline.setVisible(false);
        btnP1vsAIOffline.setGraphic(imgP1vsAIOffline);
        btnP1vsAIOffline.setId("btnP1Online");

        // btnAIvsAIOffline - offline
        pnLauncher.getChildren().add(btnAIvsAIOffline);
        btnAIvsAIOffline.setVisible(false);
        btnAIvsAIOffline.setGraphic(imgAIvsAIOffline);
        btnAIvsAIOffline.setId("btnP1Online");

        // Quick player game options
        pnLauncher.getChildren().add(lstGameSelectOptions);
        lstGameSelectOptions.setItems(datPlayersOptions);
        lstGameSelectOptions.setStyle("-fx-font-size:24.0;");
        lstGameSelectOptions.setVisible(false);
        lstGameSelectOptions.setLayoutY(128);
        lstGameSelectOptions.setLayoutX(64);
        lstGameSelectOptions.setPrefWidth(256);

        // Add games to gamelist
        updatePlayerboardChallenges(FXCollections.observableArrayList("Reversi", "Tic-tac-toe"));

        // Application - Window settings
        players.clear();
        scene = new Scene(pnLauncher, windowWidth, windowHeight);
        scene.getStylesheets().add("File:assets/css/buttons.css");
        // Define button actions
        masterStage.setTitle("Epic game launcher");
        masterStage.setMinWidth(1024);
        masterStage.setMinHeight(576);
        masterStage.setScene(scene);
        masterStage.show();

        // Update window resolution triggers
        masterStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                windowWidth = newSceneWidth.intValue();
                relocatePanes();
            }
        });
        masterStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                windowHeight = newSceneHeight.intValue();
                relocatePanes();
            }
        });
        masterStage.maximizedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                Platform.runLater(() -> {
                    windowWidth = (int)masterStage.getWidth();
                    windowHeight = (int)masterStage.getHeight();
                    relocatePanes();
                });
            }
        });

        // Trigger relocators
        masterStage.setWidth(windowWidth+1);
        masterStage.setWidth(windowWidth);
        masterStage.setHeight(windowHeight+1);
        masterStage.setHeight(windowHeight);
    }

    public Scene getScene() {
        return scene;
    }

    private void buttonActions(){
        // Player name clicked
        playerList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (controller.getLoginName() == null) {
                        headPlayersOptions.setVisible(true);
                        headPlayersOptions.setText("Je moet ingelogt zijn om iemand uit te dagen");
                        lstPlayersOptions.setVisible(false);
                        return;
                    }
                    // Check if playername is valid
                    if (playerList.getSelectionModel().getSelectedItem() == null){
                        return;
                    }
                    headPlayersOptions.setVisible(true);
                    controller.setRivalName(playerList.getSelectionModel().getSelectedItem());
                    headPlayersOptions.setText(playerList.getSelectionModel().getSelectedItem() + " uitdagen voor een potje");
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
                // Check if selected game is valid game
                if (lstPlayersOptions.getSelectionModel().getSelectedItem() == null) {
                    return;
                }

                // Challenge rival
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
                    if (loginStatus == "ok") {
                        usernameEdit.setDisable(true);
//                        bgUsernameUse.setImage(bgUsernameOk);
                        controller.setLoginName(usernameEdit.getCharacters().toString());
                        controller.getPlayerList();
                        enableChallengeOptions(false);
                        btnChangeName.setGraphic(imgUsernameEdit);
                        // Update state of online buttons
                        if (lstGameSelectOptions.getSelectionModel().getSelectedItem() != null) {
                            if (controller.getLoginName() != null) {
                                selectGameModeScreenOnlineButtons(false);
                            }
                        }
                    } else if (loginStatus == "short") {
                        //JOptionPane.showConfirmDialog(null, "Deze naam is niet lang genoeg", "Waarschuwing", JOptionPane.CLOSED_OPTION);
                    }
                } else {
                    controller.logout();
                    usernameEdit.setDisable(false);
//                    bgUsernameUse.setImage(bgUsernameEdit);
                    controller.setLoginName(null);
                    controller.setRivalName(null);
                    enableChallengeOptions(false);
                    btnChangeName.setGraphic(imgUsernameLogin);
                    // Update state of online buttons
                    selectGameModeScreenOnlineButtons(true);
                }
                // Update visuals
                NoConnection(false);
            }
        });

        // Quickplay
        btnQuickPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (btnQuickPlay.getText() == "Nu spelen!") {
                    lstGameSelectOptions.getSelectionModel().select(-1);
                    selectGameModeScreen(true);
                    btnQuickPlay.setText("Terug");
                    selectGameModeScreenOnlineButtons(true);
                    selectGameModeScreenOfflineButtons(true);
                } else {
                    selectGameModeScreen(false);
                    btnQuickPlay.setText("Nu spelen!");
                }

                // Update position
                setQuickPlayPosition();
            }
        });

        // P1 VS P2 -- online
        btnP1Online.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> P1 VS Online");
                controller.setGameSettings(true, false,false);
                controller.subscribe(getSelectedGameName());
            }
        });

        // AI VS P2 -- online
        btnAIOnline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> AI VS Online");
                controller.setGameSettings(true, true,false);
                controller.subscribe(getSelectedGameName());
            }
        });

        // P1 VS P2 -- offline
        btnP1vsP2Offline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> P1 VS P2");
                controller.setGameSettings(false, false,false);
                controller.subscribe(getSelectedGameName());
            }
        });

        // P1 VS AI -- offline
        btnP1vsAIOffline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> P1 VS AI");
                controller.setGameSettings(false, true, false);
                controller.subscribe(getSelectedGameName());

            }
        });

        // AI VS AI -- offline
        btnAIvsAIOffline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> AI VS AI");
                controller.setGameSettings(false, true, true);
                controller.subscribe(getSelectedGameName());
            }
        });

        lstGameSelectOptions.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (lstGameSelectOptions.getSelectionModel().getSelectedItem() != null){
                    if (controller.getLoginName() != null) {
                        selectGameModeScreenOnlineButtons(false);
                        selectGameModeScreenOfflineButtons(false);
                        return;
                    }
                    selectGameModeScreenOfflineButtons(false);
                }
            }
        });

    }


    // Executed when client has connected
    public void connected(Boolean isConnected){
        NoConnection(!isConnected);
    }

    // Update leaderboard
    public void updatePlayerboard(ObservableList<String> newPlayerlist){
        Platform.runLater(() -> {
            players.clear();
            players.addAll(newPlayerlist);
            playersOnline.setText(players.size() + " andere spelers online");
            NoConnection(false);
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
        setGameModePosition();
    }

    private void setUsernamePosition(){
        // Username - background
        bgUsernameUse.setLayoutX(windowWidth-480); // Top right

        // Username - change name
        btnChangeName.setLayoutX(windowWidth-80);
        //btnChangeName.setMinHeight(50);
        btnChangeName.setLayoutY(8);

        // Username - edit field
        usernameEdit.setLayoutY(8);
        usernameEdit.setLayoutX(windowWidth-400);
        usernameEdit.setMaxWidth(312);

        // Set line width
        bgUsernameLine.setWidth(windowWidth);

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
        // Get smallest dimension
        int smDimension = windowHeight;
        if (windowWidth < windowHeight){
            smDimension = windowWidth;
        }

        // Quick player
        btnQuickPlay.setPrefWidth(windowWidth/8);
        btnQuickPlay.setLayoutY(windowHeight-128);

        if (btnQuickPlay.getText() == "Terug") {
            btnQuickPlay.setLayoutX(64);
        } else {
            btnQuickPlay.setLayoutX(windowWidth - btnQuickPlay.getWidth() - 64);
        }

    }

    private void setGameModePosition(){
        // Get smallest dimension
        int smDimension = windowHeight;
        if (windowWidth < windowHeight){
            smDimension = windowWidth;
        }

        int gameListWidth = 256;

        txtOffline.setLayoutX(gameListWidth+128);
        txtOnline.setLayoutX(gameListWidth+128);

        // Online mode button
        btnP1Online.setTranslateX(gameListWidth+128);
        btnP1Online.setPrefHeight(smDimension/6);
        btnP1Online.setPrefWidth(smDimension/6);
        imgP1Online.setFitHeight(smDimension/6);
        imgP1Online.setFitWidth(smDimension/6);

        // Online mode button AI
        btnAIOnline.setTranslateX(gameListWidth+128+(smDimension/24*1)+(smDimension/6));
        btnAIOnline.setPrefHeight(smDimension/6);
        btnAIOnline.setPrefWidth(smDimension/6);
        imgAIOnline.setFitHeight(smDimension/6);
        imgAIOnline.setFitWidth(smDimension/6);

        // Offline mode button AI
        txtOffline.setTranslateY(96+96+(smDimension/6));
        btnP1vsP2Offline.setTranslateY(96+128+(smDimension/6));
        btnP1vsP2Offline.setTranslateX(gameListWidth+128);
        btnP1vsP2Offline.setPrefHeight(smDimension/6);
        btnP1vsP2Offline.setPrefWidth(smDimension/6);
        imgP1vsP2Offline.setFitHeight(smDimension/6);
        imgP1vsP2Offline.setFitWidth(smDimension/6);

        // Offline mode button AI
        btnP1vsAIOffline.setTranslateY(96+128+(smDimension/6));
        btnP1vsAIOffline.setTranslateX(gameListWidth+128+(smDimension/24*1)+(smDimension/6));
        btnP1vsAIOffline.setPrefHeight(smDimension/6);
        btnP1vsAIOffline.setPrefWidth(smDimension/6);
        imgP1vsAIOffline.setFitHeight(smDimension/6);
        imgP1vsAIOffline.setFitWidth(smDimension/6);

        // Offline mode button AI
        btnAIvsAIOffline.setTranslateY(96+128+(smDimension/6));
        btnAIvsAIOffline.setTranslateX(gameListWidth+128+(smDimension/24*2)+(smDimension/6)*2);
        btnAIvsAIOffline.setPrefHeight(smDimension/6);
        btnAIvsAIOffline.setPrefWidth(smDimension/6);
        imgAIvsAIOffline.setFitHeight(smDimension/6);
        imgAIvsAIOffline.setFitWidth(smDimension/6);

        lstGameSelectOptions.setPrefHeight(windowHeight*0.5);
    }

    public int getNameSelected() {
        return playerList.getSelectionModel().getSelectedIndex();
    }

    public void setNameSelected(int sel) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playerList.getSelectionModel().select(sel);

                // Check if rivalname is set
                if (controller.getRivalName() == null) {
                    return;
                }

                // Check if rivalname is at same position in the list
                if (controller.getRivalName().equals( playerList.getSelectionModel().getSelectedItem() ) == false) {
                    for (int i=0; i<players.size(); i++) {
                        playerList.getSelectionModel().select(i);
                        if (playerList.getSelectionModel().getSelectedItem().equals(controller.getRivalName())) {
                            return;
                        }
                    }
                    // Rivalname has left the game
                    controller.setRivalName(null);
                    playerList.getSelectionModel().select(null);
                    enableChallengeOptions(false);
                }

            }
        });
    }

    private void enableChallengeOptions(boolean state){
        lstPlayersOptions.setVisible(state);
        headPlayersOptions.setVisible(state);
    }

    private void selectGameModeScreenOnlineButtons(boolean state){
        btnP1Online.setDisable(state);
        btnAIOnline.setDisable(state);
    }

    private void selectGameModeScreenOfflineButtons(boolean state){
        btnP1vsP2Offline.setDisable(state);
        btnP1vsAIOffline.setDisable(state);
        btnAIvsAIOffline.setDisable(state);
    }

    private GameName getSelectedGameName(){
        if (lstGameSelectOptions.getSelectionModel().getSelectedItem().equals("Reversi")) {
            return GameName.REVERSI;
        } else if (lstGameSelectOptions.getSelectionModel().getSelectedItem().equals("Tic-tac-toe")) {
            return GameName.TICTACTOE;
        }
        return null;
    }

    private void selectGameModeScreen(boolean state){
        // Clear rival name
        controller.setRivalName(null);
        playerList.getSelectionModel().select(-1);

        if (state) {
            enableChallengeOptions(!state);
        }

        // Hide all unrelated items
        playerList.setVisible(!state);
        playersOnline.setVisible(!state);

        txtSpellen.setVisible(state);

        // Unhide items - online
        txtOnline.setVisible(state);
        btnP1Online.setVisible(state);
        btnAIOnline.setVisible(state);

        // Offline
        txtOffline.setVisible(state);
        btnP1Online.setVisible(state);
        btnP1vsP2Offline.setVisible(state);
        btnP1vsAIOffline.setVisible(state);
        btnAIvsAIOffline.setVisible(state);
        lstGameSelectOptions.setVisible(state);

    }

    public void NoConnection(boolean state){
        // Make background correct color
        if (state == true) {
            bgUsernameLine.setFill(Color.rgb(183, 64, 31));
            bgUsernameUse.setImage(bgUsernameError);
        } else {
            if (controller.getLoginName() == null) {
                bgUsernameLine.setFill(Color.rgb(187, 168, 47));
                bgUsernameUse.setImage(bgUsernameEdit);
            } else {
                bgUsernameLine.setFill(Color.rgb(69, 149, 35));
                bgUsernameUse.setImage(bgUsernameOk);
            }
        }
        // Set button availability in correct state
        btnChangeName.setDisable(state);
        usernameEdit.setDisable(state);
    }

}
