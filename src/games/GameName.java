package games;

public enum GameName {
    REVERSI("Reversi"),
    TICTACTOE("TicTacToe");

    public final String label;

    private GameName(String label) {
        this.label = label;
    }
}
