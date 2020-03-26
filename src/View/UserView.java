package View;

import Controller.UserController;
import Model.TicTacToeItems.FieldStatus;
import Model.UserModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class UserView extends View {
    UserModel model;
    UserController controller;
    public UserView(UserModel model, UserController controller) {
        this.model = model;
        this.controller = controller;

    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");
        StackPane pane = new StackPane();
        TextField loginName = new TextField();
        loginName.setMaxSize(100, 20);
        Button loginSubmit = new Button("Login");
        Button logout = new Button("Logout");
        pane.getChildren().add(loginName);
        pane.getChildren().add(loginSubmit);
        pane.getChildren().add(logout);

        pane.setAlignment(Pos.TOP_CENTER);     // Right-justify nodes in stack
        StackPane.setMargin(loginSubmit, new Insets(40, 0, 0, 0)); // Center "?"
        StackPane.setMargin(logout, new Insets(80, 0, 0, 0)); // Center "?"

        loginSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.login((loginName.getCharacters().toString()));

            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.logout();
                System.out.println("Action has run");
            }
        });

        stage.setScene(new Scene(pane, 800, 400));
        stage.show();
    }

    public void update() {
        Platform.runLater(() -> {

        });
    }
}
