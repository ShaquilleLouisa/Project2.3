import Controller.GameType;
import Controller.ServerCommunication;

public class Main {
    public static void main(String[] args) {
        ServerCommunication serverCommunication = new ServerCommunication();
        serverCommunication.read();

        serverCommunication.login("anne");
        serverCommunication.subscribe(GameType.TICTACTOE);
    }
}