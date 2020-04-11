package view;

import controller.MasterController;
import games.Game;
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

public class MasterView extends View {
    MasterController controller;

    Scene scene;
    public MasterView(MasterController controller) {
        this.controller = controller;
    }

    /** Application settings **/
    // Application - window settings
    int windowWidth = 1280; // Default screen size
    int windowHeight = 720;
    Pane pnLauncher = new Pane(); // Main pane


    /** Topbar username **/
    // Username - buttons
    Button btnChangeName = new Button("");

    // Username - Text fields
    TextField usernameEdit = new TextField();

    // Username - Edit name button
    ImageView imgUsernameEdit = new ImageView("File:assets/launcher/nameEdit.png");
    ImageView imgUsernameLogin = new ImageView("File:assets/launcher/nameLogin.png");

    // Username backgrounds
    Image bgUsernameOk = new Image("File:assets/launcher/bgUsernameOk.png");
    Image bgUsernameEdit = new Image("File:assets/launcher/bgUsernameEdit.png");
    Image bgUsernameError = new Image("File:assets/launcher/bgUsernameError.png");
    ImageView bgUsernameUse = new ImageView("File:assets/launcher/bgUsernameError.png");
    Rectangle bgUsernameLine = new Rectangle(0, 0,   windowWidth,   12);


    /** Playerboard **/
    // Playerboard - Invitation icon
    Image icoInvitationRecv = new Image("File:assets/launcher/iconInvitationRecv.png");
    Image icoInvitationSend = new Image("File:assets/launcher/iconInvitationSend.png");

    // Playerboard - listview playerlist (LEFT)
    Text headPlayersOnline = new Text("Server niet gevonden");
    ListView<String> lstPlayerList = new ListView<String>();
    ObservableList<String> players = FXCollections.observableArrayList ("");


    /** Playerboard challenges/options **/
    // Playerboard challenges/options (RIGHT)
    Text headPlayersOptions = new Text("");
    ListView<String> lstPlayersOptions = new ListView<String>();
    ObservableList<String> datPlayersOptions = FXCollections.observableArrayList ("");


    /** Quick play **/
    // Quick play
    Button btnQuickPlay = new Button("Nu spelen!");


    /** Gamemode selection screen **/
    // Gamemode selection screen - Headers
    Text txtOnline = new Text("Online spelen");
    Text txtOffline = new Text("Offline spelen");
    Text txtSpellen = new Text("Spellen");

    // Gamemode selection screen - Game options
    ListView<String> lstGameSelectOptions = new ListView<String>();

    /** Gamemode selection screen -- Mode buttons **/
    // P1 VS P2 - online
    Button btnP1Online = new Button("");
    ImageView imgP1Online = new ImageView("File:assets/launcherStart/modeP1Online.png");

    // AI VS P2 - online
    Button btnAIOnline = new Button("");
    ImageView imgAIOnline = new ImageView("File:assets/launcherStart/modeAIOnline.png");

    // P1 VS P2 - offline
    Button btnP1vsP2Offline = new Button("");
    ImageView imgP1vsP2Offline = new ImageView("File:assets/launcherStart/modeP1vsP2Offline.png");

    // P1 VS AI - offline
    Button btnP1vsAIOffline = new Button("");
    ImageView imgP1vsAIOffline = new ImageView("File:assets/launcherStart/modeP1vsAIOffline.png");

    // AI VS AI - offline
    Button btnAIvsAIOffline = new Button("");
    ImageView imgAIvsAIOffline = new ImageView("File:assets/launcherStart/modeAIvsAIOffline.png");


