package View;

import Controller.UserController;
import Model.UserModel;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UserView extends View {
    UserModel model;
    UserController controller;
    public UserView(UserModel model, UserController controller) {
        this.model = model;
        this.controller = controller;

    }

    // Define buttons
    Button loginSubmit = new Button("Login");
    Button logout = new Button("Logout");

    // Define text fields
    TextField loginName = new TextField();

    // Default window resolution
    int windowWidth = 1280;
    int windowHeight = 720;

    // Get screen resolution
//    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//    double screenWidth = screenSize.getWidth();
//    double screenHeight = screenSize.getHeight();

    // Pane background color
    String defaultPaneBGcolor = "ffffff";

    // Panes
    Pane pnUsername = new Pane();

    @Override
    public void start(Stage stage) {
        // Define button actions
        buttonActions();

        // Image
        Image image = new Image("File:assets/launcher/bg_name.png");
        pnUsername.getChildren().add(new ImageView(image));

        // Name
        Text username = new Text("Jos Badpak");
        username.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        pnUsername.getChildren().add(username);
        username.setLayoutY(48);
        username.setLayoutX(64);

        // Pane username
        pnUsername.setStyle("-fx-background-color: #"+defaultPaneBGcolor); // Default background color
        pnUsername.getChildren().add(loginName);
        loginName.setLayoutX(64);
        loginName.setLayoutY(32);
        pnUsername.getChildren().add(loginSubmit);
        loginSubmit.setLayoutX(64);
        pnUsername.getChildren().add(logout);
        logout.setLayoutX(64+128);


        // Window settings
        stage.setTitle("Epic game launcher");
        relocatePanes();
        stage.setMinWidth(1024);
        stage.setMinHeight(576);
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
    }

    private void buttonActions(){
        // Login button
        loginSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.login((loginName.getCharacters().toString()));

            }
        });

        // Logout button
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.logout();
                System.out.println("Action has run");
            }
        });
    }

    private void relocatePanes() {
        setUsernamePosition();
    }

    private void setUsernamePosition(){
        pnUsername.setLayoutX(windowWidth-512); // Top right

    }


//    public void update() {
//        Platform.runLater(() -> {
//
//        });
//    }
}
