package controller;

import applicationObject.Appointment;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import applicationTools.LocalDateTimeApplicationTool;
import com.sun.javafx.scene.control.InputField;
import dataAccessObject.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointments {

    // TOP BAR //
    @FXML public Button backToHomeFromAp;



    @FXML public void backToHomeAction(ActionEvent actionEvent) {
    }

    // MAIN TABLE //
     public TableView<applicationObject.Appointment> primaryApTable;
    @FXML public TableColumn<?, ?> apTitle;
    @FXML public TableColumn<?, ?> apType;
    @FXML public TableColumn<?, ?> apLocation;
    @FXML public TableColumn<?, ?> apID;
    @FXML public TableColumn<?, ?> apDescription;
    @FXML public TableColumn<?, ?> apStart;
    @FXML public TableColumn<?, ?> apEnd;
    @FXML public TableColumn<?, ?> apCustomerID;
    @FXML public TableColumn<?, ?> apContact;
    @FXML public TableColumn<?, ?> apContactID;
    @FXML public TableColumn<?, ?> apUserID;

    public void handleTableDoubleClick(MouseEvent mouseEvent) {
            //TODO [cu] Test Double Click - success
        if (mouseEvent.getClickCount() == 2) {
            selectAppointment();
        }
    }



    // SEARCH BAR //
        //Radio Buttons//
    @FXML public ToggleGroup apRangeToggle;
    
    @FXML public RadioButton apWeekRadial;
    public void apWeekSelected(ActionEvent actionEvent) {
    }
    @FXML public RadioButton apMonthRadio;
    public void apMonthSelected(ActionEvent actionEvent) {
    }
    @FXML public RadioButton apAllRadio;
    public void apAllSelected(ActionEvent actionEvent) {
    }
    
//APPOINTMENT EDITOR//
    //TABLE SELECTION CONTROL//

    // TODO [cu] Implement selectAppointment() into table actionEvent
    @FXML public void selectAppointment() {
        ////If tab update is open {

        Appointment selectedAp = primaryApTable.getSelectionModel().getSelectedItem();
        if (selectedAp != null) {
            //TODO [L] add the ability to retrieve data from non-appointment DAO
            // -this must be done after DAOs are completed
            ////get the DAO info for the corresponding object values (We have not set up otherDAO
        }

        //TODO [cu] fill update fields on selection of appointment
        assert selectedAp != null;
        apTitleUpdate.setText(selectedAp.getApTitle());
        apTypeUpdate.setText(selectedAp.getApType());
        apLocationUpdate.setText(selectedAp.getApLocation());
        apIdUpdate.setText(String.valueOf(selectedAp.getApID()));
        apStartDateUpdate.setValue(selectedAp.getApStart().toLocalDate());
        apStartTimeUpdate.setValue(String.valueOf(selectedAp.getApStart().toLocalTime()));
        apEndDateUpdate.setValue(selectedAp.getApEnd().toLocalDate());
        apEndTimeUpdate.setValue(String.valueOf(selectedAp.getApEnd().toLocalTime()));
        // TODO [cu] Bug on apCustomerIdUpdate fix me.
        //  -Found: fxml changed to apCustomerIdUpdateID11 on git commit IDK why
        //  -Tested: Fixed, frustrating that FX:ID variables do not warn of unreferenced fxml fx:id on variable.
        // TODO [Extra] make a note of this. How fxml & Java make an environment where bugs are easy to make and difficult to detect, standing in direct contradiction to the highly abstracted, object oriented, and design constrained model that we use java for in the first place.
        apCustomerIdUpdate.setText(String.valueOf(selectedAp.getApCustomerID()));
        apContactUserIdUpdate.setText(String.valueOf(selectedAp.getApUserID()));
        //add contact name . set value . selectedAp . getContactName
        // TODO [L] make "contactName" in appointment obj getting it from Contact
        apDescriptionUpdate.setText(String.valueOf(selectedAp.getApUserID()));


        //  }
    }
    //UPDATE APPOINTMENT TAB//
    @FXML public TextField apTitleUpdate;
    @FXML public TextField apTypeUpdate;
    @FXML public TextField apLocationUpdate;
    @FXML public TextField apIdUpdate;
    @FXML public DatePicker apStartDateUpdate;
    @FXML public ComboBox<String> apStartTimeUpdate;
    @FXML public DatePicker apEndDateUpdate;
    @FXML public ComboBox<String> apEndTimeUpdate;
    @FXML public TextField apCustomerIdUpdate; //
    @FXML public ComboBox<String> apContactUpdate;
    // ^ TODO [cu] rename this to IDK: apContactNameUpdate
    // ^ TODO [Extra] create an event to add or Edit selected Contact.
    @FXML public TextField apContactUserIdUpdate;
    // ^ TODO [L] is there a difference from 'user' and 'Contact'
    @FXML public Button apMakeUpdateButton;
    @FXML public void makeApUpdate(ActionEvent actionEvent) throws SQLException {

        CChoulesDevTools.println("Updating selected Appointment.");

        //TODO [cu] replace selected appointment in the appointment list using ID with updated appointment. then update the DB. Or because there may be 2+ people updating to the database at the same time update the database object then reload the Appointment list. Tested: worked & prevented conflict with another instance of the application.
        //TODO [cu] Make a method to parse local date & time from strings to convert to LocalDateTime. This will be application wide so add to application tools. TESTED: works Implementation was more difficult than anticipated involving new String formatting methods.


        LocalDateTime apStartEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apStartDateUpdate.getEditor().getText(), apStartTimeUpdate.getValue());
        CChoulesDevTools.println(apStartDateUpdate.getValue().toString());
        LocalDateTime apEndEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apEndDateUpdate.getEditor().getText(), apEndTimeUpdate.getValue());
        //TODO [cu] Find a Way to get the Field to parse to LocalDateTime -First error: Text '2020-05-28 12:00' could not be parsed at index 2- FOUND: utilize string Formatting tools in Java properly parse field input to MM-dd-yyyy

        if (apEndEdited == null || apStartEdited == null){
            //TODO [l] create a popup or indicator to prompt user of bad input
            CChoulesDevTools.println("Date format not accepted, aborting update.");
            return;
        }

        Appointment userEditOfAp = new Appointment(
                apTitleUpdate.getText(),
                apTypeUpdate.getText(),
                apLocationUpdate.getText(),
                Integer.parseInt(apIdUpdate.getText()),
                apDescriptionUpdate.getText(),
                apStartEdited,
                apEndEdited,
                Integer.parseInt(apCustomerIdUpdate.getText()),
                Integer.parseInt(apContactUserIdUpdate.getText()),
                Integer.parseInt(apDescriptionUpdate.getText())
        );

        try {
            AppointmentDAO.updateAppointment(userEditOfAp, JDBTools.getConnection());
            initialize();
        } catch (SQLException e) {
            CChoulesDevTools.println(e.toString());
        }




    }
    @FXML public TextArea apDescriptionUpdate;

    //ADD APPOINTMENT TAB//
    @FXML public TextField apTitleAdd;
    @FXML public TextField apTypeAdd;
    @FXML public TextField apLocationAdd;
    @FXML public TextField apIdAdd;
    @FXML public DatePicker apStartDateAdd;
    @FXML public ComboBox<String> apStartTimeAdd;
    @FXML public DatePicker apEndDateAdd;
    @FXML public ComboBox<String> apEndTimeAdd;
    @FXML public TextField apCustomerIdAdd;
    @FXML public ComboBox<String> apContactAdd;
    @FXML public TextField apContactUserIdAdd;
    @FXML public Button apMakeAdd;
    @FXML public void commitAdd(ActionEvent actionEvent) {
    }


    @FXML public TextArea apDescriptionAdd;


    public void addSelectionToDeletion(ActionEvent actionEvent) {
    }

    //DELETE APPOINTMENT TAB//
    @FXML public TableView<applicationObject.Appointment> mainAppointmentsTable1;
    @FXML public TableColumn<?, ?> appointmentTitle2;
    @FXML public TableColumn<?, ?> appointmentType2;
    @FXML public TableColumn<?, ?> appointmentLocation2;
    @FXML public TableColumn<?, ?> appointmentID2;
    @FXML public TableColumn<?, ?> appointmentDescription2;
    @FXML public TableColumn<?, ?> appointmentStart2;
    @FXML public TableColumn<?, ?> appointmentEnd2;
    @FXML public TableColumn<?, ?> appointmentCustomerID2;
    @FXML public TableColumn<?, ?> tableContactID21;
    @FXML public TableColumn<?, ?> tableContactID2;
    @FXML public TableColumn<?, ?> tableUserID2;
    @FXML public Button addApToDelete;
    public void deleteAppointment(ActionEvent actionEvent) {
    }
    @FXML public Button deleteAppointment;

