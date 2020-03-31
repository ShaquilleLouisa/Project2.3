package Controller;

import Games.GameName;
import Model.MasterModel;
import Model.Model;
import View.MasterView;
import View.View;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MasterController extends Controller {
    MasterModel model;
    MasterView view;
    private ServerCommunication serverCommunication;

    public MasterController() {
        serverCommunication = new ServerCommunication();
    }


    public void start(Stage stage) {
        //Create control panel
        view.start(stage);

        serverCommunication.connect();
        view.connected(true);
        //First read should be empty because garbage 2 lines
        try {
            serverCommunication.read();
        } catch (
                IOException e) {
            System.out.println("No connecting with server:start");
        }
        System.out.println("Connected");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handleInput();
                //serverCommunication.getPlayerList();
            }
        },0,100);
    }


    private void handleInput() {
        String originalInput = null;
        try {
            originalInput = serverCommunication.read();
        } catch (IOException e) {
            System.out.println("No connecting with server:handleInput");
        }
        if (originalInput != null) {
            String inputLowerCase = originalInput.toLowerCase();
            String[] words = inputLowerCase.split(" ");
            switch (words[0]) {
                case "ok":
                    break;
                case "err":
                    break;
                case "svr":
                    // All server commands
                    switch (words[1]) {
                        case "help":
                            //this would not ever happen
                            break;
                        case "game":
                            //GAME INFO
                            break;
                        case "match":
                            //MATCH INFO
                            break;
                        case "yourturn":
                            break;
                        case "move":
                            //SERVER WONT SAY MOVE this would never happen
                            break;
                        case "challenge":
                            //CHALLENGE INFO
                            break;
                        case "win":
                            //YOU WON
                            break;
                        case "loss":
                            //YOU LOST
                            break;
                        case "draw":
                            //DRAW
                            break;
                        case "playerlist":
                            // Send whole playerlist and filter harmful data
                            String[] playerNames = originalInput.substring(16, originalInput.length() - 2).split("\", ");
                            for (int i = 0; i < playerNames.length; i++) {
                                playerNames[i] = playerNames[i].substring(1);
                            }
                            view.updatePlayerboard(FXCollections.observableArrayList(playerNames));
                            break;
                        case "gamelist":
                            // Send whole playerlist and filter harmful data
                            String[] gameNames = originalInput.substring(14, originalInput.length() - 2).split("\", ");
                            for (int i = 0; i < gameNames.length; i++) {
                                gameNames[i] = gameNames[i].substring(1);
                            }
                            view.updatePlayerboardChallenges(FXCollections.observableArrayList(gameNames));
                            break;

                    }
                default:
                    break;
            }
        }
    }

    public void logout() {
        System.out.println("oef");
        serverCommunication.logout();
    }

    public String login(String name) {
        return serverCommunication.login(name);
    }

    public void subscribe(GameName game) {
        model.setGame(game);
        serverCommunication.subscribe(game);
    }

    public void getGameList() { serverCommunication.getGameList(); }

    public void getPlayerList() {
        serverCommunication.getPlayerList();
    }

    public String getLoginName() {
        return model.getLoginName();
    }

    public void setLoginName(String loginName) { model.setLoginName(loginName); }

    public String getRivalName() {
        return model.getRivalName();
    }

    public void setRivalName(String rivalName) { model.setRivalName(rivalName); }

    @Override
    public void addView(View view) {
        this.view = (MasterView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (MasterModel) model;
    }
}
