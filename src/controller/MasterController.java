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

/**
 * The masterController is the master ruler of the whole framework and makes all of the decisions
 */
public class MasterController extends Controller {
    MasterModel model;
    MasterView view;
    Stage stage;
    private ServerCommunication serverCommunication;

    /**
     * MasterController constructor
     */
    public MasterController() {
        serverCommunication = new ServerCommunication();
    }

    /**
     * This method is used to start the application, to start listening to the selected server.
     * @param stage main stage created when launching application.
     */
    public void start(Stage stage) {
        // Create control panel
        this.stage = stage;
        view.start(stage);
        boolean isConnected = serverCommunication.connect(getServerIP(), getServerPort());
        view.connected(isConnected);
        // First read should be empty because garbage 2 lines
        if(isConnected) {
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
        }, 0, 1000);

        Thread handleThread = new Thread(() -> {
            while (true) {
                handleInput();
            }
        });
        handleThread.start();

    }

    /**
     * This method is used to handle all of the commands server send to us.
     */
    private void handleInput() {
        String originalInput = null;
        try {
            originalInput = serverCommunication.read();
        } catch (IOException e) {
            System.out.println("No connecting with server:handleInput");
            view.NoConnection(true);
            try {
                Thread.sleep(500);
            } catch (Exception es) {}
        }
        if (originalInput != null) {
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
                            switch (words[2]) {
                                case "match":
                                    System.out.println("Match message: " + inputLowerCase.substring(totalLetters));
                                    // Get rival name using regex
<<<<<<< Updated upstream
                                    Pattern p = Pattern.compile("OPPONENT: \"([^\"]*)\"");
                                    Matcher m = p.matcher(originalInput);
=======
                                    p = Pattern.compile("OPPONENT: \"([^\"]*)\"");
                                    m = p.matcher(originalInput);
                                    if (model.getRivalName() == null) {
                                        while (m.find()) {
                                            System.out.println("Match rivalname received:" + m.group(1));
                                            model.setRivalName(m.group(1));
                                        }
                                    }
                                    // Get rival gametype using regex
                                    p = Pattern.compile("GAMETYPE: \"([^\"]*)\"");
                                    m = p.matcher(originalInput);
>>>>>>> Stashed changes
                                    while (m.find()) {
                                        System.out.println("Rivalname received:" + m.group(1));
                                        model.setRivalName(m.group(1));
                                    }
                                    break;
                                case "yourturn":
<<<<<<< Updated upstream
                                    System.out.println("Your turn");
                                    int ourMove;
                                    if(model.getGame().getModel().isUseAi()) {
                                        ourMove = model.getGame().getNextMove();
                                        //TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                                        //fieldStatus.setCircle();
                                        //model.getGame().getModel().setFieldStatus(ourMove, fieldStatus);
                                        model.getGame().setMove(ourMove, false);
=======
                                    if (model.getLoginColor() == 0) {
                                        model.setLoginColor(1);
                                    }

                                    //System.out.println(" ");
                                    //System.out.println("Your turn");

                                    int ourMove;
                                    if (model.getGame().getModel().isUseAi()) {
//                                        try{
//                                            Thread.sleep(2000);
//                                        } catch (InterruptedException e){
//                                            e.printStackTrace();
//                                        }

                                        //System.out.println("[YOURTURN]Set player " + model.getLoginColor());
                                        FieldStatus fieldStatus = new ReversiFieldStatus();
                                        fieldStatus.setId(model.getLoginColor());

                                        ourMove = model.getGame().getNextMove(fieldStatus);

                                        //TicTacToeFieldStatus fieldStatus = new TicTacToeFieldStatus();
                                        //fieldStatus.setCircle();
                                        //model.getGame().getModel().setFieldStatus(ourMove, fieldStatus);
                                        model.getGame().getModel().setPlayer(model.getLoginColor());
                                        try {
                                            model.getGame().setMove(ourMove, model.getLoginColor());
                                        } catch (MoveException e) {
                                            System.out.println("ILLEGAL MOVE");
                                        }
>>>>>>> Stashed changes
                                        serverCommunication.move(ourMove);
                                    } else {
                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            int userMove = model.getGame().getModel().getUserMove();

                                            @Override
                                            public void run() {
<<<<<<< Updated upstream
                                                int move = model.getGame().getModel().getUserMove();
                                                if(userMove != move) {
                                                    serverCommunication.move(move);
                                                    userMove = move;
=======
                                                boolean illegal = false;
                                                int move = model.getGame().getModel().getUserMove();
                                                if (userMove != move) {
                                                    try {
                                                        model.getGame().setMove(move, model.getLoginColor());
                                                    } catch (MoveException e) {
                                                        illegal = true;
                                                    }
                                                    if(!illegal) {
                                                        serverCommunication.move(move);
                                                        userMove = move;
                                                        this.cancel();
                                                    }
>>>>>>> Stashed changes
                                                }
                                            }
                                        }, 0, 100);
                                    }

                                    break;
                                case "loss":
<<<<<<< Updated upstream
                                    System.out.println("You lost");
                                    model.getGame().getView().updateNotification("I'm sorry you lost");
                                    break;
                                case "win":
                                    System.out.println("You won");
                                    model.getGame().getView().updateNotification("Congrats you won");
                                    break;
                                case "draw":
=======
<<<<<<< HEAD
                                    System.out.println(inputLowerCase);
                                    //System.out.println("You lost");
                                        endGame();
                                    //model.getGame().getView().updateNotification("I'm sorry you lost");
                                    break;
                                case "win":
                                    System.out.println(inputLowerCase);
                                    //System.out.println("You won");
                                        endGame();
                                    //model.getGame().getView().updateNotification("Congrats you won");
                                    break;
                                case "draw":
                                    System.out.println(inputLowerCase);
                                        endGame();
=======
                                    System.out.println("You lost");
                                    Platform.runLater(() -> {
                                        removeChallengeByName(getRivalName());
                                        setRivalName(null);
                                        model.setGame(null);
                                        model.setLoginColor(0);
                                        stage.setScene(view.getScene());
                                        view.updatePlayerboardImages();
                                    });
                                    //model.getGame().getView().updateNotification("I'm sorry you lost");
                                    break;
                                case "win":
                                    System.out.println("You won");
                                    Platform.runLater(() -> {
                                        removeChallengeByName(getRivalName());
                                        setRivalName(null);
                                        model.setGame(null);
                                        model.setLoginColor(0);
                                        stage.setScene(view.getScene());
                                        view.updatePlayerboardImages();
                                    });
                                    //model.getGame().getView().updateNotification("Congrats you won");
                                    break;
                                case "draw":
                                    System.out.println("Draw");
                                    Platform.runLater(() -> {
                                        removeChallengeByName(getRivalName());
                                        setRivalName(null);
                                        model.setGame(null);
                                        model.setLoginColor(0);
                                        stage.setScene(view.getScene());
                                        view.updatePlayerboardImages();
                                    });
>>>>>>> 89fcfa32e0b8340451bec2f190dc3879be4174c4
>>>>>>> Stashed changes
                                    // DRAW
                                    System.out.println("Draw");
                                    model.getGame().getView().updateNotification("Its a draw :|");
                                    break;
                                case "move":
                                    System.out.println("inputLowerCase " + words[4]);
                                    System.out.println("Move has been done: " + inputLowerCase.substring(totalLetters)
                                            + "name: " + getRivalName());
                                    if (words[4].contains(getRivalName())) {

                                        System.out.println(words[6].substring(1, words[6].length() - 2));

                                        int opponentMove = Integer.parseInt(words[6].substring(1, words[6].length() - 2));//Integer.parseInt(words[6]);
                                        //TicTacToeFieldStatus fieldStatusCross = new TicTacToeFieldStatus();
                                        //fieldStatusCross.setCross();
                                        //model.getGame().getModel().setFieldStatus(opponentMove, fieldStatusCross);
<<<<<<< Updated upstream
                                        System.out.println("opponentMove: " + opponentMove);
                                        model.getGame().setMove(opponentMove, true);
=======
                                        //System.out.println("opponentMove: " + opponentMove);
                                        if (model.getLoginColor() == 1) {
                                            //System.out.println("[MOVE]Set player " + 2);
                                            try {
                                                model.getGame().setMove(opponentMove, 2);
                                            } catch (MoveException e) {
                                                System.out.println("ILLEGAL MOVE");
                                            }
                                        } else {
                                            //System.out.println("[MOVE]Set player " + 1);
                                            try {
                                                model.getGame().setMove(opponentMove, 1);
                                            } catch (MoveException e) {
                                                System.out.println("ILLEGAL MOVE");
                                            }
                                        }

                                        //System.out.println("Opponent move has been set");
>>>>>>> Stashed changes

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

<<<<<<< HEAD
    private void endGame(){
        Platform.runLater(() -> {
            removeChallengeByName(getRivalName());
            setRivalName(null);
            model.setGame(null);
            model.setLoginColor(0);
            stage.setScene(view.getScene());
            view.updatePlayerboardImages();
        });
    }

=======
    /**
     * Send logout command to server
     */
>>>>>>> 89fcfa32e0b8340451bec2f190dc3879be4174c4
    public void logout() {
        System.out.println("logout");
        serverCommunication.logout();
    }

    /**
     * Send login command to server
      * @param name name is used to select a name to login with
     * @return returns if login was "ok".
     */
    public String login(String name) {
        String res = serverCommunication.login(name);
        if (res == "ok") {
            model.setLoginName(name);
        }
        // subscribe(GameName.TICTACTOE);
        return res;
    }

<<<<<<< Updated upstream
    public void subscribe(GameName gameName) {
=======
    /**
     * This method will subscribe to a specifc game and set it up (and if connected send a subscribe command)
     * @param gameName name of the game to subscribe to
     */
    public synchronized void subscribe(String gameName) {
>>>>>>> Stashed changes
        Game game = null;
        try {
            game = (Game) Class.forName("games." + gameName.className).getConstructor(boolean.class, boolean.class, boolean.class).newInstance(model.isOnlineGame(), model.isUseAi(), model.isDoubleAi());
        } catch (Exception e) {
            System.out.println("Game not found");
        }
        if (game != null) {
            GameController gameController = game.getController();
            model.setGame(game);
<<<<<<< Updated upstream
            GameView gameView = model.getGame().getView();
            stage.setScene(gameView.getScene());
            Timer timerOffline = new Timer();

            if (model.isOnlineGame()) {
                serverCommunication.subscribe(gameName);
                System.out.println("Subscribed to " + gameName.label);
            } else {
=======
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.setScene(model.getGame().getView().getScene());
                    model.getGame().getModel().setFirstMoves();
                }
            });

            Timer timerOffline = new Timer();

            if (model.isOnlineGame() && !model.isDoubleAi()) {
                serverCommunication.subscribe(gameName);
                System.out.println("Subscribed to " + gameName);
            }else if (!model.isOnlineGame() && model.isDoubleAi()) {
                System.out.println("test");
                final Game gameFinal = game;
>>>>>>> Stashed changes
                timerOffline.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        gameFinal.getController().nextTurn();
                    }
<<<<<<< Updated upstream
                }, 0, 1000);
=======
                }, 0, 100);
            } else if (!model.isOnlineGame() && model.isUseAi() && !model.isDoubleAi()) {
                model.getGame().getModel().setPlayer(1);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    int userMove = -1;

                    @Override
                    public void run() {
                        if (model.getGame().getModel().getPlayer() == 1) {
                            int move = model.getGame().getModel().getUserMove();
                            if (userMove != move) {
                                System.out.println("HEYO");
                                userMove = move;
                                boolean isIllegal = false;
                                try {
                                    model.getGame().setMove(userMove, 1);
                                } catch (MoveException e) {
                                    isIllegal = true;
                                    System.out.println("oef");
                                }

                                if (!isIllegal) {
                                    System.out.println("USER MOVE GEDAAn");
                                    model.getGame().getModel().setPlayer(2);
                                    System.out.println("NU WORDT HET DUS WIT");
                                }
                            }
                        } else {
                            FieldStatus fieldStatus = new ReversiFieldStatus();
                            fieldStatus.setId(2);
                            try {
                                model.getGame().setMove(model.getGame().getNextMove(fieldStatus), 2);
                            } catch (MoveException e) {
                                System.out.println("ILLEGAL MOVE");
                            }
                            model.getGame().getModel().setPlayer(1);
                        }
                        int winner = model.getGame().getModel().checkEnd(model.getGame().getModel().getBoard());
                        if (winner != -1) {
                            if(winner == 0) {
                                model.getGame().getView().updateNotification("draw");
                            } else {
                                model.getGame().getView().updateNotification("player " + winner + " has won!");
                            }
                            this.cancel();
                        }
                    }
                }, 0, 100);
            } else if (!model.isDoubleAi() && !model.isUseAi() && !model.isOnlineGame()) {
                System.out.println("1v1 off");
                final Game gameFinal = game;
                timerOffline.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(gameFinal.getModel().getPlayer() == 0) {
                            gameFinal.getModel().setPlayer(1);
                        }

                        int userMove = -1;
                        boolean doneMove = false;
                        boolean illegalMove = false;
                        FieldStatus fieldStatus = new ReversiFieldStatus();
                        fieldStatus.setId(gameFinal.getModel().getPlayer());
                        if (gameFinal.getModel().getUserMove() != userMove) {
                            try {
                                gameFinal.getController().doMove(gameFinal.getModel().getUserMove(), fieldStatus);
                            } catch (MoveException e) {
                                illegalMove = true;
                            }
                            doneMove = true;
                            gameFinal.getModel().setUserMove(-1);
                        }

                        if (!illegalMove && doneMove) {
                            int winner = gameFinal.getModel().checkEnd(gameFinal.getModel().getBoard());

                            if (winner != -1) {
                                if(winner == 0) {
                                    model.getGame().getView().updateNotification("draw");
                                } else {
                                    model.getGame().getView().updateNotification("player " + winner + " has won!");
                                }
                                this.cancel();
                            }
                            gameFinal.getModel().setPlayer((gameFinal.getModel().getPlayer() == 1 ? 2 : 1));
                            System.out.println("DID MOVWE CONTROLLER");
                        }

                    }
                }, 0, 100);
