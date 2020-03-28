package Games;

public enum GameName {
    REVERSI("Reversi"),
    TICTACTOE("Tic-tac-toe");

    public final String label;

    private GameName(String label) {
        this.label = label;
    }
}
