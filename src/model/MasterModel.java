package model;

import games.Game;
import games.GameName;
import view.MasterView;

import java.util.ArrayList;

public class MasterModel extends Model {

    private MasterView view;
    private Game game;
    private String loginName;
    private String rivalName;
    private boolean inGame;
    private boolean onlineGame;
    private boolean useAi;
    private boolean doubleAi;
    ArrayList<String[]> challengesReceived = new ArrayList<String[]>();

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

    public void addChallenge(String CHALLENGER, String CHALLENGERNUMBER,  String GAMETYPE) {
        String[] finData = new String[3];
        finData[0] = CHALLENGER;
        finData[1] = CHALLENGERNUMBER;
        finData[2] = GAMETYPE;
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
        return "niks";
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
}
