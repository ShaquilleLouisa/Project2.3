package games;

public enum GameName {
    REVERSI("Reversi", "Reversi"),
    TICTACTOE("Tic-tac-toe", "TicTacToe");

    public final String label;
    public final String className;

    private GameName(String label, String className) {
        this.label = label;
        this.className = className;
    }
}
