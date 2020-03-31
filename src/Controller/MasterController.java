package Controller;

import AI.TicTacToeAI;
import Exceptions.MoveException;
import Exceptions.WrongAIException;
import Games.GameName;
import Games.TicTacToe;
import Model.MasterModel;
import Model.Model;
import Model.GameItems.*;
import View.MasterView;
import View.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class MasterController extends Controller {
    MasterModel model;
    MasterView view;
    private ServerCommunication serverCommunication;

    public MasterController() {
        serverCommunication = new ServerCommunication();
    }

    public void start(Stage stage) {
        // Create control panel
        view.start(stage);

        serverCommunication.connect();
        view.connected(true);
        // First read should be empty because garbage 2 lines
        try {
            serverCommunication.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        String originalInput = null;
        try {
            originalInput = serverCommunication.read();
        } catch (IOException e) {
            System.out.println("No connecting with server:handleInput");
        }
        if (originalInput != null) {
            System.out.println(originalInput);
            String inputLowerCase = originalInput.toLowerCase();
            String[] words = inputLowerCase.split(" ");
            int totalLetters = 0;
            if (words.length > 2) {
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
                            // this would not ever happen
                            break;
                        case "game":
                            System.out.println("Game message");
                            TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                            switch (words[2]) {
                                case "match":
                                    System.out.println("Match message: " + inputLowerCase.substring(totalLetters));
                                    break;
                                case "yourturn":
                                    System.out.println("Your turn");
                                    int ourMove = model.getGame().getNextMove();
                                    fieldStatus.setCircle();
                                    model.getGame().getModel().setFieldStatus(ourMove, fieldStatus);
                                    serverCommunication.move(ourMove);
                                    break;
                                case "loss":
                                    System.out.println("You lost");
                                    break;
                                case "win":
                                    System.out.println("You won");
                                    break;
                                case "draw":
                                    // DRAW
                                    System.out.println("Draw");
                                    break;
                                case "move":
                                    System.out.println("Move has been done: " + inputLowerCase.substring(totalLetters));
                                    if (!inputLowerCase.substring(totalLetters).contains(model.getLoginName())) {
                                        int opponentMove = Integer
                                                .parseInt(inputLowerCase.substring(totalLetters).substring(
                                                        inputLowerCase.substring(totalLetters).lastIndexOf("move: ")
                                                                + "move: ".length() + 1,
                                                        inputLowerCase.substring(totalLetters).lastIndexOf("move: ")
                                                                + "move: ".length() + 2));
                                        fieldStatus.setCross();
                                        model.getGame().getModel().setFieldStatus(opponentMove, fieldStatus);
                                    }

                            }
                            // GAME INFO
                            break;
                        case "challenge":
                            // CHALLENGE INFO
                            break;
                        case "playerlist":
                            // Send whole playerlist and filter harmful data
                            String[] playerNames = originalInput.substring(16, originalInput.length() - 2)
                                    .split("\", ");
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
        serverCommunication.login(name);
        model.setLoginName(name);
        // subscribe(GameName.TICTACTOE);
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

    public void challengeRival(String rivalName, String gameName) {
        serverCommunication.challengeRival(rivalName, gameName);
    }

    public void getGameList() {
        serverCommunication.getGameList();
    }

    public void getPlayerList() {
        serverCommunication.getPlayerList();
    }

    public String getLoginName() {
        return model.getLoginName();
    }

    public void setLoginName(String loginName) {
        model.setLoginName(loginName);
    }

    public void createGame(GameName gameName) {
        if (gameName == GameName.TICTACTOE) {
            TicTacToe ticTacToe = new TicTacToe();
            try {
                ticTacToe.setAI(new TicTacToeAI(ticTacToe.getModel()));
            } catch (WrongAIException e) {
                System.out.println("WRONG AI");
            }
        }
    }

    public String getRivalName() {
        return model.getRivalName();
    }

    public void setRivalName(String rivalName) {
        model.setRivalName(rivalName);
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