//NON-SCENE ELEMENTS//
    //SCENE INITIALIZATION//
    public void initialize() throws SQLException{

        //APPOINTMENT LIST//
        ObservableList<applicationObject.Appointment> appointmentList = AppointmentDAO.getAllAppointments();

        //TODO [l] After you complete the applicationObject & DAO for Client Name include access to it here
        // In addition change the contactName to Customer Name (Customer Name is important for meetings)
//        String sqlQueryForContact = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, contactIdParameter);

        apID.setCellValueFactory(new PropertyValueFactory<>("apID"));
        apTitle.setCellValueFactory(new PropertyValueFactory<>("apTitle"));
        apDescription.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
        apLocation.setCellValueFactory(new PropertyValueFactory<>("apLocation"));
        apType.setCellValueFactory(new PropertyValueFactory<>("apType"));
        apStart.setCellValueFactory(new PropertyValueFactory<>("apStart"));
        apEnd.setCellValueFactory(new PropertyValueFactory<>("apEnd"));

        //Naming conventions are off on Appointment
        //TODO [cu] Bug Non-Appointment ID are not working after updating naming convention.
        // -Found: Seriously? apCustomerID string is converted to getApCustomerID, then calls the method by the same name. The actual Appointment.getCustomerID has no "Ap" fixing now.
        // -Tested: Finding Correct -> Bug removed.
        apCustomerID.setCellValueFactory(new PropertyValueFactory<>("apCustomerID"));
        //ToDo [l] include Contact ID to Name
        apContactID.setCellValueFactory(new PropertyValueFactory<>("apContactID"));
        apUserID.setCellValueFactory(new PropertyValueFactory<>("apUserID"));
        // TODO [Extra] Make a comment on how frustrating PropertyValueFactory values are.
        //  -we should not be using strings here, why javaFX? -And code injection with a string alteration? Are you kidding? I can't inject folders of code as local code to keep my code organized(at least no to my beginner level of knowledge) but JavaFX is making me work with this very insane way of calling methods?
        //

        primaryApTable.setItems(appointmentList);

        System.out.println("//APPOINTMENT LIST//");

        for (applicationObject.Appointment appointment : appointmentList) {
            // Adjust the property names based on your Appointment class
            System.out.println("Title: " + appointment.getApTitle());
            System.out.println("Type: " + appointment.getApType());
            System.out.println("Location: " + appointment.getApLocation());
            System.out.println("ID: " + appointment.getApID());
            System.out.println("Description: " + appointment.getApDescription());
            System.out.println("Start: " + appointment.getApStart());
            System.out.println("End: " + appointment.getApEnd());
            System.out.println("Customer ID: " + appointment.getApCustomerID());
            System.out.println("Contact ID: " + appointment.getApContactID());
            System.out.println("User ID: " + appointment.getApUserID());
            System.out.println();
        }

    }


    //FXML LOADER METHOD//
    public static void loadThisFXML(Stage stage) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        CChoulesDevTools.println("Loading Appointments.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(controller.Appointments.class.getResource("/views/appointments.fxml"));


        Parent root = loader.load();

        controller.Appointments mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