    public void start(Stage masterStage) {
        // Put triggers on buttons
        buttonWatchdogs();

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

        // Playerboard - players online
        pnLauncher.getChildren().add(headPlayersOnline);
        headPlayersOnline.setFill(Color.WHITE);
        headPlayersOnline.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headPlayersOnline.setLayoutX(32);
        headPlayersOnline.setLayoutY(64);

        // Playerboard - Playerslist
        pnLauncher.getChildren().add(lstPlayerList);
        lstPlayerList.setItems(players);
        lstPlayerList.setStyle("-fx-font-size:24.0;");
        lstPlayerList.setLayoutX(32);
        lstPlayerList.setLayoutY(80);

        // Playerboard - Challenge
        pnLauncher.getChildren().add(lstPlayersOptions);
        lstPlayersOptions.setItems(datPlayersOptions);
        lstPlayersOptions.setStyle("-fx-font-size:24.0;");
        lstPlayersOptions.setVisible(false);
        lstPlayersOptions.setLayoutY(80+48);
        lstPlayersOptions.setPrefHeight(48*2+1);
        lstPlayersOptions.setPrefWidth(320);

        // Playerboard - Challenge Head
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

        // Game mode selection screen - Game options header
        pnLauncher.getChildren().add(txtSpellen);
        txtSpellen.setVisible(false);
        txtSpellen.setFill(Color.WHITE);
        txtSpellen.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        txtSpellen.setLayoutX(64);
        txtSpellen.setLayoutY(96);

        // Game mode selection screen - P1 vs Online
        pnLauncher.getChildren().add(btnP1Online);
        btnP1Online.setVisible(false);
        btnP1Online.setLayoutY(128);
        btnP1Online.setGraphic(imgP1Online);
        btnP1Online.setId("btnP1Online");

        // Game mode selection screen - AI vs Online
        pnLauncher.getChildren().add(btnAIOnline);
        btnAIOnline.setVisible(false);
        btnAIOnline.setLayoutY(128);
        btnAIOnline.setGraphic(imgAIOnline);
        btnAIOnline.setId("btnP1Online");

        // Gamemode selection screen - Game options header Offline
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

    private void buttonWatchdogs(){
        // Player name clicked
        lstPlayerList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (controller.getLoginName() == null) {
                        headPlayersOptions.setVisible(true);
                        headPlayersOptions.setText("Je moet ingelogt zijn om iemand uit te dagen");
                        lstPlayersOptions.setVisible(false);
                        return;
                    }

                    String selectedName = lstPlayerList.getSelectionModel().getSelectedItem();

                    // Check if playername is valid
                    if (selectedName == null || selectedName.isEmpty()){
                        return;
                    }
                    headPlayersOptions.setVisible(true);
                    //controller.setRivalName(lstPlayerList.getSelectionModel().getSelectedItem());
                    lstPlayerList.setId(selectedName);

                    // Check if invite is available
                    if (controller.checkChallenger(lstPlayerList.getId())){
                        updatePlayersOptions();
                    } else {
                        // Add normal games to gamelist
                        updatePlayerboardChallenges(FXCollections.observableArrayList(GameName.REVERSI.label, GameName.TICTACTOE.label));
                        headPlayersOptions.setText(selectedName + " uitdagen voor:");
                    }
                    lstPlayersOptions.setVisible(true);
                } catch(Exception e) {
                    System.out.println("lstPlayerList:empty");
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

                // Accept rival invite
                if (lstPlayersOptions.getSelectionModel().getSelectedItem().length()>10) {
                    if (lstPlayersOptions.getSelectionModel().getSelectedItem().substring(lstPlayersOptions.getSelectionModel().getSelectedItem().length() - 10).equals("accepteren")) {
                        challengeAccept(lstPlayerList.getId());
                        return;
                    }
                }

                // Challenge rival
                headPlayersOptions.setText(lstPlayerList.getSelectionModel().getSelectedItem() + " is uitgedaagd voor " + lstPlayersOptions.getSelectionModel().getSelectedItem());
                updatePlayerboardImages();
                controller.challengeRival(lstPlayerList.getSelectionModel().getSelectedItem(), lstPlayersOptions.getSelectionModel().getSelectedItem());

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
                    updatePlayerboardChallenges(FXCollections.observableArrayList(GameName.REVERSI.label, GameName.TICTACTOE.label));
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
                controller.subscribeServer(lstGameSelectOptions.getSelectionModel().getSelectedItem());
                controller.subscribe(lstGameSelectOptions.getSelectionModel().getSelectedItem());
            }
        });

        // AI VS P2 -- online
        btnAIOnline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> AI VS Online");
                controller.setGameSettings(true, true,false);
                controller.subscribeServer(lstGameSelectOptions.getSelectionModel().getSelectedItem());
                controller.subscribe(lstGameSelectOptions.getSelectionModel().getSelectedItem());

        }
        });

        // P1 VS P2 -- offline
        btnP1vsP2Offline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> P1 VS P2");
                controller.setGameSettings(false, false,false);
                controller.subscribe(lstGameSelectOptions.getSelectionModel().getSelectedItem());
            }
        });

        // P1 VS AI -- offline
        btnP1vsAIOffline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> P1 VS AI");
                controller.setGameSettings(false, true, false);
                controller.subscribe(lstGameSelectOptions.getSelectionModel().getSelectedItem());

            }
        });

        // AI VS AI -- offline
        btnAIvsAIOffline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button pressed -> AI VS AI");
                controller.setGameSettings(false, true, true);
                controller.subscribe(lstGameSelectOptions.getSelectionModel().getSelectedItem());
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
    public void updatePlayerboard(ObservableList<String> newlstPlayerList){
        Platform.runLater(() -> {
            players.clear();
            players.addAll(newlstPlayerList);
            headPlayersOnline.setText(players.size() + " andere spelers online");
            NoConnection(false);

            // Update images
            updatePlayerboardImages();
        });
    }

    public void updatePlayerboardImages() {
        // Add invitation image
        lstPlayerList.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(controller.checkChallenger(name)) {
                        imageView.setImage(icoInvitationRecv);
//                        if (lstPlayerList.getId().equals(controller.getRivalName())){
                            updatePlayersOptions();
//                        }
                    } else if(name.equals(controller.getRivalName())) {
                        imageView.setImage(icoInvitationSend);
                    }
                    setText(name + " ");
                    setGraphic(imageView);
                }
            }
        });
    }

    public void updatePlayersOptions(){
        String playerID = lstPlayerList.getId();
        if (playerID == null || playerID.isEmpty()){
            return;
        }
        String gameTitle = controller.checkChallengerGametype(playerID);
        //System.out.println("playerID "+playerID+ " gameTitle "+ gameTitle);
        if (gameTitle != null && !gameTitle.isEmpty()) {
            headPlayersOptions.setText(playerID + " wil " + gameTitle + " spelen");
            if (gameTitle.equals(GameName.REVERSI.label)) {
                updatePlayerboardChallenges(FXCollections.observableArrayList(GameName.REVERSI.label + " → accepteren", GameName.TICTACTOE.label));
            } else if (gameTitle.equals(GameName.TICTACTOE.label)) {
                updatePlayerboardChallenges(FXCollections.observableArrayList(GameName.REVERSI.label, GameName.TICTACTOE.label + " → accepteren"));
            }
        }

    }

    // Update leaderboard
    public void updatePlayerboardChallenges(ObservableList<String> newGamelist){
        Platform.runLater(() -> {
            datPlayersOptions.clear();
            datPlayersOptions.addAll(newGamelist);
            //headPlayersOnline.setText(players.size() + "DEBUG: Gamelist");
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
        int lstPlayerListWidth = windowWidth/3;
        if (lstPlayerListWidth < 320) {
            lstPlayerListWidth = 320;
        }

        // Leaderboard - lstPlayerList
        lstPlayerList.setPrefHeight(windowHeight-(80+64));
        lstPlayerList.setPrefWidth(lstPlayerListWidth);

        // Leaderboard - Challenge
        lstPlayersOptions.setLayoutX(lstPlayerListWidth+64);
        headPlayersOptions.setLayoutX(lstPlayerListWidth+64);
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
        return lstPlayerList.getSelectionModel().getSelectedIndex();
    }

    public void setNameSelected(int sel) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lstPlayerList.getSelectionModel().select(sel);
                //lstPlayerList.setId(lstPlayerList.getSelectionModel().getSelectedItem());

                // Check if rivalname is set
//                if (controller.getRivalName() == null) {
//                    lstPlayerList.setId(null);
//                    return;
//                }

                // Check if rivalname is at same position in the list
                if (lstPlayerList.getId() == null || lstPlayerList.getId().isEmpty()) {
                    // Rivalname has left the game
                    //kickRival();

                } else if (lstPlayerList.getId().equals( lstPlayerList.getSelectionModel().getSelectedItem() ) == false) {
                    for (int i=0; i<players.size(); i++) {
                        lstPlayerList.getSelectionModel().select(i);
                        if (lstPlayerList.getSelectionModel().getSelectedItem().equals(controller.getRivalName())) {
                            lstPlayerList.setId(lstPlayerList.getSelectionModel().getSelectedItem());
                            return;
                        }
                    }
                    // Rivalname has left the game
                    //kickRival();
                }

            }
        });
    }

    private void kickRival(){
        // Rivalname has left the game
        controller.setRivalName(null);
        lstPlayerList.getSelectionModel().select(null);
        lstPlayerList.setId(null);
        enableChallengeOptions(false);
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

    private void selectGameModeScreen(boolean state){
        // Clear rival name
        controller.setRivalName(null);
        lstPlayerList.getSelectionModel().select(-1);
        lstPlayerList.setId(null);

        if (state) {
            enableChallengeOptions(!state);
        }

        // Hide all unrelated items
        lstPlayerList.setVisible(!state);
        headPlayersOnline.setVisible(!state);

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
                usernameEdit.setDisable(false);
            } else {
                bgUsernameLine.setFill(Color.rgb(69, 149, 35));
                bgUsernameUse.setImage(bgUsernameOk);
                usernameEdit.setDisable(true);
            }
        }
        // Set button availability in correct state
        btnChangeName.setDisable(state);
    }

    public void challengeAccept(String challengeName){

        if (challengeName == null || challengeName.isEmpty()){
            enablePlayersOptions(false);
        }

        int getChallengeNumber = controller.getChallengeNumber(challengeName);

        if (getChallengeNumber != -1) {
            enablePlayersOptions(false);
            controller.challengeAccept(getChallengeNumber);
        } else {
            System.out.println("Challenge got deleted try again "+getChallengeNumber + " name " + challengeName);
        }
    }

    public void enablePlayersOptions(boolean state) {
        lstPlayersOptions.setVisible(state);
        headPlayersOptions.setVisible(state);
    }

}
