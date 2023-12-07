package controller;

import devTools.DevToolC;
import devTools.DevToolC.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginMenu {


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
            System.out.println("Enter key pressed. Perform specific action here.");
            DevToolC.println("Enter key pressed. Perform specific action here.");
            DevToolC.toolsOff();
        }
    }

    public void handleDarkButtonClick(ActionEvent actionEvent) {
        System.out.println("Executing handleDarkButtonClick");
    }

    public void exitClick(ActionEvent actionEvent) {
        System.out.println("Executing exitClick");
    }

}
