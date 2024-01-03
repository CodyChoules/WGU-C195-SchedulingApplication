package controller;


import dataAccessObject.UserDAO;
import applicationTools.CChoulesDevTools;
import applicationTools.CChoulesJTools;
import applicationTools.JDBTools;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainWindow extends Application {


    @FXML public TabPane tabPane;
    @FXML public AnchorPane anchorPane;
    Stage stage;



    //home>
    @FXML public Button openAppointmentsButton;
    public void openAppointments(ActionEvent actionEvent) {
        //TODO [c] tabPain is not working (NullPointerException) possibly due to implementing "<Include>" into my fxml. If I wish to continue the intended project structure I may have combined all fxml & controllers into 1 controller & 1 fxml. Really do not like this and am frustrated that FXML does not appear to have an intuitive way to reverence fx:id values from parent fxml object when using include. If this was the case can i could open tabs, windows, or any other fxml object using purpose built fxml files to set up more versatile scenes.
        // - Solution - sure enough after hours of research and trial the only solution appears to be removing the <include> fxml functionality and creating 1 big fxml file.I wish that I could find a way to have the included fxml inherit the parent fxml fx:id.
        //Missing Skill This will now require me to create a unified fxml & controller class to keep the original intention and avoid avoid a sapir-whorf like tool constraint.
        Tab newTab = new Tab();
        tabPane.getTabs().add(newTab);
    }

    @FXML public Button openCustomersButton;
    public void openCustomer(ActionEvent actionEvent) {
        handleActionToOpenTabWithFxml(actionEvent, getClass().getResource("/views/customers.fxml"), tabPane);
    }

    @FXML public Button openReportsButton;
    public void openReports(ActionEvent actionEvent) {
        handleActionToOpenTabWithFxml(actionEvent, getClass().getResource("/views/reports.fxml"), tabPane);
    }
    //<home






    public void handleActionToOpenTabWithFxml(ActionEvent event, URL fxmlUrl, TabPane tabPane){
        try {
            System.out.println("Button Clicked!");

            //TODO [l] there were problems getting Resource from here
            // Try again once everything is working on home.
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            AnchorPane subTestContent = loader.load();

            Tab tab = new Tab();
            tab.setText("This is a new tab");
            tab.setContent(subTestContent);
            //This tabPane ends up null no matter what I do.
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewTab(String fxmlFileName, String tabTitle) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            AnchorPane root = loader.load();

            // Create a new tab and set its content
            Tab newTab = new Tab(tabTitle);
            newTab.setContent(root);

            TabPane tabPane1 = this.tabPane;

            // Add the new tab to the TabPane
            tabPane1.getTabs().add(newTab);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOn();

        //Sets Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/windows/mainWindow.fxml")));
        primaryStage.setTitle("main.Main Menu");

        //TODO [Extra] test to decorate stage(remove if no solution found){Good chance to use git to add this feature}
        //CChoulesDevTools.redecorateStage(primaryStage);

        Scene scene = new Scene(root);

        //CChoulesDevTools.setWindowCenteredOnCursor(scene,primaryStage);

        primaryStage.setScene(scene);

        primaryStage.show();

        CChoulesDevTools.applyDevStyleToScene(scene);

        CChoulesDevTools.println("Stage & Scene Set");

        //TODO [c] test DB connection using JBDTools -completed
        JDBTools.openConnection();
        CChoulesDevTools.println("Launching Arguments");
        JDBTools.closeConnection();
    }

   public void loginMethod(String usernameInput,String passwordInput, Button loginButton) throws IOException, InterruptedException {

        // TODO [c] create or find a method to sanitize user input remember to implement this into any account creation as well.
        // TODO [c] Get login info from input.
        String nameInput = CChoulesJTools.sanitizeInput( usernameInput);
        String passInput = CChoulesJTools.sanitizeInput( passwordInput);

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
            FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("/windows/mainWindow.fxml"));

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

}
