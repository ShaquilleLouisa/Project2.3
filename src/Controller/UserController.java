package Controller;

import Model.UserModel;
import View.UserView;

public class UserController extends Controller {
    private UserModel model;
    private ServerCommunication serverCommunication;


    UserController(UserModel model, ServerCommunication serverCommunication) {
        this.model = model;
        this.serverCommunication = serverCommunication;
    }

    public void logout() {
        System.out.println("oef");
        serverCommunication.logout();
    }

    public void login(String name) {
        serverCommunication.login(name);
    }

    public void subscribe(String game) {
        if(game.toLowerCase().equals("tic-tac-toe")) {
            model.setGame(GameType.TICTACTOE);
            serverCommunication.subscribe(GameType.TICTACTOE);
        }
        if(game.toLowerCase().equals("reversi")) {
            model.setGame(GameType.REVERSI);
            serverCommunication.subscribe(GameType.REVERSI);
        }
    }
}
