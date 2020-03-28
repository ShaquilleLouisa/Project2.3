package Controller;

import Games.GameName;
import Model.MasterModel;
import Model.Model;
import View.MasterView;
import View.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class MasterController extends Controller {
    MasterModel model;
    MasterView view;
    private ServerCommunication serverCommunication;
    public MasterController() {serverCommunication = new ServerCommunication();}


    public void start(Stage stage) {
        //Create control panel
        view.start(stage);

        serverCommunication.connect();
        view.connected(true);
        //First read should be empty because garbage 2 lines
        serverCommunication.read();


        Thread handleThread = new Thread(() -> {
            while (true) {
                handleInput();
            }
        });
        handleThread.start();


    }


    private void handleInput() {
        String input = serverCommunication.read();
        if(input != null) {
            input = input.toLowerCase();
            String[] words = input.split(" ");
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

    public ObservableList<String> getPlayerList() {
        return serverCommunication.getPlayerList();
    }

    public String getLoginName() {
        return model.getLoginName();
    }

    public void setLoginName(String loginName) {model.setLoginName(loginName); }

    @Override
    public void addView(View view) {
        this.view = (MasterView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (MasterModel) model;
    }
}
