package controller;


import applicationObject.Appointment;
import applicationObject.Contact;
import applicationObject.Customer;
import applicationObject.guiObject.SchedulingApplicationPrompt;
import applicationObject.guiObject.Searcher;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import applicationTools.LocalDateTimeApplicationTool;
import dataAccessObject.AppointmentDAO;
import dataAccessObject.ContactDAO;
import dataAccessObject.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller class for managing appointments in the appointments tab of the project.
 * Includes controls for the three sub tabs update, add, and delete Appointment Tabs
 */
public class ViewAppointments {
    private static Stage mainStage; //This is for page refresh. May not work with tabs

    // MAIN TABLE //
    @FXML public TableView<applicationObject.Appointment> primaryApTable;
    @FXML public TableColumn<?, ?> apTitle;
    @FXML public TableColumn<?, ?> apType;
    @FXML public TableColumn<?, ?> apLocation;
    @FXML public TableColumn<?, ?> apId;
    @FXML public TableColumn<?, ?> apDescription;
    @FXML public TableColumn<?, ?> apStart;
    @FXML public TableColumn<?, ?> apEnd;
    @FXML public TableColumn<?, ?> apCustomerName;
    @FXML public TableColumn<?, ?> apCustomerId;
    @FXML public TableColumn<?, ?> apContact;
    @FXML public TableColumn<?, ?> apContactId;
    @FXML public TableColumn<?, ?> apUserId;

    /**
     * Handles the double-click event on the main table.
     * @param mouseEvent The MouseEvent triggered by the double-click event.
     * @throws SQLException If an SQL-related error occurs.
     */
    public void handleTableDoubleClick(MouseEvent mouseEvent) throws SQLException {
        //TODO [c] Test Double Click - success
        if (mouseEvent.getClickCount() == 2) {
            selectAppointment();
        }
    }

// SEARCH BAR //
    @FXML
    public TextField searchBar;
    /**
     * Handles the Enter key event in the search bar, triggering a search when Enter is pressed.
     * @param keyEvent  The KeyEvent triggered by the Enter key press.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML
    public void searchBarEnter(KeyEvent keyEvent) throws SQLException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            applicationObject.guiObject.Searcher.updateFromSearch(searchBar, primaryApTable);
        }
    }
    /**
     * Searches the primary Appointment Table by title.
     * @param actionEvent The ActionEvent triggered by the button click.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML
    public void searchAppointments(ActionEvent actionEvent) throws SQLException {
        applicationObject.guiObject.Searcher.updateFromSearch(searchBar, primaryApTable);
    }

    //Radio Buttons//
    LocalDateTime timeWindowStart = null;
    LocalDateTime timeWindowEnd = null;

    @FXML public ToggleGroup apRangeToggle;
    @FXML public RadioButton apWeekRadial;

    /**
     * Sets the time window of the primary table to week.
     * @param actionEvent The ActionEvent triggered by the radio button selection.
     * @throws SQLException If an SQL-related error occurs.
     */
    public void apWeekSelected(ActionEvent actionEvent) throws SQLException {
        //Question [] after trying a bunch of the minus options this seems to be working. Double check with professor.
        timeWindowStart = LocalDateTime.now().minusWeeks(1);
        timeWindowEnd = LocalDateTime.now().plusWeeks(1);

        constrictTimeFrame();
    }
    /**
     * Filters appointments based on the specified time window and updates the main table accordingly.
     * Here I use a lambda to limit the time frame for each ap in allApList filtering those that don't.
     * @throws SQLException If an SQL-related error occurs.
     */
    public void constrictTimeFrame() throws SQLException {
        ObservableList<Appointment> allApList = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> apWithinTime = FXCollections.observableArrayList();

        if (timeWindowStart == null || timeWindowEnd == null){
            primaryApTable.setItems(allApList);
            return;
        }

        //Try Lambda
        allApList.forEach(appointment -> {
            if (appointment.getApEnd().isAfter(timeWindowStart)
                &&  appointment.getApStart().isBefore(timeWindowEnd)) {
                apWithinTime.add(appointment);
            }
            primaryApTable.setItems(apWithinTime);
        });
    }

    @FXML public RadioButton apMonthRadio;

