package Controller;

import Games.TicTacToe;
import Model.UserModel;
import View.UserView;
import javafx.stage.Stage;

public class MasterController extends Controller {
    private ServerCommunication serverCommunication;
    private UserController userController;
    public MasterController(Stage stage)  {
        serverCommunication = new ServerCommunication();

        UserModel userModel = new UserModel();
        UserController userController = new UserController(userModel, serverCommunication);
        UserView userView = new UserView(userModel, userController);
        userView.start(stage);



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
}
