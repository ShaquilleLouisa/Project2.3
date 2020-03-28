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

    // Panes
    Pane pnUsername = new Pane();

    @Override
    public void start(Stage stage) {
        // Define button actions
        buttonActions();

        // Background
        Image usernameBG = new Image("File:assets/launcher/bg_name.png");
        pnUsername.getChildren().add(new ImageView(usernameBG));

        // Name
        //Text usernameEdit = new Text("Jos Badpak");
        usernameEdit.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        //usernameEdit.setFill(Color.WHITE);
        pnUsername.getChildren().add(usernameEdit);
        usernameEdit.setLayoutY(8);
        usernameEdit.setLayoutX(1600+windowWidth);
        usernameEdit.setMaxWidth(312);

        // Pane username
        pnUsername.setStyle("-fx-background-color: #262626"); // Default background color

        // Change name button
        ImageView usernameEdit = new ImageView("File:assets/launcher/nameEdit.png");
        pnUsername.getChildren().add(btnChangeName);
        btnChangeName.setStyle("-fx-background-color: #262626;");
        btnChangeName.setGraphic(usernameEdit);
        btnChangeName.setLayoutX(1930+windowWidth);
        btnChangeName.setLayoutY(8);


        // Window settings
        stage.setTitle("Epic game launcher");
        stage.setMinWidth(windowWidth);
        stage.setMinHeight(windowHeight);
        stage.setMaxWidth(2560);
        stage.setMaxHeight(1440);
        stage.setScene(new Scene(pnUsername, windowWidth, windowHeight));
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
                    nameSet = true;
                } else {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Om uw naam te wijzigen moet u eerst de lobby verlaten. Wilt u de lobby nu verlaten?","Waarschuwing",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        // Saving code here
                        controller.logout();
                        usernameEdit.setDisable(false);
                        nameSet = false;
                    }
                }
            }
        });
    }

    private void relocatePanes() {
        setUsernamePosition();
    }

    private void setUsernamePosition(){
        pnUsername.setLayoutX(-3300+windowWidth); // Top right

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
