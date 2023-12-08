package controller;

import devTools.DevToolC;
import appSettings.styleSetter;
import devTools.DevToolC.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class LoginMenu {

    @FXML private AnchorPane rootNode;
    @FXML public Label loginTitle;
    @FXML public Label usernameLabel;
    @FXML public TextField loginScreenUsernameInput;
    @FXML public Label passwordLabel;
    @FXML public TextField loginScreenPassword;
    @FXML public Label locationLabel;
    @FXML public TextField loginScreenLocationField;
    @FXML public Button loginButton;
    @FXML public Label darkModeLabel;
    @FXML public Button darkModeButton; //FXML fx:id References
    @FXML public Button exitButton;

    public void loginClick(ActionEvent actionEvent) {
        System.out.println("Executing loginClick method: Handles the actionEvent when the login button is clicked.");
    }

    public void loginEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            // Only execute the following code when the Enter key is pressed
            DevToolC.println("Enter key pressed. Perform specific action here.");
            DevToolC.println("Enter key pressed. Perform specific action here.");
            DevToolC.toolsOff();
        }
    }


    private boolean darkButtonOn = styleSetter.darkModeBool();
    public void handleDarkButtonClick(ActionEvent actionEvent) {

        if (darkButtonOn) {
            styleSetter.darkModeOff();
        } else {
            styleSetter.darkModeOn();
        }

        DevToolC.println("Executing handleDarkButtonClick");
        if (rootNode != null) {
            // Access the current scene
            System.out.println("Accessed current scene: " + rootNode.getScene());
            styleSetter.applyStyleToScene(rootNode.getScene());
        } else {
            System.out.println("Root node is not assigned.");
        }

        DevToolC.println("DarkModeBool = " + styleSetter.darkModeBool() + "\nDarkModeButton = " + darkButtonOn);

        if (darkButtonOn) {
            styleSetter.darkModeOff();
        } else {
            styleSetter.darkModeOn();
        }
        darkButtonOn = styleSetter.darkModeBool();

        DevToolC.println("DarkModeBool = " + styleSetter.darkModeBool() + "\nDarkModeButton = " + darkButtonOn);


    }

    public void exitClick(ActionEvent actionEvent) {
        System.out.println("Executing exitClick");
    }

}
