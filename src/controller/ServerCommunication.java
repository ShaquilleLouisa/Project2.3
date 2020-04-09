package controller;

import games.GameName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ServerCommunication {
    MasterController masterController;

    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    boolean connected = false;

    public ServerCommunication() {

    }

    public boolean connect() {
        socket = null;
        reader = null;
        writer = null;


        try {
            socket = new Socket("localhost", 7789);
            //socket = new Socket("145.33.225.170", 7789);
            //socket = new Socket("77.170.155.250", 7789);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            connected = true;
            return true;
        } catch (IOException e) {
            System.out.println("Could not connect with server");
            return false;
        }

    }

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

    public void getGameList() {
        try {
            write("get gamelist");
        } catch (IOException e) {
            System.out.println("No connecting with server:getGameList");
        }
    }

    public void getPlayerList() {
        if (connected == true) {
            try {
                write("get playerlist");
            } catch (IOException e) {
                System.out.println("No connecting with server:ServerCommunication:getPlayerList()");
            }
        }
    }

    public void challengeAccept(int challengeNmr) {
        try {
            write("challenge accept " + challengeNmr);
        } catch (IOException e) {
            System.out.println("No connecting with server:challengeAccept");
        }
    }

    public void forfeit() {
        try {
            write("forfeit");
        } catch (IOException e) {
            System.out.println("No connecting with server:forfeit");
        }
    }

    public void subscribe(String gameName) {
        try {
            write("subscribe " + gameName);
        } catch (IOException e) {
            System.out.println("No connecting with server:subscribe");
        }
    }

    public void challengeRival(String rivalName, String gameName) {
        try {
            write("challenge \"" + rivalName + "\" \"" + gameName + "\"");
        } catch (IOException e) {
            System.out.println("No connecting with server:challengeRival");
        }
    }

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

    // Their should be some checks here.
    public void move(int move) {
        try {
            write("move " + move);
        } catch (IOException e) {
            System.out.println("No connecting with server:move");
        }
    }

    // This function is always executed when creating the servercom
    // Read() will read the text the server sends and will act accordingly by
    // executing functions required.
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

    // Write is to give an awnser back to the server.
    // Write should never be public because only methods (commands) should be able
    // to use it.
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