    /**
     * Sets the time window of the primary table to month.
     * @param actionEvent The ActionEvent triggered by the radio button selection.
     * @throws SQLException If an SQL-related error occurs.
     */
    public void apMonthSelected(ActionEvent actionEvent) throws SQLException {
        //Question [] after trying a bunch of the minus options this seems to be working. Double check with professor.
        timeWindowStart = LocalDateTime.now().minusMonths(1);
        timeWindowEnd = LocalDateTime.now().plusMonths(1);

        constrictTimeFrame();
    }

    @FXML
    public RadioButton apAllRadio;

    /**
     * Remove time window for primary table.
     * @param actionEvent The ActionEvent triggered by the radio button selection.
     * @throws SQLException If an SQL-related error occurs.
     */
    public void apAllSelected(ActionEvent actionEvent) throws SQLException {
        //Question [] after trying a bunch of the minus options this seems to be working. Double check with professor.
        timeWindowStart = null;
        timeWindowEnd = null;

        constrictTimeFrame();
    }


//APPOINTMENT EDITOR//
    //TABLE SELECTION CONTROL//

    /**
     * Handles the action when an appointment is selected in the table.
     * Updates the fields in the corresponding tab update, add, or delete with the details of
     * the selected appointment.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML
    public void selectAppointment() throws SQLException {
        if (updateApTab.isSelected()) {

            //TODO [Extra] Create an update ap table method and implement it here like you did for delete table
            Appointment selectedAp = primaryApTable.getSelectionModel().getSelectedItem();
            if (selectedAp == null) {
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
            // TODO [Extra] make a note of this. How fxml and Java make an environment where bugs are easy to make and difficult to detect, standing in direct contradiction to the highly abstracted, object oriented, and design constrained model that we use java for in the first place.
            apCustomerIdUpdate.setText(String.valueOf(selectedAp.getApCustomerId()));
            apContactUserIdUpdate.setText(String.valueOf(selectedAp.getApUserId()));
            //apContactUpdate.setItems(contactNames);
            //add contact name . set value . selectedAp . getContactName
            apContactUpdate.setValue(String.valueOf(selectedAp.getApContactName()));
            // TODO [c] make "contactName" in appointment obj getting it from Contact
            apDescriptionUpdate.setText(String.valueOf(selectedAp.getApUserId()));
            apCustomerUpdate.setValue(String.valueOf(selectedAp.getApCustomerName()));
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
            //apContactAdd.setItems(contactNames);
            //add contact name . set value . selectedAp . getContactName
            apCustomerAdd.setValue(String.valueOf(selectedAp.getApCustomerName()));
            apContactAdd.setValue(String.valueOf(selectedAp.getApContactName()));
            apDescriptionAdd.setText(String.valueOf(selectedAp.getApUserId()));
        }
        else if (delApTab.isSelected()) {
            Appointment selectedAp = primaryApTable.getSelectionModel().getSelectedItem();
            addApToBeDeleted(selectedAp);
        }
    }

    //UPDATE APPOINTMENT TAB//
    @FXML public Tab updateApTab;
    @FXML public Tab addApTab;
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
    public ComboBox<String> apCustomerUpdate;
    // ^ TODO [Extra] create an event to add or Edit selected Contact.

    @FXML public TextField apContactUserIdUpdate;

    @FXML public Button apMakeUpdateButton;

    /**
     * Handles the action when a Contact is selected in the combo box for updating an appointment.
     * Updates the contact ID field based on the selected contact's name.
     * @param actionEvent Selecting a contact in the combo box.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML public void apContactUpdateSelect(ActionEvent actionEvent) throws SQLException {
        //TODO [c] create a method to update contact ID field on selection of combo Box
        CChoulesDevTools.println("Updating ID of contact selection");

        //Name of selection = get selection
        String nameOfContact = apContactUpdate.getValue();
        //create new contact = contactDAO find contact with name of selection
        int matchingContactId = ContactDAO.findContactIdFromName(nameOfContact);
        //set apContactUserIdUpdate = contact.getId
        apContactUserIdUpdate.setText(String.valueOf(matchingContactId));
    }
    /**
     * Handles the action when a  Customer is selected in the combo box for updating an appointment.
     * Updates the customer ID field based on the selected customer's name.
     * @param actionEvent Selecting a customer in the combo box.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML public void apCustomerUpdateSelect(ActionEvent actionEvent) throws SQLException {
        //TODO [] create a method to update customer ID field on selection of combo Box
        CChoulesDevTools.println("Updating ID of customer selection");

        //Name of selection = get selection
        String nameOfCustomer = apCustomerUpdate.getValue();

        int matchingCustomerId = CustomerDAO.findIdFromName(nameOfCustomer);
        apCustomerIdUpdate.setText(String.valueOf(matchingCustomerId));
    }
    //Missing Skill take note that the Above methods would have been easier if i would have implemented Application Object sooner with ID and Name as extended variables. I also need to learn how to call an extended class as its parent class.

    /**
     * Handles the process of taking the fields edited by user then constructing an appointment object
     * and replacing the existing object in the data base.
     * @param actionEvent The action of committing an update of the edited appointment.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML public void commitApUpdate(ActionEvent actionEvent) throws SQLException {

        CChoulesDevTools.println("Updating selected Appointment.");

        //TODO [c] replace selected appointment in the appointment list using ID with updated appointment. then update the DB. Or because there may be 2+ people updating to the database at the same time update the database object then reload the Appointment list. Tested: worked and prevented conflict with another instance of the application.
        //TODO [c] Make a method to parse local date and time from strings to convert to LocalDateTime. This will be application wide so add to application tools. TESTED: works Implementation was more difficult than anticipated involving new String formatting methods.

        LocalDateTime apStartEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apStartDateUpdate.getEditor().getText(), apStartTimeUpdate.getValue());

        try {
            CChoulesDevTools.println(apStartDateUpdate.getValue().toString());
        } catch (Exception ignored){

        }

        LocalDateTime apEndEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apEndDateUpdate.getEditor().getText(), apEndTimeUpdate.getValue());
        //TODO [c] Find a Way to get the Field to parse to LocalDateTime -First error: Text '2020-05-28 12:00' could not be parsed at index 2- FOUND: utilize string Formatting tools in Java properly parse field input to MM-dd-yyyy

        if (apEndEdited == null || apStartEdited == null) {
            //TODO [l] create a popup or indicator to prompt user of bad input
            CChoulesDevTools.println("Date format not accepted, aborting update.");
            return;
        }



        Appointment userEditedAp = new Appointment(
                apTitleUpdate.getText(),
                apTypeUpdate.getText(),
                apLocationUpdate.getText(),
                Integer.parseInt(apIdUpdate.getText()),
                apDescriptionUpdate.getText(),
                apStartEdited,
                apEndEdited,
                Integer.parseInt(apCustomerIdUpdate.getText()),
                Integer.parseInt(apContactUserIdUpdate.getText()),
                Main.currentUser.getUserId()
        );


        if (userEditedAp.isInBusinessHours()
                && userEditedAp.isWithinOneDay()
                && userEditedAp.isStartBeforeEnd()
                && userEditedAp.isNotOverlapping()
                && userEditedAp.getApId() != 0
                && userEditedAp.getApCustomerId() != 0
                && userEditedAp.getApContactId() != 0
                && userEditedAp.isComplete()

        ) {
            try {
                //TODO [c] create a an addAppointment method to AppointmentDAO
                CChoulesDevTools.println("User ID is: " + Main.currentUser.getUserId());
                AppointmentDAO.updateAppointment(userEditedAp, JDBTools.getConnection());
                tableLoadFromDB();

            } catch (SQLException e) {
                CChoulesDevTools.println(e.toString());
            }
            //Finish method and return without popup
            return;
        }

        String popupContent = "The following rules have been broken:";

        if (!userEditedAp.isInBusinessHours()){
            popupContent = popupContent +
                    "\n -Appointment must be within business hours.";
        }

        if (!userEditedAp.isWithinOneDay()){
            popupContent = popupContent +
                    "\n -Appointment cannot go over night.";
        }

        if (!userEditedAp.isStartBeforeEnd()){
            popupContent = popupContent +
                    "\n -Is Starting after it Ends";
        }
        if (!userEditedAp.isNotOverlapping()){
            popupContent = popupContent +
                    "\n -Is overlapping with an existing appointment.";
        }

        if (userEditedAp.getApId() == 0){
            popupContent = popupContent +
                    "\n -No appointment ID : You must select an appointment when Updating an appointment.";
        }

        if (userEditedAp.getApCustomerId() == 0){
            popupContent = popupContent +
                    "\n -No Customer Selected.";
        }

        if (userEditedAp.getApCustomerId() == 0){
            popupContent = popupContent +
                    "\n -No Contact Selected.";
        }
        if (!userEditedAp.isComplete()){
            popupContent = popupContent +
                    "\n -Their must be a value in all appointment fields.";
        }


        SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();

        popup.inappropriateInputPopup(popupContent);


    }

    @FXML
    public TextArea apDescriptionUpdate;

    //ADD APPOINTMENT TAB//
    @FXML public TextField apTitleAdd;
    @FXML public TextField apTypeAdd;
    @FXML public TextField apLocationAdd;
    @FXML public TextField apIdAdd;
    @FXML public DatePicker apStartDateAdd;
    @FXML public ComboBox<String> apStartTimeAdd;
    @FXML public DatePicker apEndDateAdd;
    @FXML public ComboBox<String> apEndTimeAdd;
    @FXML public ComboBox<String>  apCustomerAdd;
    @FXML public TextField apCustomerIdAdd;
    @FXML public ComboBox<String> apContactAdd;
    @FXML public TextField apContactUserIdAdd;
    @FXML public Button apMakeAdd;

    /**
     * Handles the process of taking the fields edited by user then constructing an appointment object
     * and adding the existing object in the data base.
     * @param actionEvent The action of committing an update of the edited appointment.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML public void commitAdd(ActionEvent actionEvent) throws SQLException {
        //Question [] another example of repeating code how do I not extend the functionality of 'commitApUpdate' to this method replacing the object references and adding a few adjustments.
        //TODO [c] Create a commit add method that add ap to db
        //TODO [c] bug on add ap appointment added then bugged. Bug fixed.

        CChoulesDevTools.println("Adding Appointment.");

        LocalDateTime apStartEdited = LocalDateTimeApplicationTool.parseToLocalDateTime(apStartDateAdd.getEditor().getText(), apStartTimeAdd.getValue());
        try {
            CChoulesDevTools.println(apStartDateAdd.getValue().toString());
        } catch (Exception ignored){

        }
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
                Main.currentUser.getUserId()
        );
        String popupContent = "The following rules have been broken:";
        if (userAddedAp.isInBusinessHours()
                && userAddedAp.isWithinOneDay()
                && userAddedAp.isNotOverlapping()
                && userAddedAp.isStartBeforeEnd()
                && userAddedAp.isComplete()
        ) {
            try {
                //TODO [c] create an addAppointment method to AppointmentDAO
                CChoulesDevTools.println("User ID is: " + Main.currentUser.getUserId());
                AppointmentDAO.addAppointment(userAddedAp, JDBTools.getConnection());
                tableLoadFromDB();

            } catch (SQLException e) {
                CChoulesDevTools.println(e.toString());
            }
            return;
        }

        if (!userAddedAp.isInBusinessHours()){
            popupContent = popupContent +
                    "\n -Appointment must be within business hours";
        }

        if (!userAddedAp.isWithinOneDay()){
            popupContent = popupContent +
                    "\n -Appointment cannot go over night";
        }
        if (!userAddedAp.isStartBeforeEnd()){
            popupContent = popupContent +
                    "\n -Is Starting after it Ends";
        }
        if (!userAddedAp.isNotOverlapping()){
            popupContent = popupContent +
                    "\n -Is overlapping with an existing appointment.";
        }
        if (!userAddedAp.isComplete()){
            popupContent = popupContent +
                    "\n -Their must be a value in all appointment feilds.";
        }

        SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();

        popup.inappropriateInputPopup(popupContent);

    }

    /**
     * Handles the action when a Contact is selected in the combo box for updating an appointment.
     * Updates the contact ID field based on the selected contact's name.
     * @param actionEvent Selecting a contact in the combo box.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML public void apContactAddSelect(ActionEvent actionEvent) throws SQLException {
        //TODO [c] create a method to update contact ID field on selection of combo Box
        CChoulesDevTools.println("Updating ID of contact selection");

        //Name of selection = get selection
        String nameOfContact = apContactAdd.getValue();
        //create new contact = contactDAO find contact with name of selection
        int matchingContactId = ContactDAO.findContactIdFromName(nameOfContact);
        //set apContactUserIdUpdate = contact.getId
        apContactUserIdAdd.setText(String.valueOf(matchingContactId));
    }

    /**
     * Handles the action when a Customer is selected in the combo box for updating an appointment.
     * Updates the contact ID field based on the selected Customer's name.
     * @param actionEvent Selecting a Customer in the combo box.
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML public void apCustomerAddSelect(ActionEvent actionEvent) throws SQLException {

        CChoulesDevTools.println("Updating ID of customer selection");

        //Name of selection = get selection
        String nameOfCustomer = apCustomerAdd.getValue();

        int matchingCustomerId = CustomerDAO.findIdFromName(nameOfCustomer);
        apCustomerIdAdd.setText(String.valueOf(matchingCustomerId));

    }

    @FXML public TextArea apDescriptionAdd;


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
    @FXML public Button removeAllApToBeDeleted;
    //TODO [c] add to delete method -> on double click while delete tab to add to delete table.

    /**
     * Handles the process of taking the list of Appointments to be deleted
     * and deleting the matching appointments in the data base by ID.
     * @param actionEvent The action of committing
     * @throws SQLException If an SQL-related error occurs.
     */
    @FXML public void deleteAppointments(ActionEvent actionEvent) throws SQLException {

        SchedulingApplicationPrompt prompt = new SchedulingApplicationPrompt();

        String promptContent = "Are you sure you would like to delete the following Appointments:" +
                "\nTitle : " +
                "ID : " +
                "Type ";

        for (Appointment appointment:toBeDeletedApList){
            promptContent = promptContent +
                    "\n" +
                    appointment.getApTitle() +
                    " : " +
                    appointment.getApId() +
                    " : " +
                    appointment.getApType();
        }

        if (prompt.deleteConformationPrompt(promptContent))
        for (Appointment appointment:toBeDeletedApList){
            AppointmentDAO.deleteAppointment(appointment.getApId(), JDBTools.getConnection());

        }
        toBeDeletedApList.removeAll(toBeDeletedApList);
        delApTable.setItems(toBeDeletedApList);
        tableLoadFromDB();
    }

