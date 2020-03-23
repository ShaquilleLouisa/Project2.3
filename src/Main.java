import Controller.ServerCommunication;

public class Main {
    public static void main(String[] args) {
        ServerCommunication serverCommunication = new ServerCommunication();
        serverCommunication.read();

        serverCommunication.write("login anne2");
        serverCommunication.write("subscribe Tic-tac-toe");
    }
}