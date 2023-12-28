package controller;

import applicationTools.CChoulesDevTools;
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

    // TODO [c] learn how to sanitize and validate user inputs for sql.
    // answer - https://www.codecademy.com/learn/seasp-defending-node-applications-from-sql-injection-xss-csrf-attacks/modules/seasp-preventing-sql-injection-attacks/cheatsheet - This shows our parameter solution is good enough for the scope of this project , but just in case we will sanitize user input for SQL queries as well.
    /*
     TODO [c] Scaffold all scenes/controllers here with fxml layout done.
      --create a test scaffold then prepare actions & structure below
      --structure
            login -c-> mainWindow { tabs:
            -->Appointments {add brings up split pane}
            -->Customer (Edit,Delete,Add New) -->Save
            -->Reports {subTabs:
                -->Contact Schedule
                -->Appointment Totals
                -->Customer by Country
            -->Exit

            -optional-> Logout
     TODO [c] had difficulty bringing multiple controllers to interact
      with mainWindow. Test to see including all needed code inside
      1 controller. This was mainly around the tabs pain interaction.
      FOUND: Unfortunately using the "include fxml" feature does allow a clear way for the controller to communicat with the parent fxml elements & controller. We will have to revert to a unified FXML & Controller to utilize tabs in fxml.
      SOLUTION: Setting up a new Window controller that will contain all controllers once they are tested and functioning.

     TODO [Extra] logout button.
     TODO [l] exit button -remember to close connection.
     TODO [l] Clean up Login class.
     TODO [c] Complete appointment controller.
    */

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

        CChoulesDevTools.println("Loading HomeMenu.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(passGetClassMethod.getResource("/views/HomeMenu.fxml"));

        Parent root = loader.load();

        controller.Home mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);

        stage = (Stage) buttonBringingUsHome.getScene().getWindow();
        Scene scene = new Scene(root);

        CChoulesDevTools.applyDevStyleToScene(scene);

        stage.setScene(scene);
        stage.show();
    }

    //TODO [l] create a method to load subview or load FAILED DELETE THIS entire class and organize the notes.
    public static void loadHomeFxmlWithSubView(Stage stage, Button buttonBringingUsHome, Class passGetClassMethod) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        CChoulesDevTools.println("Loading HomeMenu.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(passGetClassMethod.getResource("/views/HomeMenu.fxml"));

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
        CChoulesDevTools.println("This is a test indicating a successful method call.");
    }

}
