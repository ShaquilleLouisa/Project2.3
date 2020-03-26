package Model;

import Controller.GameType;

public class UserModel {
    private GameType game;
    private String loginName;
    private boolean inGame;

    public UserModel() {
        game = null;
        loginName = null;
        inGame = false;
    }

    public GameType getGame() {
        return game;
    }

    public void setGame(GameType game) {
        this.game = game;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInGame() {
        return inGame;
    }
}
