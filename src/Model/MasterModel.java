package Model;

import Games.Game;
import Games.GameName;
import View.MasterView;

public class MasterModel extends Model {

    private MasterView view;
    private Game game;
    private String loginName;
    private boolean inGame;

    public MasterModel(MasterView view) {
        this.view = view;
        game = null;
        loginName = null;
        inGame = false;
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

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInGame() {
        return inGame;
    }
}