>>>>>>> Stashed changes
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (gameController.isDone()) {
                        Platform.runLater(() -> {
                            timerOffline.cancel();
                            gameController.setDone(false);
                            stage.setScene(view.getScene());
                        });
                    }
                }
            }, 0, 100);
        }
    }

<<<<<<< Updated upstream
=======
    /**
     * Remove challenge by name
     * @param CHALLENGER name of challenger
     */
    public void removeChallengeByName(String CHALLENGER) {
        model.removeChallengeByName(CHALLENGER);
    }

    /**
     * Remove challenge by challengenumber
     * @param CHALLENGERNUMBER challengenumber
     */
    public void removeChallenge(String CHALLENGERNUMBER) {
        model.removeChallenge(CHALLENGERNUMBER);
        view.updatePlayerboardImages();
    }

    /**
     * Check if challenger is current challenger
     * @param challengerName challengerName to check
     * @return if challenger is current challenger return true, else return false
     */
    public boolean checkChallenger(String challengerName) {
        return model.checkChallenger(challengerName);
    }

    /**
     * Check the gametype of challenger
     * @param challengerName challenger to check
     * @return String gameName
     */
    public String checkChallengerGametype(String challengerName) {
        return model.checkChallengerGametype(challengerName);
    }

    /**
     * Get challenge number from challenge name
     * @param challengerName challengername
     * @return challengeNumber int
     */
    public int getChallengeNumber(String challengerName) {
        return model.getChallengeNumber(challengerName);
    }
