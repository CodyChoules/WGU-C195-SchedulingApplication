package controller;

import devTools.DevToolC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

    //TODO [ip] review rubric
    // Missing a focus on the following competencies:
    // Lambda expressions
    // Collections w/ streams & filters
    // Localization API & Date/Time API

    // TODO [] Scaffold all scenes/controller here with fxml layout done
    // TODO [] logout button
    // TODO [] exit button -remember to close connection
    // TODO [] clean up Login class
    // TODO [] Complete appointment controller

    public Button homeAppointmentsButton;
    public Button homeCustomersButton;
    public Button homeReportsButton;
    public Button homeLogOutButton;
    public Button homeExitButton;

    public void homeAppointmentClick(ActionEvent actionEvent) {
    }

    public void homeCustomerClick(ActionEvent actionEvent) {
    }

    public void homeReportsClick(ActionEvent actionEvent) {
    }

    public void logOutClick(ActionEvent actionEvent) {
    }
    
    public void homeExitClick(ActionEvent actionEvent) {
    }

    //TODO [c] Create a method to set open this controller's scene.
    public static void loadHomeFXML(Stage stage, Button buttonBringingUsHome, Class passGetClassMethod) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        DevToolC.println("Loading HomeMenu.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(passGetClassMethod.getResource("/controller/HomeMenu.fxml"));

        Parent root = loader.load();

        controller.Home mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);

        stage = (Stage) buttonBringingUsHome.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void testMethod(){
        DevToolC.println("This is a test indicating a successful method call.");
    }

}
