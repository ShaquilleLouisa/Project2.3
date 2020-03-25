package Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerCommunication {
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    Boolean done = false;

    public ServerCommunication() {

        socket = null;
        reader = null;
        writer = null;
        try {
            socket = new Socket("localhost", 7789);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Could not connect with server");
        }
    }

    public void login(String name) {
        write("login " + name);
    }

    public void logout() {
        write("logut");
    }

    public void setGame(String game) {
        if(game.equals("Tic-tac-toe") || game.equals(""))
        write("subscibe ");
    }

    public void getGameList() {
        write("get gamelist");
    }

    public void subscribe(GameType gameType) {
        if(gameType == GameType.TICTACTOE) {
            write("subscribe Tic-tac-toe");
        } else if(gameType == GameType.REVERSI) {
            write("subscibe Reversi");
        }
    }

    public void read() {
        new Thread((Runnable) () -> {
            if (socket != null && reader != null && writer != null) {
                try {
                    String input = reader.readLine().toLowerCase();


                    while (true) {
                        System.out.println(input);
                        String[] words = input.split(" ");
                        switch (words[0]) {
                            case "OK":
                                break;
                            case "ERR":
                                break;
                            case "svr":
                                System.out.println("");
                                // All server commands
                                switch (words[1]) {
                                    case "help":
                                        break;
                                    case "game":
                                        break;
                                    case "match":
                                        break;
                                    case "yourturn":
                                        break;
                                    case "move":
                                        break;
                                    case "challenge":
                                        break;
                                    case "win":
                                        break;
                                    case "loss":
                                        break;
                                    case "draw":
                                        break;
                                }
                        }
                        input = reader.readLine();
                    }
                } catch (IOException e) {
                    System.out.println("Could not read from server");
                }
            }
        }).start();

    }

    private void write(String command) {
        if (socket != null && reader != null && writer != null) {
            try {
                writer.write(command);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.out.println("Could not read from server");
            }
        }
    }
}
