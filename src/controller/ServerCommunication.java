package controller;

import games.GameName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Class that is used to communicate with the server
 * @author Anne de Graaff
 */

public class ServerCommunication {
    MasterController masterController;

    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    boolean connected = false;

    public ServerCommunication() {

    }

    /**
     * Try to connect with the server
     * @return if connected return true else false
     */
    public boolean connect() {
        socket = null;
        reader = null;
        writer = null;
        try {
            //socket = new Socket("localhost", 7789);
            //socket = new Socket("145.33.225.170", 7789);
            socket = new Socket("77.170.155.250", 7789);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            connected = true;
            return true;
        } catch (IOException e) {
            System.out.println("Could not connect with server");
            return false;
        }

    }

    /**
     * Login to the server with a specfic name
     * @param name String name to login with
     * @return login status String
     */
    public String login(String name) {
        // Check if name is longer than 0 characters
        if (name.length() > 0) {
            if (!connected) {
                connect();
            }

            if(connected) {
                try {
                    write("login " + name);
                } catch (IOException e) {
                    System.out.println("No connecting with server:login");
                }
                System.out.println("Logged in");
                return "ok"; // Valid name
            } else {
                return "connection";
            }
        }
        return "short"; // Invalid name -- To short
        //return "inUse"; // Invalid name -- Already in use

        // // Check if name is longer than 0 characters
        // if (!connected) {
        //     connect();
        // }
        // if (name.length() > 0) {
        //     try {
        //         write("login " + name);
        //     } catch (IOException e) {
        //         System.out.println("No connecting with server:login");
        //     }
        //     System.out.println("Logged in");
        //     return "ok"; // Valid name
        // }
        // return "short"; // Invalid name -- To short
        // //return "inUse"; // Invalid name -- Already in use
    }

    /**
     * Logout the server
     */
    public void logout() {
        try {
            socket.close();
            System.out.println("Closed socket");
        } catch (IOException e) {
            System.out.println("Socket disconnect error");
        }
        connected = false;
        System.out.println("Logged out");
        try {
            write("logout");
        } catch (IOException e) {
            System.out.println("No connecting with server:logout");
        }

    }

    /**
     * get gamelist from server
     */
    public void getGameList() {
        try {
            write("get gamelist");
        } catch (IOException e) {
            System.out.println("No connecting with server:getGameList");
        }
    }

    /**
     * Get the current player list
     */
    public void getPlayerList() {
        if (connected == true) {
            try {
                write("get playerlist");
            } catch (IOException e) {
                System.out.println("No connecting with server:ServerCommunication:getPlayerList()");
            }
        }
    }

    /**
     * Accept a specfic challenge
     * @param challengeNmr challenge to accept
     */
    public void challengeAccept(int challengeNmr) {
        try {
            write("challenge accept " + challengeNmr);
        } catch (IOException e) {
            System.out.println("No connecting with server:challengeAccept");
        }
    }

    /**
     * Forfeit a game
     */
    public void forfeit() {
        try {
            write("forfeit");
        } catch (IOException e) {
            System.out.println("No connecting with server:forfeit");
        }
    }

    /**
     * Subscribe to game
     * @param gameName game to subscribe to
     */
    public void subscribe(String gameName) {
        try {
            write("subscribe " + gameName);
        } catch (IOException e) {
            System.out.println("No connecting with server:subscribe");
        }
    }

    /**
     * Challenge rival for a specifc game
     * @param rivalName Rival to challenge
     * @param gameName Game for challenge
     */
    public void challengeRival(String rivalName, String gameName) {
        try {
            write("challenge \"" + rivalName + "\" \"" + gameName + "\"");
        } catch (IOException e) {
            System.out.println("No connecting with server:challengeRival");
        }
    }

    /**
     * Ask for help to the server
     * @param helpType
     */
    // HelpType can be empty
    public void help(String helpType) {
        try {
            if (helpType.equals("")) {
                write("help");
            } else {
                write("help " + helpType);
            }
        } catch (IOException e) {
            System.out.println("No connecting with server:help");
        }
    }

    /**
     * Do a move
     * @param move move to do
     */
    public void move(int move) {
        try {
            write("move " + move);
        } catch (IOException e) {
            System.out.println("No connecting with server:move");
        }
    }

    /**
     * Read the server
     * @return server text
     * @throws IOException
     */
    public String read() throws IOException {
        if (connected) {
            try {
                return reader.readLine();
            } catch (Exception e) {
                System.out.println("Could not read from server");
                System.out.println("Reconnecting");
                connect();
            }
            return null;
        } else {
            throw new IOException("Not Connected");
        }
    }

    /**
     * Write something to the server
     * THIS SHOULD ALWAYS BE PRIVATE because otherwise you can write custom things to the server and that should only be done through methods
     * @param command command to send to the server
     * @throws IOException
     */
    private void write(String command) throws IOException {
        if (connected) {
            try {
                writer.write(command);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.out.println("Could not read from server -> ServerCommunication:write()");
                System.out.println(e);
            }
        } else {
            throw new IOException("Not Connected");
        }
    }
}
