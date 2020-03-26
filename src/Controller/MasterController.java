package Controller;

public class MasterController {
    private ServerCommunication serverCommunication;
    public MasterController() {
        serverCommunication = new ServerCommunication();
        //First read should be empty because garbage 2 lines
        serverCommunication.read();
        while (true) {
            handleInput();
        }
    }


    private void handleInput() {
        String input = serverCommunication.read();
        if(input != null) {
            input = input.toLowerCase();
            String[] words = input.split(" ");
            switch (words[0]) {
                case "ok":
                    break;
                case "err":
                    break;
                case "svr":
                    // All server commands
                    switch (words[1]) {
                        case "help":
                            //this would not ever happen
                            break;
                        case "game":
                            //GAME INFO
                            break;
                        case "match":
                            //MATCH INFO
                            break;
                        case "yourturn":
                            break;
                        case "move":
                            //SERVER WONT SAY MOVE this would never happen
                            break;
                        case "challenge":
                            //CHALLENGE INFO
                            break;
                        case "win":
                            //YOU WON
                            break;
                        case "loss":
                            //YOU LOST
                            break;
                        case "draw":
                            //DRAW
                            break;
                    }
                  default:
                      //If no OK ERR or SVR it is the first time connecting to the server --> LOGIN
                      serverCommunication.login("itv2c6");
            }
        }
    }
}
