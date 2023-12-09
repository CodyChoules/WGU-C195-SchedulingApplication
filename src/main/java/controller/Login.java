package controller;

import controller.Home;

import dataAccessObject.userDAO;

import devTools.DevToolC;

import devTools.JDBTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login {


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

    Stage stage;

    private void loginMethod() throws IOException {

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
        boolean loginValidated = userDAO.validateUserLogin(nameInput,passInput);
        if (loginValidated){ DevToolC.println("User input Validated, Logging in."); }


        //TODO [cu] create home menu fxml
        //TODO [cu] create load home menu on validation logic //Committing
        if (loginValidated) {
            Home.testMethod();
            //TODO [Extra] Complete dark mode pass through from this scene to home
            Home.loadHomeFXML(stage, loginButton, getClass());
        }


        //TODO [] create a incorrect password popup
//        else
//        {
//            showIncorrectPasswordPopup();
//        }

        //TODO [] review rubric



    }

    private void showIncorrectPasswordPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Password");
        alert.setHeaderText(null);
        alert.setContentText("The password you entered is incorrect. Please try again.");

        // Customizing the alert dialog
        //alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-dialog");

        // Adding an OK button
        alert.getButtonTypes().setAll(ButtonType.OK);

//        // Handling the OK button action
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                System.out.println("OK button clicked");
//            }
//        });
    }

    public void loginClick(ActionEvent actionEvent) throws IOException {
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
