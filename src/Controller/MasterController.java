package Controller;

import AI.TicTacToeAI;
import Exceptions.MoveException;
import Exceptions.WrongAIException;
import Games.GameName;
import Games.TicTacToe;
import Model.MasterModel;
import Model.Model;
import Model.TicTacToeItems.FieldStatus;
import View.MasterView;
import View.View;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.HashMap;

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

                try {
                    handleInput();
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            }
        });
        handleThread.start();


    }


    private void handleInput() throws MoveException {
        String input = serverCommunication.read();
        if(input != null) {
            input = input.toLowerCase();
            String[] words = input.split(" ");
            int totalLetters = 0;
            if(words.length > 2) {
                totalLetters = words[0].length() + words[1].length() + words[2].length() + 3;
            }
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
                            System.out.println("Game message");
                            switch (words[2]) {
                                case "match":
                                    System.out.println("Match message: " + input.substring(totalLetters));
                                    break;
                                case "yourturn":
                                    System.out.println("Your turn");
                                    int ourMove = model.getGame().getNextMove();
                                    model.getGame().getModel().setFieldStatus(ourMove, FieldStatus.CIRCLE);
                                    serverCommunication.move(ourMove);
                                    break;
                                case "loss":
                                    System.out.println("You lost");
                                    break;
                                case "win":
                                    System.out.println("You won");
                                    break;
                                case "draw":
                                    //DRAW
                                    System.out.println("Draw");
                                    break;
                                case "move":
                                    System.out.println("Move has been done: " + input.substring(totalLetters));
                                    if(!input.substring(totalLetters).contains(model.getLoginName())) {
                                        int opponentMove = Integer.parseInt(input.substring(totalLetters).substring(input.substring(totalLetters).lastIndexOf("move: ") + "move: ".length()+1, input.substring(totalLetters).lastIndexOf("move: ") + "move: ".length()+2));
                                        model.getGame().getModel().setFieldStatus(opponentMove, FieldStatus.CROSS);
                            }

                            }
                            //GAME INFO
                            break;
                        case "challenge":
                            //CHALLENGE INFO
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
        serverCommunication.login(name);
        model.setLoginName(name);
        subscribe(GameName.TICTACTOE);
        return serverCommunication.login(name);
    }

    public void subscribe(GameName game) {
        TicTacToe ticTacToe = new TicTacToe();
        try {
            ticTacToe.setAI(new TicTacToeAI(ticTacToe.getModel()));
        } catch (Exception e) {
            System.out.println("FUCK");
        }
        model.setGame(ticTacToe);
        serverCommunication.subscribe(game);
    }

    public ObservableList<String> getPlayerList() {
        return serverCommunication.getPlayerList();
    }

    public String getLoginName() {
        return model.getLoginName();
    }

    public void setLoginName(String loginName) {model.setLoginName(loginName); }

    public void createGame(GameName gameName) {
        if(gameName == GameName.TICTACTOE) {
            TicTacToe ticTacToe = new TicTacToe();
            try {
                ticTacToe.setAI(new TicTacToeAI(ticTacToe.getModel()));
            } catch (WrongAIException e) {
                System.out.println("WRONG AI");
            }
        }
    }

    @Override
    public void addView(View view) {
        this.view = (MasterView) view;
    }

    @Override
    public void addModel(Model model) {
        this.model = (MasterModel) model;
    }
}
