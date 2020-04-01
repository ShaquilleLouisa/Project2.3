import controller.MasterController;
import model.MasterModel;
import view.MasterView;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MasterController masterController = new MasterController();
        MasterView masterView = new MasterView(masterController);
        MasterModel masterModel = new MasterModel(masterView);

        masterController.addModel(masterModel);
        masterController.addView(masterView);
        masterController.start(stage);
    }
}