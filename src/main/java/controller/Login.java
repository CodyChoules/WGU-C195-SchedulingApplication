package controller;

import dataAccessObject.UserDAO;

import applicationTools.CChoulesDevTools;

import applicationTools.CChoulesJTools;
import applicationTools.JDBTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;




import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Login implements Initializable {


    @FXML public Label loginTitle;
    @FXML public Label usernameLabel;
    @FXML private TextField usernameInput;
    @FXML public Label passwordLabel;
    @FXML private TextField passwordInput;
    @FXML public Label locationLabel;
    @FXML private TextField loginScreenLocationField;
    @FXML public Button loginButton;
    @FXML public Label darkModeLabel;
    @FXML public Button darkModeButton; //FXML fx:id References
    @FXML public Button exitButton;

    Stage stage;


    //todo [] transferred this method to MainWindow
    private void loginMethod() throws IOException, InterruptedException {

        // TODO [] create or find a method to sanitize user input remember to implement this into
        //  any account creation as well.
        // TODO [c] Get login info from input.
        String nameInput = CChoulesJTools.sanitizeInput( usernameInput.getText() );
        String passInput = CChoulesJTools.sanitizeInput( passwordInput.getText() );

        // TODO [c] solve login on no input error.
        if (nameInput.isEmpty() || passInput.isEmpty()) {
            CChoulesDevTools.println("Input Values Found Empty, login attempt abandoned.");
            return;
        }

        JDBTools.openConnection();

        //Missing Skill,
        //Skill Found: DAO Use Case:
        // Need to create a Data Access Object(DAO).
        //DAOs are abstractions used to access persistent mechanisms
        //such as Data Bases. Focused often on CRUD (Create, Read,
        // Update, Delete) operations on data entities.

        // TODO [c] create a dataAccessObject package

        // TODO [c] create a DAO to access user date inside the SQL DB
        //Validates user input
        boolean loginValidated = UserDAO.validateUserLogin(nameInput,passInput);

        // TODO [c] create a incorrect password popup
        if (! loginValidated) {
            CChoulesDevTools.println("Input on Password or Username not found. Login attempt abandoned");
            // TODO [c] schedulingApplicationPopup is not firing -The version of java alerts was out of date in the example I was looking at.
            // TODO [c] create an class to store popups settings for this application -SchedulingApplicationPrompt
            //showIncorrectPasswordPopup();
            SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();
            popup.loginFailedPopup();


//

            CChoulesDevTools.println("SchedulingApplicationPrompt Now!");
        }


        if (loginValidated){ CChoulesDevTools.println("User input Validated, Logging in."); }


        //TODO [c] create home menu fxml
        //TODO [c] create load home menu on validation logic //Committing
        if (loginValidated) {


            //Home.loadHomeFXML(stage, loginButton, getClass());

            //TODO [Extra] was attempting to make a a clean method for loading scenes shown below, did not work properly but may revit the idea.
            //Home.loadHomeFXML(loginButton, getClass());

            CChoulesDevTools.println("Loading mainWindow.fxml");

            //Note: this is incorrect I keep doing this bellow:
            //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/mainWindow.fxml"));

            Parent root = loader.load();

            //TODO [Extra] Complete dark mode pass through from this scene to home
//            controller.Home mp = loader.getController();
//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);

            stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);

            CChoulesDevTools.applyDevStyleToScene(scene);

            stage.setScene(scene);
            stage.show();
        }

        //showIncorrectPasswordPopup();


    }

    public void loginClick(ActionEvent actionEvent) throws IOException, InterruptedException {
        System.out.println("Executing loginClick method: ");
        MainWindow newWindow = new MainWindow();
        newWindow.loginMethod(usernameInput.getText(),passwordInput.getText(),loginButton);

    }

    public void loginEnter(KeyEvent keyEvent) throws IOException, InterruptedException {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            CChoulesDevTools.println("Enter key pressed. Preforming Login Action.");

            loginMethod();

        }
    }

    public void handleDarkButtonClick(ActionEvent actionEvent) {
        System.out.println("Executing handleDarkButtonClick");
    }

    public void exitClick(ActionEvent actionEvent) {
        System.out.println("Executing exitClick");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (CChoulesDevTools.toolsState()) {
            passwordInput.setText("test");
            usernameInput.setText("test");
        }
    }
}