>>>>>>> Stashed changes

    /**
     * Challenge rival with specifc gamename command to server
     * @param rivalName name of rival
     * @param gameName name of game
     */
    public void challengeRival(String rivalName, String gameName) {
        serverCommunication.challengeRival(rivalName, gameName);
    }

<<<<<<< Updated upstream
=======
    /**
     * Accept challenge command to server
     * @param challengeNmr challenge number to accept
     */
    public void challengeAccept(int challengeNmr) {
        serverCommunication.challengeAccept(challengeNmr);
        removeChallenge(Integer.toString(challengeNmr));
    }

    /**
     * Get game list
     * This info will be handled in the inputHandle method
     */
>>>>>>> Stashed changes
    public void getGameList() {
        serverCommunication.getGameList();
    }

    /**
     * Get player list
     * This info will be handled in the inputHandle method
     */
    public void getPlayerList() {
        serverCommunication.getPlayerList();
    }

    /**
     * Get login name
     * @return loginname String
     */
    public String getLoginName() {
        return model.getLoginName();
    }

    /**
     * Set login name
     * @param loginName name to login with
     */
    public void setLoginName(String loginName) {
        model.setLoginName(loginName);
    }

    /**
     * Get rival name
     * @return rivalname String
     */
    public String getRivalName() {
        return model.getRivalName();
    }

    /**
     * Set rival name
     * @param rivalName name of rival to set
     */
    public void setRivalName(String rivalName) {
        model.setRivalName(rivalName);
    }

    /**
     * Add View to Controller (MVC)
     * @param view view to add to controller
     */
    @Override
    public void addView(View view) {
        this.view = (MasterView) view;
    }


    /**
     * Add Model to controller (MVC)
     * @param model model to add to controller
     */
    @Override
    public void addModel(Model model) {
        this.model = (MasterModel) model;
    }

    /**
     * Reset stage to default scene (masterview)
     */
    public void resetStage() {
        stage.setScene(view.getScene());
    }

    /**
     * Set game settings
     * @param online is the game online
     * @param ai does the game use an AI
     * @param doubleAi does the game use 2 AI's
     */
    public void setGameSettings(boolean online, boolean ai, boolean doubleAi) {
        model.setOnlineGame(online);
        model.setUseAi(ai, doubleAi);
    }
<<<<<<< Updated upstream
=======

    /**
     * Subscribe to game command to server
     * @param gameName gamename to subscribe to
     */
    public void subscribeServer(String gameName) {
        serverCommunication.subscribe(gameName);
    }

    public void setClientTimeout(int timeout){
        model.setClientTimeout(timeout);
    }

    public int getClientTimeout(){
        return model.getClientTimeout();
    }

    public void setServerAddress(String address){
        model.setServerAddress(address);
        serverCommunication.updateServerSettings(getServerIP(), getServerPort());
    }

    public String getServerIP(){
        return model.getServerIP();
    }

    public int getServerPort(){
        return model.getServerPort();
    }

    public boolean getConnectionState() {
        return serverCommunication.getConnectionState();
    }


>>>>>>> Stashed changes
}
