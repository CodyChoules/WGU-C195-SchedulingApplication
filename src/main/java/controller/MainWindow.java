package controller;


import devTools.CChoulesDevTools;
import devTools.JDBTools;
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


    //home>
    @FXML public Button openAppointmentsButton;
    public void openAppointments(ActionEvent actionEvent) {
        Tab e = new Tab();

        
        tabPane.getTabs().add(e);


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




    //Test to be deleted
    Test test=new Test();
    public void handleButtonClick(ActionEvent actionEvent) {
        test.handleActionToOpenTabWithFxml(actionEvent, getClass().getResource("/views/subViews/subTest.fxml"), tabPane);
    }

    public void handleActionToOpenTabWithFxml(ActionEvent event, URL fxmlUrl, TabPane tabPane){
        try {
            System.out.println("Button Clicked!");

            //TODO [] there were problems getting Resource from here
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
        primaryStage.setTitle("Main Menu");

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
}
