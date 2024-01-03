package controller;


import applicationObject.Appointment;
import applicationObject.Contact;
import applicationObject.guiObject.Searcher;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import applicationTools.LocalDateTimeApplicationTool;
import dataAccessObject.AppointmentDAO;
import dataAccessObject.ContactDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentController {
    private static Stage mainStage; //This is for page refresh. May not work with tabs

    // TOP BAR //
    @FXML
    public Button backToHomeFromAp;



    @FXML
    public void backToHomeAction(ActionEvent actionEvent) throws IOException {

        loadThisFXML(mainStage); //currently set to refresh page for testing
    }

    // MAIN TABLE //
    public TableView<applicationObject.Appointment> primaryApTable;
    @FXML
    public TableColumn<?, ?> apTitle;
    @FXML
    public TableColumn<?, ?> apType;
    @FXML
    public TableColumn<?, ?> apLocation;
    @FXML
    public TableColumn<?, ?> apId;
    @FXML
    public TableColumn<?, ?> apDescription;
    @FXML
    public TableColumn<?, ?> apStart;
    @FXML
    public TableColumn<?, ?> apEnd;
    @FXML
    public TableColumn<?, ?> apCustomerId;
    @FXML
    public TableColumn<?, ?> apContact;
    @FXML
    public TableColumn<?, ?> apContactId;
    @FXML
    public TableColumn<?, ?> apUserId;

    public void handleTableDoubleClick(MouseEvent mouseEvent) throws SQLException {
        //TODO [c] Test Double Click - success
        if (mouseEvent.getClickCount() == 2) {
            selectAppointment();
        }
    }

// SEARCH BAR //
    @FXML
    public TextField searchBar;

    @FXML
    public void searchBarEnter(KeyEvent keyEvent) throws SQLException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            applicationObject.guiObject.Searcher.updateFromSearch(searchBar, primaryApTable);
        }
    }

    //Radio Buttons//
    LocalDateTime timeWindowStart = null;
    LocalDateTime timeWindowEnd = null;

    @FXML
    public ToggleGroup apRangeToggle;

    @FXML
    public RadioButton apWeekRadial;

    public void apWeekSelected(ActionEvent actionEvent) throws SQLException {
        //Question [] after trying a bunch of the minus options this seems to be working. Double check with professor.
        timeWindowStart = LocalDateTime.now().minusWeeks(1);
        timeWindowEnd = LocalDateTime.now().plusWeeks(1);

        constrictTimeFrame();
    }

    public void constrictTimeFrame() throws SQLException {
        ObservableList<Appointment> allApList = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> apWithinTime = FXCollections.observableArrayList();

        if (timeWindowStart == null || timeWindowEnd == null){
            primaryApTable.setItems(allApList);
            return;
        }

        allApList.forEach(appointment -> {
            if (appointment.getApEnd().isAfter(timeWindowStart)
                &&  appointment.getApStart().isBefore(timeWindowEnd)) {
                apWithinTime.add(appointment);
            }
            primaryApTable.setItems(apWithinTime);
        });
    }

    @FXML
    public RadioButton apMonthRadio;

    public void apMonthSelected(ActionEvent actionEvent) throws SQLException {
        //Question [] after trying a bunch of the minus options this seems to be working. Double check with professor.
        timeWindowStart = LocalDateTime.now().minusMonths(1);
        timeWindowEnd = LocalDateTime.now().plusMonths(1);

        constrictTimeFrame();
    }

    @FXML
    public RadioButton apAllRadio;

    public void apAllSelected(ActionEvent actionEvent) throws SQLException {
        //Question [] after trying a bunch of the minus options this seems to be working. Double check with professor.
        timeWindowStart = null;
        timeWindowEnd = null;

        constrictTimeFrame();
    }


