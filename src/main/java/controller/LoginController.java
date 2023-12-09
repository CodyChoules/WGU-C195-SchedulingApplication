package controller;

import dataAccessObject.userDAO;

import devTools.DevToolC;

import devTools.JDBTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {


    @FXML public Label loginTitle;
    @FXML public Label usernameLabel;
    @FXML private TextField loginScreenUsernameInput;
    @FXML public Label passwordLabel;
    @FXML private TextField loginScreenPasswordInput;
    @FXML public Label locationLabel;
    @FXML private TextField loginScreenLocationField;
    @FXML public Button loginButton;
    @FXML public Label darkModeLabel;
    @FXML public Button darkModeButton; //FXML fx:id References
    @FXML public Button exitButton;

    private void loginMethod() {

        JDBTools.openConnection();

        //TODO [c] Get login info from input.
        String nameInput = loginScreenUsernameInput.getText();
        String passInput = loginScreenPasswordInput.getText();

        //Missing Skill,
        //Skill Found: DAO Use Case:
        // Need to create a Data Access Object(DAO).
        //DAOs are abstractions used to access persistent mechanisms
        //such as Data Bases. Focused often on CRUD (Create, Read,
        // Update, Delete) operations on data entities.

        //TODO [c] create a dataAccessObject package

        //TODO [c] create a DAO to access user date inside the SQL DB
        //Validates user input
        boolean validated = userDAO.validateUserLogin(nameInput,passInput);
        if (validated){ DevToolC.println("User input Validated, Logging in."); }

        //End of data-base-connection initial tasks. -committing & pushing branch
        //TODO [] create main menu fxml
        //TODO [] create load main menu on validation logic
        //TODO [] create a incorrect password popup
        //TODO [] review rubric



    }

    public void loginClick(ActionEvent actionEvent) {
        System.out.println("Executing loginClick method: ");
        loginMethod();

    }

    public void loginEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            DevToolC.println("Enter key pressed. Preforming Login Action.");

        }
    }

    public void handleDarkButtonClick(ActionEvent actionEvent) {
        System.out.println("Executing handleDarkButtonClick");
    }

    public void exitClick(ActionEvent actionEvent) {
        System.out.println("Executing exitClick");
    }

}
