package model;

import games.Game;
import games.GameName;
import view.MasterView;

public class MasterModel extends Model {

    private MasterView view;
    private Game game;
    private String loginName;
    private String rivalName;
    private boolean inGame;
    private boolean onlineGame;
    private boolean useAi;
    private boolean doubleAi;

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