//APPOINTMENT EDITOR//
    //TABLE SELECTION CONTROL//

    // TODO [c] Implement selectAppointment() into table actionEvent
    @FXML
    public void selectAppointment() throws SQLException {
        ////If tab update is open {
        if (updateApTab.isSelected()) {

            //TODO [Extra] Create an update ap table method and implement it here like you did for delete table
            Appointment selectedAp = primaryApTable.getSelectionModel().getSelectedItem();
            if (selectedAp == null) {

                ////get the DAO info for the corresponding object values (We have not set up otherDAO
                return;
            }

            //TODO [c] add the ability to retrieve data from non-appointment DAO -this must be done after DAOs are completed
            //Create Contact List
            ObservableList<Contact> contactList = ContactDAO.getAllContacts();
            Contact selectedApContact = ContactDAO.findNameOfContactFromId(selectedAp.apContactID);


            //Found This' Importing Collectors and testing now Delete if fail
            //TODO [c] Create logic to convert contactList to names so they can be displayed in the selection box
            ObservableList<String> contactNames = contactList.stream()
                    .map(Contact::getContactName)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
            //TODO [Extra] Redundant code above refine if you have time


            //TODO [c] fill update fields on selection of appointment
            assert selectedAp != null;
            apTitleUpdate.setText(selectedAp.getApTitle());
            apTypeUpdate.setText(selectedAp.getApType());
            apLocationUpdate.setText(selectedAp.getApLocation());
            apIdUpdate.setText(String.valueOf(selectedAp.getApId()));
            apStartDateUpdate.setValue(selectedAp.getApStart().toLocalDate());
            apStartTimeUpdate.setValue(String.valueOf(selectedAp.getApStart().toLocalTime()));
            apEndDateUpdate.setValue(selectedAp.getApEnd().toLocalDate());
            apEndTimeUpdate.setValue(String.valueOf(selectedAp.getApEnd().toLocalTime()));
            // TODO [c] Bug on apCustomerIdUpdate fix me. -Found: fxml changed to apCustomerIdUpdateID11 on git commit IDK why -Tested: Fixed, frustrating that FX:ID variables do not warn of unreferenced fxml fx:id on variable.
            // TODO [Extra] make a note of this. How fxml & Java make an environment where bugs are easy to make and difficult to detect, standing in direct contradiction to the highly abstracted, object oriented, and design constrained model that we use java for in the first place.
            apCustomerIdUpdate.setText(String.valueOf(selectedAp.getApCustomerId()));
            apContactUserIdUpdate.setText(String.valueOf(selectedAp.getApUserId()));
            apContactUpdate.setItems(contactNames);
            //add contact name . set value . selectedAp . getContactName
            apContactUpdate.setValue(selectedAp.getApContactName());
            // TODO [c] make "contactName" in appointment obj getting it from Contact
            apDescriptionUpdate.setText(String.valueOf(selectedAp.getApUserId()));
        }
        else if (addApTab.isSelected()) {
            //TODO [c] Logic for add tab repeated from update tab
            Appointment selectedAp = primaryApTable.getSelectionModel().getSelectedItem();
            if (selectedAp == null) {
                return;
            }

            ObservableList<Contact> contactList = ContactDAO.getAllContacts();
            Contact selectedApContact = ContactDAO.findNameOfContactFromId(selectedAp.apContactID);

            ObservableList<String> contactNames = contactList.stream()
                    .map(Contact::getContactName)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));


            assert selectedAp != null;

            apTitleAdd.setText(selectedAp.getApTitle());
            apTypeAdd.setText(selectedAp.getApType());
            apLocationAdd.setText(selectedAp.getApLocation());
            apIdAdd.setText(String.valueOf(selectedAp.getApId()));
            apStartDateAdd.setValue(selectedAp.getApStart().toLocalDate());
            apStartTimeAdd.setValue(String.valueOf(selectedAp.getApStart().toLocalTime()));
            apEndDateAdd.setValue(selectedAp.getApEnd().toLocalDate());
            apEndTimeAdd.setValue(String.valueOf(selectedAp.getApEnd().toLocalTime()));
            apCustomerIdAdd.setText(String.valueOf(selectedAp.getApCustomerId()));
            apContactUserIdAdd.setText(String.valueOf(selectedAp.getApUserId()));
            apContactAdd.setItems(contactNames);
            //add contact name . set value . selectedAp . getContactName
            apContactAdd.setValue(selectedAp.getApContactName());
            apDescriptionAdd.setText(String.valueOf(selectedAp.getApUserId()));
        }
        else if (delApTab.isSelected()) {
            Appointment selectedAp = primaryApTable.getSelectionModel().getSelectedItem();
            addApToBeDeleted(selectedAp);
        }
        //  }
    }

    //UPDATE APPOINTMENT TAB//
    @FXML
    public Tab updateApTab;
    @FXML
    public Tab addApTab;
    @FXML
    public TextField apTitleUpdate;
    @FXML
    public TextField apTypeUpdate;
    @FXML
    public TextField apLocationUpdate;
    @FXML
    public TextField apIdUpdate;
    @FXML
    public DatePicker apStartDateUpdate;
    @FXML
    public ComboBox<String> apStartTimeUpdate;
    @FXML
    public DatePicker apEndDateUpdate;
    @FXML
    public ComboBox<String> apEndTimeUpdate;
    @FXML
    public TextField apCustomerIdUpdate; //
    @FXML
    public ComboBox<String> apContactUpdate;
    // ^ TODO [c] rename this to IDK: apContactNameUpdate
    // ^ TODO [Extra] create an event to add or Edit selected Contact.
    @FXML
    public TextField apContactUserIdUpdate;
    // ^ Question [] is there a difference from 'user' and 'Contact'
    @FXML
    public Button apMakeUpdateButton;

    @FXML
    public void apContactUpdateSelect(ActionEvent actionEvent) throws SQLException {
        //TODO [c] create a method to update contact ID field on selection of combo Box
        CChoulesDevTools.println("Updating ID of contact selection");

        //Name of selection = get selection
        String nameOfContact = apContactUpdate.getValue();
        //create new contact = contactDAO find contact with name of selection
        int matchingContactId = ContactDAO.findContactIdFromName(nameOfContact);
        //set apContactUserIdUpdate = contact.getId
        apContactUserIdUpdate.setText(String.valueOf(matchingContactId));
    }

    @FXML
    public void makeApUpdate(ActionEvent actionEvent) throws SQLException {

        CChoulesDevTools.println("Updating selected Appointment.");

        //TODO [c] replace selected appointment in the appointment list using ID with updated appointment. then update the DB. Or because there may be 2+ people updating to the database at the same time update the database object then reload the Appointment list. Tested: worked & prevented conflict with another instance of the application.
        //TODO [c] Make a method to parse local date & time from strings to convert to LocalDateTime. This will be application wide so add to application tools. TESTED: works Implementation was more difficult than anticipated involving new String formatting methods.

        LocalDateTime apStartEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apStartDateUpdate.getEditor().getText(), apStartTimeUpdate.getValue());
        CChoulesDevTools.println(apStartDateUpdate.getValue().toString());
        LocalDateTime apEndEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apEndDateUpdate.getEditor().getText(), apEndTimeUpdate.getValue());
        //TODO [c] Find a Way to get the Field to parse to LocalDateTime -First error: Text '2020-05-28 12:00' could not be parsed at index 2- FOUND: utilize string Formatting tools in Java properly parse field input to MM-dd-yyyy

        if (apEndEdited == null || apStartEdited == null) {
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
                Integer.parseInt(apContactUserIdUpdate.getText())
        );

        try {
            AppointmentDAO.updateAppointment(userEditOfAp, JDBTools.getConnection());
            tableLoadFromDB();
        } catch (SQLException e) {
            CChoulesDevTools.println(e.toString());
        }


    }

    @FXML
    public TextArea apDescriptionUpdate;

    //ADD APPOINTMENT TAB//
    @FXML
    public TextField apTitleAdd;
    @FXML
    public TextField apTypeAdd;
    @FXML
    public TextField apLocationAdd;
    @FXML
    public TextField apIdAdd;
    @FXML
    public DatePicker apStartDateAdd;
    @FXML
    public ComboBox<String> apStartTimeAdd;
    @FXML
    public DatePicker apEndDateAdd;
    @FXML
    public ComboBox<String> apEndTimeAdd;
    @FXML
    public TextField apCustomerIdAdd;
    @FXML
    public ComboBox<String> apContactAdd;
    @FXML
    public TextField apContactUserIdAdd;
    @FXML
    public Button apMakeAdd;

    @FXML
    public void commitAdd(ActionEvent actionEvent) throws SQLException {
        //Question [] another example of repeating code how do I not extend the functionality of 'makeApUpdate' to this method replacing the object references & adding a few adjustments.
        //TODO [c] Create a commit add method that add ap to db
        //TODO [c] bug on add ap appointment added then bugged. Bug fixed.
        CChoulesDevTools.println("Adding Appointment.");

        LocalDateTime apStartEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apStartDateAdd.getEditor().getText(), apStartTimeAdd.getValue());
        CChoulesDevTools.println(apStartDateAdd.getValue().toString());
        LocalDateTime apEndEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apEndDateAdd.getEditor().getText(), apEndTimeAdd.getValue());

        if (apEndEdited == null || apStartEdited == null) {
            //TODO [l] create a popup or indicator to prompt user of bad input
            CChoulesDevTools.println("Date format not accepted, aborting update.");
            return;
        }

        Appointment userAddedAp = new Appointment(
                apTitleAdd.getText(),
                apTypeAdd.getText(),
                apLocationAdd.getText(),
                0,
                apDescriptionAdd.getText(),
                apStartEdited,
                apEndEdited,
                Integer.parseInt(apCustomerIdAdd.getText()),
                Integer.parseInt(apContactUserIdAdd.getText()),
                Integer.parseInt(apDescriptionAdd.getText())
        );

        try {
            //TODO [c] create a an addAppointment method to AppointmentDAO
            AppointmentDAO.addAppointment(userAddedAp, JDBTools.getConnection());
            tableLoadFromDB();
            //TODO [c] try to create a table load method outside of initialize to deal with bug a1
        } catch (SQLException e) {
            CChoulesDevTools.println(e.toString());
        }


    }


    @FXML
    public TextArea apDescriptionAdd;


    public void addSelectionToDeletion(ActionEvent actionEvent) {
    }

    //DELETE APPOINTMENT TAB//
    ObservableList<Appointment> toBeDeletedApList = FXCollections.observableArrayList();
    //Question [] is there a way to note things when I type them for future use. For Example i forgot FXCollections.observableArrayList() and was trying to do : new Observable list instead. It there a more efficient way to remember these things.
    @FXML public Tab delApTab;
    @FXML public TableView<applicationObject.Appointment> delApTable;
    @FXML public TableColumn<?, ?> delApTitle;
    @FXML public TableColumn<?, ?> delApType;
    @FXML public TableColumn<?, ?> delApLocation;
    @FXML public TableColumn<?, ?> delApId;
    @FXML public TableColumn<?, ?> delApDescription;
    @FXML public TableColumn<?, ?> delApStart;
    @FXML public TableColumn<?, ?> delApEnd;
    @FXML public TableColumn<?, ?> delApCustomerId;
    @FXML public TableColumn<?, ?> delApContactName;
    @FXML public TableColumn<?, ?> delApContactId;
    @FXML public TableColumn<?, ?> delApUserId;
    @FXML public Button addApToDelete;
    //TODO [c] add to delete method -> on double click while delete tab to add to delete table.
    @FXML public void deleteAppointments(ActionEvent actionEvent) throws SQLException {
        for (Appointment appointment:toBeDeletedApList){
            AppointmentDAO.deleteAppointment(appointment.getApId(), JDBTools.getConnection());
        }
        tableLoadFromDB();
    }
    
    public void addApToBeDeleted(Appointment apToBeDeleted){
        if (toBeDeletedApList.contains(apToBeDeleted)){
            toBeDeletedApList.remove(apToBeDeleted);
        } else {
            toBeDeletedApList.add(apToBeDeleted);
        }
        updateDeleteTable();
    }

    public void updateDeleteTable(){   

        delApId.setCellValueFactory(new PropertyValueFactory<>("apId"));
        delApTitle.setCellValueFactory(new PropertyValueFactory<>("apTitle"));
        delApDescription.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
        delApLocation.setCellValueFactory(new PropertyValueFactory<>("apLocation"));
        delApType.setCellValueFactory(new PropertyValueFactory<>("apType"));
        delApStart.setCellValueFactory(new PropertyValueFactory<>("apStart"));
        delApEnd.setCellValueFactory(new PropertyValueFactory<>("apEnd"));
        delApCustomerId.setCellValueFactory(new PropertyValueFactory<>("apCustomerId"));
        delApContactName.setCellValueFactory(new PropertyValueFactory<>("apContactName"));
        delApContactId.setCellValueFactory(new PropertyValueFactory<>("apContactId"));
        delApUserId.setCellValueFactory(new PropertyValueFactory<>("apUserId"));

        delApTable.setItems(toBeDeletedApList);

        CChoulesDevTools.println("//APPOINTMENT LIST//");

        for (applicationObject.Appointment appointment : toBeDeletedApList) {
            CChoulesDevTools.println("Title: " + appointment.getApTitle());
            CChoulesDevTools.println("Type: " + appointment.getApType());
            CChoulesDevTools.println("Location: " + appointment.getApLocation());
            CChoulesDevTools.println("ID: " + appointment.getApId());
            CChoulesDevTools.println("Description: " + appointment.getApDescription());
            CChoulesDevTools.println("Start: " + appointment.getApStart());
            CChoulesDevTools.println("End: " + appointment.getApEnd());
            CChoulesDevTools.println("CustomerController ID: " + appointment.getApCustomerId());
            CChoulesDevTools.println("Contact ID: " + appointment.getApContactId());
            CChoulesDevTools.println("User ID: " + appointment.getApUserId() +"\n");
        }
    }


    @FXML
    public Button deleteAppointment;

    //NON-SCENE ELEMENTS//
    //SCENE INITIALIZATION//
    public void initialize() throws SQLException {

        tableLoadFromDB();

        //UPDATE TIME COMBO BOX//
        List<ComboBox<String>> comboBoxTimeList = Arrays.asList(
                apStartTimeUpdate,
                apEndTimeUpdate,
                apStartTimeAdd,
                apEndTimeAdd);
        for (ComboBox<String> comboBox : comboBoxTimeList) {
            Searcher.nameListener(
                    comboBox,
                    Searcher.timeIntervalsList()
            );
        }
        //TODO [Extra] make note of this: Is there a better way to write this? I feel like making a list is a bad way to reduce redundancy here.

        //UPDATE CONTACT NAME//
        //CONTACT LIST//
        ObservableList<applicationObject.Contact> contactList = ContactDAO.getAllContacts();

        CChoulesDevTools.println("Updating Contact names");
        List<ComboBox<String>> comboBoxNameList = Arrays.asList(
                apContactUpdate,
                apContactAdd);
        for (ComboBox<String> comboBox : comboBoxNameList) {
            ObservableList<String> contactNames = contactList.stream()
                    .map(Contact::getContactName)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
            Searcher.nameListener(comboBox, contactNames);
            //apContactUpdate.setItems(contactNames);
        }

        //Name of selection = get selection
        String nameOfContact = apContactUpdate.getValue();
        //create new contact = contactDAO find contact with name of selection
        int matchingContactId = ContactDAO.findContactIdFromName(nameOfContact);
        //set apContactUserIdUpdate = contact.getId
        apContactUserIdUpdate.setText(String.valueOf(matchingContactId));

        //ADD CONTACT NAME//



    }

    //Table load//

    public void tableLoadFromDB() throws SQLException {
    //APPOINTMENT LIST//
    ObservableList<applicationObject.Appointment> appointmentList = AppointmentDAO.getAllAppointments();


    //TODO [l] After you complete the applicationObject & DAO for Client Name include access to it here
    // In addition change the contactName to CustomerController Name (CustomerController Name is important for meetings)
//        String sqlQueryForContact = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, contactIdParameter);

        apId.setCellValueFactory(new PropertyValueFactory<>("apId"));
        apTitle.setCellValueFactory(new PropertyValueFactory<>("apTitle"));
        apDescription.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
        apLocation.setCellValueFactory(new PropertyValueFactory<>("apLocation"));
        apType.setCellValueFactory(new PropertyValueFactory<>("apType"));
        apStart.setCellValueFactory(new PropertyValueFactory<>("apStart"));
        apEnd.setCellValueFactory(new PropertyValueFactory<>("apEnd"));

    //Naming conventions are off on Appointment
    //TODO [c] Bug Non-Appointment ID are not working after updating naming convention. --Found: Seriously? apCustomerId string is converted to getApCustomerId, then calls the method by the same name. The actual Appointment.getCustomerId has no "Ap" fixing now.--Tested: Finding Correct -> Bug removed.
    //TODO [c] same bug happened again refactoring apCustomerId to ...Id did not refactor here resulting in null value. This is from propertyValueFactory code injection from string alteration.


    //TODO [Extra] When the appointment is add or updated check to see if the Id of contact matches what is in the data base to make sure the database has not been edited since the start of the edit.
        apCustomerId.setCellValueFactory(new PropertyValueFactory<>("apCustomerId"));
    //ToDo [c] include contact name
        apContact.setCellValueFactory(new PropertyValueFactory<>("apContactName"));
    //TODO [c] Solved: how does the PropertyValueFactory decide where to access the list name? Perhaps it is better to access the contactName through the appointment applicationObj instead. -Found -> its set by primaryApTable.setItems Decided to update appointment class to include a getApContactName

        apContactId.setCellValueFactory(new PropertyValueFactory<>("apContactId"));
        apUserId.setCellValueFactory(new PropertyValueFactory<>("apUserId"));
    // TODO [Extra] Make a comment on how frustrating PropertyValueFactory values are.
    //  -we should not be using strings here, why javaFX? -And code injection with a string alteration? Are you kidding? I can't inject folders of code as local code to keep my code organized(at least no to my beginner level of knowledge) but JavaFX is making me work with this very insane way of calling methods?
    //

        primaryApTable.setItems(appointmentList);
        constrictTimeFrame();

        CChoulesDevTools.println("//APPOINTMENT LIST//");

        for (applicationObject.Appointment appointment : appointmentList) {
            CChoulesDevTools.println("Title: " + appointment.getApTitle());
            CChoulesDevTools.println("Type: " + appointment.getApType());
            CChoulesDevTools.println("Location: " + appointment.getApLocation());
            CChoulesDevTools.println("ID: " + appointment.getApId());
            CChoulesDevTools.println("Description: " + appointment.getApDescription());
            CChoulesDevTools.println("Start: " + appointment.getApStart());
            CChoulesDevTools.println("End: " + appointment.getApEnd());
            CChoulesDevTools.println("CustomerController ID: " + appointment.getApCustomerId());
            CChoulesDevTools.println("Contact ID: " + appointment.getApContactId());
            CChoulesDevTools.println("User ID: " + appointment.getApUserId() +"\n");
        }
    }

    //FXML LOADER METHOD//
    public static void loadThisFXML(Stage stage) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        CChoulesDevTools.println("Loading AppointmentController.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(AppointmentController.class.getResource("/views/appointments.fxml"));


        Parent root = loader.load();

        AppointmentController mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        mainStage = stage;
    }


    public void loginEnter(KeyEvent keyEvent) {
    }
}
