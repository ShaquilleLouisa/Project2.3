import Controller.GameType;
import Controller.MasterController;
import Controller.ServerCommunication;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MasterController masterController = new MasterController(stage);
    }
}