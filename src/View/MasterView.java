package View;

import Controller.MasterController;
import Model.MasterModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.swing.*;


public class MasterView extends View {
    MasterController controller;

    public MasterView() {

    }

    public MasterView(MasterController controller) {
        this.controller = controller;
    }

    // Define buttons
    Button btnChangeName = new Button("");

    // Define text fields
    TextField usernameEdit = new TextField();

    // Default window resolution
    int windowWidth = 1280;
    int windowHeight = 720;
    boolean nameSet = false;

    // Get screen resolution
//    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//    double screenWidth = screenSize.getWidth();
//    double screenHeight = screenSize.getHeight();

    // Username backgrounds
    Image bgUsernameOk = new Image("File:assets/launcher/bgUsernameOk.png");
    Image bgUsernameEdit = new Image("File:assets/launcher/bgUsernameEdit.png");
    ImageView bgUsernameUse = new ImageView("File:assets/launcher/bgUsernameEdit.png");

    // Panes
    Pane pnLauncher = new Pane();


    @Override
    public void start(Stage stage) {
        // Define button actions
        buttonActions();

        // Background
        pnLauncher.getChildren().add(bgUsernameUse);

        // Name
        //Text usernameEdit = new Text("Jos Badpak");
        usernameEdit.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        //usernameEdit.setFill(Color.WHITE);
        pnLauncher.getChildren().add(usernameEdit);

        // Pane username
        pnLauncher.setStyle("-fx-background-color: #262626"); // Default background color

        // Change name button
        ImageView usernameEdit = new ImageView("File:assets/launcher/nameEdit.png");
        pnLauncher.getChildren().add(btnChangeName);
        btnChangeName.setStyle("-fx-background-color: #262626;");
        btnChangeName.setGraphic(usernameEdit);


        // Window settings
        stage.setTitle("Epic game launcher");
        stage.setMinWidth(1024);
        stage.setMinHeight(576);
        stage.setMaxWidth(2560);
        stage.setMaxHeight(1440);
        stage.setScene(new Scene(pnLauncher, windowWidth, windowHeight));
        stage.show();

        // Update window resolution
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                windowWidth = newSceneWidth.intValue();
                relocatePanes();
            }
        });
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                windowHeight = newSceneHeight.intValue();
                relocatePanes();
            }
        });

        // Trigger relocator
        stage.setWidth(windowWidth+1);
        stage.setWidth(windowWidth);
    }

    private void buttonActions(){
        // Logout button
        btnChangeName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nameSet == false) {
                    controller.login((usernameEdit.getCharacters().toString()));
                    usernameEdit.setDisable(true);
                    bgUsernameUse.setImage(bgUsernameOk);
                    nameSet = true;
                } else {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Om uw naam te wijzigen moet u eerst de lobby verlaten. Wilt u de lobby nu verlaten?","Waarschuwing",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        // Saving code here
                        controller.logout();
                        usernameEdit.setDisable(false);
                        bgUsernameUse.setImage(bgUsernameEdit);
                        nameSet = false;
                    }
                }
            }
        });
    }


    // Executed when client has connected
    public void connected(Boolean isConnected){
        if (isConnected == true){

        }
    }

    private void relocatePanes() {
        setUsernamePosition();
    }

    private void setUsernamePosition(){
        // Username - background
        bgUsernameUse.setLayoutX(-3300+windowWidth); // Top right

        // Username - change name
        btnChangeName.setLayoutX(windowWidth-80);
        btnChangeName.setMinHeight(50);
        btnChangeName.setLayoutY(8);

        // Username - edit field
        usernameEdit.setLayoutY(8);
        usernameEdit.setLayoutX(windowWidth-400);
        usernameEdit.setMaxWidth(312);

    }


    public static void main(String[] args) {
        launch(args);
    }

//    public void update() {
//        Platform.runLater(() -> {
//
//        });
//    }
}
