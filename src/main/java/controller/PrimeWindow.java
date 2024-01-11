package controller;


import applicationObject.Appointment;
import applicationObject.User;
import applicationObject.guiObject.SchedulingApplicationPrompt;
import applicationTools.TimeTool;
import applicationTools.CChoulesDevTools;
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
/**
 * Controller class for the PrimeWindow FXML.
 * It handles UI elements and actions related to the main application window including tabs, log out, & exit.
 */
public class PrimeWindow {


    @FXML public TabPane tabPane;
    @FXML public AnchorPane anchorPane;
    @FXML public Label timeValueCurrentAtHQ;
    @FXML public Label timeValueCurrentLocal;
    @FXML public Label timeValueHoursLocal;

    /**
     * Sets the time values for the labels in the UI,
     * including the current time at HQ, current local time,
     * and the range of hours in the local time zone.
     */
    public void setTimeValues() {

//        ArrayList<Label> timers = new ArrayList<Label>(Arrays.asList(
//                timeValueCurrentAtHQ,
//                timeValueCurrentLocal,
//                timeValueHoursLocal
//        ));
//
//        for (Label label : timers) {
//            TimeTool.setLabelToClock(label);
//        }

        TimeTool.setLabelToHQClock(timeValueCurrentAtHQ);
        TimeTool.setLabelToClock(timeValueCurrentLocal);
        timeValueHoursLocal.setText(
                TimeTool.getStartOfHoursAsLocalAsString()
                + " to " +
                TimeTool.getEndOfHoursAsLocalAsString());


    }

    Stage stage;

//HOME UI\\
    @FXML public Button openAppointmentsButton;
    /**
     * Opens the Appointments tab when the Appointments button is clicked.
     * @param actionEvent event
     * @throws Exception If an exception occurs during the loading of the Appointments tab.
     */
    @FXML public void openAppointments(ActionEvent actionEvent) throws Exception {
        Main.loadAppointmentsAsTab(tabPane);
    }

    @FXML public Button openCustomersButton;
    /**
     * Opens the Customers tab when the Customers button is clicked.
     * @param actionEvent event
     * @throws Exception If an exception occurs during the loading of the Customers tab.
     */
    public void openCustomer(ActionEvent actionEvent) throws Exception {
        Main.loadCustomersAsTab(tabPane);
    }

    @FXML public Button openReportsButton;
    /**
     * Opens the Customers tab when the Reports button is clicked.
     * @param actionEvent event
     * @throws Exception If an exception occurs during the loading of the Reports tab.
     */
    public void openReports(ActionEvent actionEvent) throws Exception {
        Main.loadReportsAsTab(tabPane);
    }
    //<home

    /**
     * Logs out and opens login when the logout button is clicked.
     * @param actionEvent event
     * @throws IOException If an exception occurs during the loading of the login.
     */
    public void logOutAction(ActionEvent actionEvent) throws IOException {
        Main.currentUser = Main.undefinedUser;
        Main.loginKeeper = new User (0, "", "");
        Main.loadLogin();
    }

    /**
     * Exits the when the logout button is clicked.
     * @param actionEvent event
     */
    public void exitApplicationActions(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Checks for upcoming appointments for the current user logs in.
     * @return An observable list containing the upcoming appointments.
     * @throws SQLException If an SQL exception occurs while retrieving appointments from the database.
     */
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

    /**
     * Initializes the controller. Sets the time values and checks for upcoming appointments.
     * @throws SQLException If an SQL exception occurs.
     */
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
                        appointment.getApStart().format(TimeTool.formatFullReadable());
            }

            prompt.upcomingApPopup(promptContent);


        }

        //Check for upcoming appointments.
    }


}
