package controller;


import applicationObject.Appointment;
import applicationObject.User;
import applicationObject.guiObject.SchedulingApplicationPrompt;
import applicationTools.ApplicationTimeTool;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import dataAccessObject.AppointmentDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PrimeWindow {


    @FXML public TabPane tabPane;
    @FXML public AnchorPane anchorPane;
    @FXML public Label timeValueCurrentAtHQ;
    @FXML public Label timeValueCurrentLocal;
    @FXML public Label timeValueHoursLocal;

    public void setTimeValues() {

//        ArrayList<Label> timers = new ArrayList<Label>(Arrays.asList(
//                timeValueCurrentAtHQ,
//                timeValueCurrentLocal,
//                timeValueHoursLocal
//        ));
//
//        for (Label label : timers) {
//            ApplicationTimeTool.setLabelToClock(label);
//        }

        ApplicationTimeTool.setLabelToHQClock(timeValueCurrentAtHQ);
        ApplicationTimeTool.setLabelToClock(timeValueCurrentLocal);
        timeValueHoursLocal.setText(
                ApplicationTimeTool.getStartOfHoursAsLocalAsString()
                + " to " +
                ApplicationTimeTool.getEndOfHoursAsLocalAsString());


    }


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

    public ObservableList<Appointment> checkForUpcomingAppointments() throws SQLException {
        //Get all AppointmentsDAO
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();

        //Create Empty list of upcoming appointments
        ObservableList<Appointment> upcomingList = FXCollections.observableArrayList();

        //Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        //For each Appointment.getStartTime is after now & is before now
        // & Appointment.getUserId == currentUser.id add appointment to upcoming list
        for (Appointment appointment : allAppointments) {
            LocalDateTime startTime = appointment.getApStart();
            int userId = appointment.getApUserId(); // Assuming getUserId() returns the user ID

            // Check if the appointment is upcoming and belongs to the current user
            if (startTime.isAfter(now) && userId == Main.currentUser.getId()) {
                upcomingList.add(appointment);
            }
        }

        //return upcoming list
        return upcomingList;

    }

    public void initialize() throws SQLException {
        setTimeValues();

        ObservableList<Appointment> upAp = checkForUpcomingAppointments();

        CChoulesDevTools.println(upAp.toString());
        if (!upAp.isEmpty()){
            SchedulingApplicationPrompt prompt = new SchedulingApplicationPrompt();

            CChoulesDevTools.println("User ID is: " + Main.currentUser.getUserId());


            String promptContent = "You have the following upcoming Appointments within 15 min." +
                    "\nTitle : " +
                    "ID : " +
                    "Type : " +
                    "Time";

            for (Appointment appointment: upAp){
                promptContent = promptContent +
                        "\n" +
                        appointment.getApTitle() +
                        " : " +
                        appointment.getApId() +
                        " : " +
                        appointment.getApType() +
                        " : " +
                        appointment.getApStart().format(ApplicationTimeTool.formatFullReadable());
            }

            prompt.upcomingApPopup(promptContent);


        }

        //Check for upcoming appointments.
    }


}
