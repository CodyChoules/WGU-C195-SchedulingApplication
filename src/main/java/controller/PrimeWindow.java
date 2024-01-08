package controller;


import applicationObject.User;
import dataAccessObject.UserDAO;
import applicationTools.CChoulesDevTools;
import applicationTools.CChoulesJTools;
import applicationTools.JDBTools;
import javafx.application.Platform;
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
import main.Main;

import java.io.IOException;
import java.net.URL;

public class PrimeWindow {


    @FXML public TabPane tabPane;
    @FXML public AnchorPane anchorPane;
    Stage stage;



    //HOME UI\\
    @FXML public Button openAppointmentsButton;

    @FXML public void openAppointments(ActionEvent actionEvent) throws Exception {
        Main.loadAppointmentsAsTab(tabPane);
    }

    @FXML public Button openCustomersButton;
    public void openCustomer(ActionEvent actionEvent) throws Exception {
        Main.loadCustomersAsTab(tabPane);
    }

    @FXML public Button openReportsButton;
    public void openReports(ActionEvent actionEvent) throws Exception {
        Main.loadReportsAsTab(tabPane);
    }
    //<home

    //TODO DELETE ME
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

    public void logOutAction(ActionEvent actionEvent) throws IOException {
        Main.currentUser = Main.undefinedUser;
        Main.loginKeeper = new User (0, "", "");
        Main.loadLogin();
    }

    public void exitApplicationActions(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