    /**
     * Adds or removes an appointment to/from the list of appointments to be deleted.
     * Updates the delete table accordingly.
     *
     * @param apToBeDeleted The appointment to be added or removed from the deletion list.
     */
    @FXML public void addApToBeDeleted(Appointment apToBeDeleted){
        if (toBeDeletedApList.contains(apToBeDeleted)){
            toBeDeletedApList.remove(apToBeDeleted);
        } else {
            toBeDeletedApList.add(apToBeDeleted);
        }
        updateDeleteTable();
    }

    /**
     * Updates the delete table with the appointments to be deleted.
     */
    public void updateDeleteTable(){

        delApId.setCellValueFactory(new PropertyValueFactory<>("apId"));
        delApTitle.setCellValueFactory(new PropertyValueFactory<>("apTitle"));
        delApDescription.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
        delApLocation.setCellValueFactory(new PropertyValueFactory<>("apLocation"));
        delApType.setCellValueFactory(new PropertyValueFactory<>("apType"));
        delApStart.setCellValueFactory(new PropertyValueFactory<>("apPrettyStart"));
        delApEnd.setCellValueFactory(new PropertyValueFactory<>("apPrettyEnd"));
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
            CChoulesDevTools.println("ViewCustomers ID: " + appointment.getApCustomerId());
            CChoulesDevTools.println("Contact ID: " + appointment.getApContactId());
            CChoulesDevTools.println("User ID: " + appointment.getApUserId() +"\n");
        }
    }

    /**
     * Removes all appointments from the list of appointments to be deleted.
     * Updates the delete table accordingly.
     * @param actionEvent The event triggered by removing all appointments.
     */
    public void removeAllFromApToBeDeleted(ActionEvent actionEvent) {
        toBeDeletedApList.removeAll(toBeDeletedApList);
        delApTable.setItems(toBeDeletedApList);
    }

    /**
     * Adds the selected appointment from the primary table to the list of appointments to be deleted.
     * @param actionEvent The event triggered by adding the selected appointment.
     */
    public void addSelectionToDeletion(ActionEvent actionEvent) {
            addApToBeDeleted(primaryApTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Adds the selected appointment from the delete table to the list of appointments to be deleted.
     * @param actionEvent The event triggered by removing the selected appointment.
     */
    public void removeSelectionToDeletion(ActionEvent actionEvent) {
            addApToBeDeleted(delApTable.getSelectionModel().getSelectedItem());
    }

    @FXML public Button deleteAppointment;

    //NON-SCENE ELEMENTS//
    //SCENE INITIALIZATION//
    /**
     * Initializes the ViewAppointments.
     * Loads data into tables and updates combo boxes with contact and customer names.
     * @throws SQLException If a database access error occurs.
     */
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


        //TODO [] morph these into methods.
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

        ObservableList<applicationObject.Customer> customerList = CustomerDAO.getAllCustomers();

        CChoulesDevTools.println("Updating Contact names");
        List<ComboBox<String>> customerComboBoxNameList = Arrays.asList(
                apCustomerUpdate,
                apCustomerAdd);
        for (ComboBox<String> comboBox : customerComboBoxNameList) {
            ObservableList<String> contactNames = customerList.stream()
                    .map(Customer::getCustomerName)
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            FXCollections::observableArrayList)
                    );
            Searcher.nameListener(comboBox, contactNames);
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
    /**
     * Loads data from the database into the appointment table.
     * Displays appointment details in the console.
     * @throws SQLException If a database access error occurs.
     */
    public void tableLoadFromDB() throws SQLException {
    //APPOINTMENT LIST//
    ObservableList<applicationObject.Appointment> appointmentList = AppointmentDAO.getAllAppointments();


    //TODO [c] After you complete the applicationObject and DAO for Client Name include access to it hereIn addition change the contactName to ViewCustomers Name (ViewCustomers Name is important for meetings)
        apId.setCellValueFactory(new PropertyValueFactory<>("apId"));
        apTitle.setCellValueFactory(new PropertyValueFactory<>("apTitle"));
        apDescription.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
        apLocation.setCellValueFactory(new PropertyValueFactory<>("apLocation"));
        apType.setCellValueFactory(new PropertyValueFactory<>("apType"));
        apStart.setCellValueFactory(new PropertyValueFactory<>("apPrettyStart"));
        apEnd.setCellValueFactory(new PropertyValueFactory<>("apPrettyEnd"));

    //Naming conventions are off on Appointment
    //TODO [c] Bug Non-Appointment ID are not working after updating naming convention. --Found: Seriously? apCustomerId string is converted to getApCustomerId, then calls the method by the same name. The actual Appointment.getCustomerId has no "Ap" fixing now.--Tested: Finding Correct -> Bug removed.
    //TODO [c] same bug happened again refactoring apCustomerId to ...Id did not refactor here resulting in null value. This is from propertyValueFactory code injection from string alteration.


    //TODO [Extra] When the appointment is add or updated check to see if the Id of contact matches what is in the data base to make sure the database has not been edited since the start of the edit. This is for use with multiple users and is not in scope of project.
        apCustomerId.setCellValueFactory(new PropertyValueFactory<>("apCustomerId"));
    //ToDo [c] include contact name
        apContact.setCellValueFactory(new PropertyValueFactory<>("apContactName"));
    //TODO [c] Solved: how does the PropertyValueFactory decide where to access the list name? Perhaps it is better to access the contactName through the appointment applicationObj instead. -Found-> its set by primaryApTable.setItems Decided to update appointment class to include a getApContactName

        apCustomerName.setCellValueFactory(new PropertyValueFactory<>("apCustomerName"));
        apContactId.setCellValueFactory(new PropertyValueFactory<>("apContactId"));
        apUserId.setCellValueFactory(new PropertyValueFactory<>("apUserId"));
    // TODO [Extra] Make a comment on how frustrating PropertyValueFactory values are.
    //  -we should not be using strings here, why javaFX? -And code injection with a string alteration? Are you kidding? I can't inject folders of code as local code to keep my code organized(at least no to my beginner level of knowledge) like i do with "include" on other languages but JavaFX is making me work with this very insane way of calling methods that is not recognized by the IDE?


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
            CChoulesDevTools.println("ViewCustomers ID: " + appointment.getApCustomerId());
            CChoulesDevTools.println("Contact ID: " + appointment.getApContactId());
            CChoulesDevTools.println("User ID: " + appointment.getApUserId() +"\n");
        }
    }

}
