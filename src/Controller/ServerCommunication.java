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
            socket = new Socket("localhost", 7789) ;
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
        write("subscibe ")
    }

    public void getGameList() {
        write("get gamelist");
    }



    public void read() {
        new Thread((Runnable) () -> {
            if (socket != null && reader != null && writer != null) {
                try {
                    String input = reader.readLine();

                    while (input != null) {
                        if(input.toLowerCase().contains("svr")) {
                            System.out.println("");
                            //All server commands
                            if(input.toLowerCase().contains("help")) {

                            } else if(input.toLowerCase().contains("game")) {

                            } else if(input.toLowerCase().contains("match")) {

                            } else if(input.toLowerCase().contains("yourturn")) {

                            } else if(input.toLowerCase().contains("move")) {

                            } else if(input.toLowerCase().contains("challenge")) {

                            } else if(input.toLowerCase().contains("win")) {

                            } else if(input.toLowerCase().contains("loss")) {

                            } else if(input.toLowerCase().contains("draw")) {

                            }
                        }

                        if(input.toLowerCase().contains("ok")) {
                            //Server OK with input
                        }

                        if(input.toLowerCase().contains("err")) {
                            //Server ERR with input
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
        if(socket != null && reader != null && writer != null) {
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
