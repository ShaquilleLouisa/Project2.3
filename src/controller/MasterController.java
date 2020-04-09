package controller;

import ai.AI;
import ai.OurReversiAI;
import ai.ReversiAI;
import ai.TicTacToeAI;
import exceptions.MoveException;
import exceptions.WrongAIException;
import games.Game;
import games.GameName;
import games.Reversi;
import games.TicTacToe;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MultipleSelectionModel;
import model.MasterModel;
import model.Model;
import model.TicTacToeModel;
import model.gameitems.*;
import view.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MasterController extends Controller {
    MasterModel model;
    MasterView view;
    Stage stage;
    private ServerCommunication serverCommunication;

    public MasterController() {
        serverCommunication = new ServerCommunication();
    }

    public void start(Stage stage) {
        // Create control panel
        this.stage = stage;
        view.start(stage);
        boolean isConnected = serverCommunication.connect();
        view.connected(isConnected);
        // First read should be empty because garbage 2 lines
        if (isConnected) {
            try {
                serverCommunication.read();
            } catch (IOException e) {
                System.out.println("MasterController:read()");
                e.printStackTrace();
            }
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                serverCommunication.getPlayerList();
            }
        }, 0, 10000);

        Thread handleThread = new Thread(() -> {
            while (true) {
                handleInput();
            }
        });
        handleThread.start();

    }

    private void handleInput() {
        String originalInput = null;
        try {
            originalInput = serverCommunication.read();
        } catch (IOException e) {
            System.out.println("No connecting with server:handleInput");
            view.NoConnection(true);
            try {
                Thread.sleep(500);
            } catch (Exception es) {
            }
        }
        if (originalInput != null) {
            String inputLowerCase = originalInput.toLowerCase();
            String[] words = inputLowerCase.split(" ");
            int totalLetters = 0;
            if (words.length > 2) {
                totalLetters = words[0].length() + words[1].length() + words[2].length() + 3;
            }
            System.out.println(inputLowerCase);
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
                            System.out.println(" ");
                            switch (words[2]) {
                                case "challenge":
                                    // Get challenger name using regex and store
                                    String challenger = "";
                                    Pattern p = Pattern.compile("CHALLENGER: \"([^\"]*)\"");
                                    Matcher m = p.matcher(originalInput);
                                    while (m.find()) {
                                        challenger=m.group(1);
                                    }
                                    String challengeNumber = "";
                                    // Get challengenumber using regex and store
                                    p = Pattern.compile("CHALLENGENUMBER: \"([^\"]*)\"");
                                    m = p.matcher(originalInput);
                                    while (m.find()) {
                                        challengeNumber=m.group(1);
                                    }
                                    String gameType = "";
                                    // Get gameType using regex and store
                                    p = Pattern.compile("GAMETYPE: \"([^\"]*)\"");
                                    m = p.matcher(originalInput);
                                    while (m.find()) {
                                        gameType=m.group(1);
                                    }
                                    model.addChallenge(challenger, challengeNumber, gameType);
                                    break;
                                case "match":
                                    System.out.println(" ");
                                    System.out.println("Match message: " + inputLowerCase.substring(totalLetters));
                                    // Get rival name using regex
                                    p = Pattern.compile("OPPONENT: \"([^\"]*)\"");
                                    m = p.matcher(originalInput);
                                    while (m.find()) {
                                        System.out.println("Match rivalname received:" + m.group(1));
                                        model.setRivalName(m.group(1));
                                    }
                                    // Get rival gametype using regex
                                    p = Pattern.compile("GAMETYPE: \"([^\"]*)\"");
                                    m = p.matcher(originalInput);
                                    while (m.find()) {
                                        System.out.println("Match gametype received:" + m.group(1));
                                        // Change scene using m.group(1)
                                        if(model.getGame() == null) {
                                            setGameSettings(true, true,false);
                                            System.out.println(m.group(1));
                                            subscribe(m.group(1));
                                        }
                                    }

                                    break;

                                case "yourturn":
                                    System.out.println(" ");
                                    System.out.println("Your turn");

                                    int ourMove;
                                    if (model.getGame().getModel().isUseAi()) {
//                                        try{
//                                            Thread.sleep(2000);
//                                        } catch (InterruptedException e){
//                                            e.printStackTrace();
//                                        }

                                        ourMove = model.getGame().getNextMove();
                                        if(ourMove != -1) {
                                            //TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                                            //fieldStatus.setCircle();
                                            //model.getGame().getModel().setFieldStatus(ourMove, fieldStatus);
                                            model.getGame().setMove(ourMove, false);
                                            serverCommunication.move(ourMove);
                                        }
                                    } else {
                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            int userMove = model.getGame().getModel().getUserMove();

                                            @Override
                                            public void run() {
                                                int move = model.getGame().getModel().getUserMove();
                                                if (userMove != move) {
                                                    serverCommunication.move(move);
                                                    userMove = move;
                                                }
                                            }
                                        }, 0, 1000);
                                    }

                                    break;
                                case "loss":
                                    System.out.println("You lost");
                                    Platform.runLater(() -> {
                                        model.setGame(null);
                                        stage.setScene(view.getScene());
                                    });
                                    //model.getGame().getView().updateNotification("I'm sorry you lost");
                                    break;
                                case "win":
                                    System.out.println("You won");
                                    Platform.runLater(() -> {
                                        model.setGame(null);
                                        stage.setScene(view.getScene());
                                    });
                                    //model.getGame().getView().updateNotification("Congrats you won");
                                    break;
                                case "draw":
                                    Platform.runLater(() -> {
                                        model.setGame(null);
                                        stage.setScene(view.getScene());
                                    });
                                    // DRAW
                                    System.out.println("Draw");
                                    //model.getGame().getView().updateNotification("Its a draw :|");
                                    break;
                                case "move":
                                    System.out.println(" ");
                                    //System.out.println("inputLowerCase " + words[4]);
                                    System.out.println("Move has been done by opponent: " + inputLowerCase.substring(totalLetters)
                                            + "name: " + getRivalName());
                                    if (words[4].contains(getRivalName().toLowerCase())) {

                                        System.out.println("Waar is deze print ??????"+words[6].substring(1, words[6].length() -2));

                                        int opponentMove = Integer.parseInt(words[6].substring(1, words[6].length() - 2));//Integer.parseInt(words[6]);
                                        //TicTacToeFieldStatus fieldStatusCross = new TicTacToeFieldStatus();
                                        //fieldStatusCross.setCross();
                                        //model.getGame().getModel().setFieldStatus(opponentMove, fieldStatusCross);
                                        System.out.println("opponentMove: " + opponentMove);
                                        model.getGame().setMove(opponentMove, true);
                                        System.out.println("Opponent move has been set");

                                    }
                                    break;
                            }
                            // GAME INFO
                            break;
                        case "challenge":
                            // CHALLENGE INFO
                            break;
                        case "playerlist":
                            // Send whole playerlist and filter harmful data
                            try {
                                // Safely split player names
                                String[] playerNames = originalInput.substring(16, originalInput.length() - 2)
                                        .split("\", ");
                                for (int i = 0; i < playerNames.length; i++) {
                                    playerNames[i] = playerNames[i].substring(1);
                                }
                                // Delete own name in playerlist
                                boolean ownNameDetected = false;
                                for (int i = 0; i < playerNames.length; i++) {
                                    if (playerNames[i].equals(model.getLoginName()) || ownNameDetected == true) {
                                        ownNameDetected = true;
                                        try {
                                            playerNames[i] = playerNames[i + 1];
                                        } catch (Exception e) {

                                        }
                                    }
                                }
                                // Create new array without own name
                                String[] filteredPlayerNames = new String[playerNames.length - 1];
                                if (ownNameDetected == true) {
                                    playerNames[playerNames.length - 1] = "";
                                    for (int i = 0; i < playerNames.length - 1; i++) {
                                        filteredPlayerNames[i] = playerNames[i];
                                    }
                                }
                                // Update playerlist and set focus
                                int sel = view.getNameSelected();
                                if (ownNameDetected == true) {
                                    view.updatePlayerboard(FXCollections.observableArrayList(filteredPlayerNames));
                                } else {
                                    view.updatePlayerboard(FXCollections.observableArrayList(playerNames));
                                }
                                view.setNameSelected(sel);
                            } catch (Exception e) {
                                view.updatePlayerboard(FXCollections.observableArrayList());
                            }

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
        System.out.println("logout");
        serverCommunication.logout();
    }

    public String login(String name) {
        String res = serverCommunication.login(name);
        if (res == "ok") {
            model.setLoginName(name);
        }
        // subscribe(GameName.TICTACTOE);
        return res;
    }

    public void subscribe(String gameName) {
        Game game = null;
        if(gameName.contains("-")) {
            gameName = gameName.replace("-", "");
            gameName = gameName.toUpperCase();
            gameName = "games." +GameName.valueOf(gameName).className;
        } else {
            gameName = "games." +gameName;
        }
        try {
            game = (Game) Class.forName(gameName).getConstructor(boolean.class, boolean.class, boolean.class).newInstance(model.isOnlineGame(), model.isUseAi(), model.isDoubleAi());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Game not found");
        }
        if (game != null) {
            GameController gameController = game.getController();
            model.setGame(game);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.setScene(model.getGame().getView().getScene());
                    model.getGame().getModel().setFirstMoves();
                }
            });

            System.out.println("blah blah blah");
            Timer timerOffline = new Timer();

            if (model.isOnlineGame() && !model.isDoubleAi()) {
                //serverCommunication.subscribe(gameName);
                System.out.println("Subscribed to " + gameName);
            } else if (!model.isOnlineGame() && model.isDoubleAi()) {
                timerOffline.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        gameController.nextTurn();
                    }
                }, 0, 100);
            } else if (!model.isOnlineGame() && model.isUseAi() && !model.isDoubleAi()) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    int userMove = model.getGame().getModel().getUserMove();
                    @Override
                    public void run() {
                        if(model.getGame().getModel().getPlayer() == 1) {
                            int move = model.getGame().getModel().getUserMove();
                            if (userMove != move) {
                                serverCommunication.move(move);
                                userMove = move;
                            }
                        } else {
                            model.getGame().setMove(model.getGame().getNextMove(), true);
                            model.getGame().getModel().switchPlayer();
                        }
                    }
                }, 0, 1000);
            }

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (gameController.isDone()) {
                        Platform.runLater(() -> {
                            timerOffline.cancel();
                            gameController.setDone(false);
                            serverCommunication.forfeit();
                            model.setGame(null);
                            stage.setScene(view.getScene());
                        });
                    }
                }
            }, 0, 100);
        }
    }

    public boolean checkChallenger(String challengerName) {
        return model.checkChallenger(challengerName);
    }

    public String checkChallengerGametype(String challengerName) {
        return model.checkChallengerGametype(challengerName);
    }

    public int getChallengeNumber(String challengerName) {
        return model.getChallengeNumber(challengerName);
    }

    public void challengeRival(String rivalName, String gameName) {
        serverCommunication.challengeRival(rivalName, gameName);
    }

    public void challengeAccept(int challengeNmr) {
        serverCommunication.challengeAccept(challengeNmr);
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

    public void resetStage() {
        stage.setScene(view.getScene());
    }

    public void setGameSettings(boolean online, boolean ai, boolean doubleAi) {
        model.setOnlineGame(online);
        model.setUseAi(ai, doubleAi);
    }

    public void subscribeServer(String gameName) {
        serverCommunication.subscribe(gameName);
    }
}
