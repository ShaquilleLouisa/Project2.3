package Controller;

import Games.GameName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ServerCommunication {
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;


    public ServerCommunication() {

    }

    public void connect() {
        socket = null;
        reader = null;
        writer = null;
        boolean connected = false;
        while (!connected) {
            try {
                socket = new Socket("localhost", 7789);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                connected = true;
            } catch (IOException e) {
                System.out.println("Could not connect with server");
                System.out.println("Trying again in 5 seconds");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception sleepException) {
                    System.out.println("Sleep exception");
                }
            }
        }
    }

    public String login(String name) {
        // Check if name is longer than 0 characters
        if (name.length() > 0) {
            write("login " + name);
            System.out.println("Logged in");
            return "ok"; // Valid name
        }
        return "short"; // Invalid name -- To short
        //return "inUse"; // Invalid name -- Already in use
    }

    public void logout() {
        write("logout");
    }


    public void getGameList() {
        write("get gamelist");
    }

    public ObservableList<String> getPlayerList() {
        write("get playerlist");

        //System.out.println(read()); // <---- ontvang resultaat nog

        // Returns list with all player names
        return FXCollections.observableArrayList ("Jos Badpak", "Bart Baksteen", "Willem Pen", "Jozef Appel", "Stefan Wortelsap", "Jochem Boterham", "Herman Bananensap");
    }

    public void challengeAccept(String challengeNmr) {
        write("challenge accept " + challengeNmr);
    }

    public void forfeit() {
        write("forfeit");
    }

    public void subscribe(GameName game) {
        write("subscribe" + game.label);
    }

    //HelpType can be empty
    public void help(String helpType) {
        if (helpType.equals("")) {
            write("help");
        } else {
            write("help " + helpType);
        }
    }

    //Their should be some checks here.
    public void move(String move) {
        write("move " + move);
    }

    //This function is always executed when creating the servercom
    //Read() will read the text the server sends and will act accordingly by executing functions required.
    public String read() {
        if (socket != null && reader != null && writer != null) {
            try {
                return reader.readLine().toLowerCase();
            } catch (Exception e) {
                System.out.println("Could not read from server");
                System.out.println("Reconnecting");
                connect();
            }
        }
        return null;
    }

    //Write is to give an awnser back to the server.
    //Write should never be public because only methods (commands) should be able to use it.
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
