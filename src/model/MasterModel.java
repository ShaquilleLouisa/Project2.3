package model;

import games.Game;
import games.GameName;
import view.MasterView;

import java.util.ArrayList;

public class MasterModel extends Model {
    private MasterView view;
    private Game game;
    private String loginName;
    private int loginColor = 0;
    private String rivalName;
    private boolean inGame;
    private boolean onlineGame;
    private boolean useAi;
    private boolean doubleAi;
    ArrayList<String[]> challengesReceived = new ArrayList<String[]>();

//    private String serverIP = "127.0.0.1";
    private String serverIP = "145.33.225.170";
//    private String serverIP = "77.170.155.250";
    private int serverPort = 7789;
    private int clientTimeout = 10;

    public MasterModel(MasterView view) {
        this.view = view;
        game = null;
        loginName = null;
        rivalName = null;
        inGame = false;
        onlineGame = false;
        useAi = false;
        doubleAi = false;
    }

    public void setClientTimeout(int timeout){
        clientTimeout = timeout;
    }

    public int getClientTimeout(){
        return clientTimeout;
    }

    public void setServerAddress(String address){
        String[] settings = address.split(":");
        System.out.println(settings[0]);
        serverIP = settings[0];
        serverPort = 7789;
        if (settings.length > 1) {
            if (settings[1].length() > 0) {
                try {
                    serverPort = Integer.parseInt(settings[1]);
                } catch (Exception e) {
                }
            }
        }

    }

    public String getServerIP(){
        return serverIP;
    }

    public int getServerPort(){
        return serverPort;
    }

    public void removeChallengeByName(String CHALLENGER) {
        // Check if name is already in the list
        for (int i=challengesReceived.size()-1; i>=0; i--){
            if (CHALLENGER.equals(challengesReceived.get(i)[0])){
                challengesReceived.remove(i);
                System.out.println("Deleting challenger entries " + CHALLENGER);
                return;
            }
        }
    }

    public void removeChallenge(String CHALLENGERNUMBER) {
        // Check if name is already in the list
        for (int i=challengesReceived.size()-1; i>=0; i--){
            if (CHALLENGERNUMBER.equals(challengesReceived.get(i)[1])){
                challengesReceived.remove(i);
                System.out.println("Deleting challenge " + CHALLENGERNUMBER);
                return;
            }
        }
    }

    public void addChallenge(String CHALLENGER, String CHALLENGERNUMBER,  String GAMETYPE) {
        String[] finData = new String[3];
        finData[0] = CHALLENGER;
        finData[1] = CHALLENGERNUMBER;
        finData[2] = GAMETYPE;

        // Check if name is already in the list
        for (int i=challengesReceived.size()-1; i>=0; i--){
            if (CHALLENGER.equals(challengesReceived.get(i)[0])){
                challengesReceived.get(i)[1] = CHALLENGERNUMBER;
                challengesReceived.get(i)[2] = GAMETYPE;
                System.out.println("Updating old challenge invite from " + CHALLENGER);
                return;
            }
        }

        System.out.println("Adding new challenge invite from " + CHALLENGER);
        // First invite add to list
        challengesReceived.add(finData);
    }

    public boolean checkChallenger(String challengerName) {
        for (int i=challengesReceived.size()-1; i>=0; i--){
            if (challengerName.equals(challengesReceived.get(i)[0])){
                return true;
            }
        }
        return false;
    }

    public String checkChallengerGametype(String challengerName) {
        for (int i=challengesReceived.size()-1; i>=0; i--){
            if (challengerName.equals(challengesReceived.get(i)[0])){
                return challengesReceived.get(i)[2];
            }
        }
        return null;
    }

    public int getChallengeNumber(String challengerName) {
        for (int i=challengesReceived.size()-1; i>=0; i--){
            if (challengerName.equals(challengesReceived.get(i)[0])){
                return Integer.parseInt(challengesReceived.get(i)[1]);
            }
        }
        System.out.println("FATAL ERROR");
        return -1;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) { this.loginName = loginName; }

    public String getRivalName() {
        return rivalName;
    }

    public void setRivalName(String rivalName) { this.rivalName = rivalName; }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInGame() {
        return inGame;
    }

    public boolean isOnlineGame() {
        return onlineGame;
    }

    public void setOnlineGame(boolean onlineGame) {
        this.onlineGame = onlineGame;
    }

    public boolean isUseAi() {
        return useAi;
    }

    public void setUseAi(boolean useAi, boolean doubleAi) {
        this.useAi = useAi;
        this.doubleAi = doubleAi;
    }

    public void setDoubleAi(boolean doubleAi) {
        this.doubleAi = doubleAi;
    }

    public boolean isDoubleAi() {
        return doubleAi;
    }

    public int getLoginColor() {
        return loginColor;
    }

    public void setLoginColor(int loginColor) {
        this.loginColor = loginColor;
    }
}
