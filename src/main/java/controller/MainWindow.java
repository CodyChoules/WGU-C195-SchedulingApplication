package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class MainWindow {


    @FXML public TabPane tabPane;
    @FXML public AnchorPane anchorPane;

    //home>
    @FXML public Button openAppointmentsButton;
    public void openAppointments(ActionEvent actionEvent) {
        handleActionToOpenTabWithFxml(actionEvent, getClass().getResource("/views/appointments.fxml"));
    }

    @FXML public Button openCustomersButton;
    public void openCustomer(ActionEvent actionEvent) {
        handleActionToOpenTabWithFxml(actionEvent, getClass().getResource("/views/customers.fxml"));
    }

    @FXML public Button openReportsButton;
    public void openReports(ActionEvent actionEvent) {
        handleActionToOpenTabWithFxml(actionEvent, getClass().getResource("/views/reports.fxml"));
    }
    //<home




    //Test to be deleted
    Test test=new Test();
    public void handleButtonClick(ActionEvent actionEvent) {
        test.handleActionToOpenTabWithFxml(actionEvent, getClass().getResource("/views/subViews/subTest.fxml"), tabPane);
    }

    public void handleActionToOpenTabWithFxml(ActionEvent event, URL fxmlUrl ){
        try {
            System.out.println("Button Clicked!");

            //TODO [] there were problems getting Resource from here
            // Try again once everything is working on home.
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            AnchorPane subTestContent = loader.load();

            Tab tab = new Tab();
            tab.setText("This is a new tab");
            tab.setContent(subTestContent);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
