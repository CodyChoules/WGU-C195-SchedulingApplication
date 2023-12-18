package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Appointments {

    // TOP BAR //
    @FXML public Button backToHomeFromAp;



    @FXML public void backToHomeAction(ActionEvent actionEvent) {
    }

    // TABLE //
     public TableView primaryTableAppointments;
    @FXML public TableColumn apTitle;
    @FXML public TableColumn apType;
    @FXML public TableColumn apLocation;
    @FXML public TableColumn apID;
    @FXML public TableColumn apDescription;
    @FXML public TableColumn apStart;
    @FXML public TableColumn apEnd;
    @FXML public TableColumn apCustomerID;
    @FXML public TableColumn apContact;
    @FXML public TableColumn apContactID;
    @FXML public TableColumn apUserID;

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
    //UPDATE APPOINTMENT TAB//
    @FXML public TextField apTitleUpdate;
    @FXML public TextField apTypeUpdate;
    @FXML public TextField apLocationUpdate;
    @FXML public TextField apIdUpdate;
    @FXML public DatePicker apStartDateUpdate;
    @FXML public ComboBox apStartTimeUpdate;
    @FXML public DatePicker apEndDateUpdate;
    @FXML public ComboBox apEndTimeUpdate;
    @FXML public TextField apCustomerIdUpdate;
    @FXML public ComboBox apContactUpdate;
    @FXML public TextField apContactUserIdUpdate;
    @FXML public Button apMakeUpdateButton;
    @FXML public void MakeApUpdate(ActionEvent actionEvent) {
    }
    @FXML public TextArea apDescriptionUpdate;

    //ADD APPOINTMENT TAB//
    @FXML public TextField apTitleAdd;
    @FXML public TextField apTypeAdd;
    @FXML public TextField apLocationAdd;
    @FXML public TextField apIdAdd;
    @FXML public DatePicker apStartDateAdd;
    @FXML public ComboBox apStartTimeAdd;
    @FXML public DatePicker apEndDateAdd;
    @FXML public ComboBox apEndTimeAdd;
    @FXML public TextField apCustomerIdAdd;
    @FXML public ComboBox apContactAdd;
    @FXML public TextField apContactUserIdAdd;
    @FXML public Button apMakeAdd;
    @FXML public void commitAdd(ActionEvent actionEvent) {
    }
    @FXML public TextArea apDescriptionAdd;


    public void addSelectionToDeletion(ActionEvent actionEvent) {
    }

    //DELETE APPOINTMENT TAB//
    @FXML public TableView mainAppointmentsTable1;
    @FXML public TableColumn appointmentTitle2;
    @FXML public TableColumn appointmentType2;
    @FXML public TableColumn appointmentLocation2;
    @FXML public TableColumn appointmentID2;
    @FXML public TableColumn appointmentDescription2;
    @FXML public TableColumn appointmentStart2;
    @FXML public TableColumn appointmentEnd2;
    @FXML public TableColumn appointmentCustomerID2;
    @FXML public TableColumn tableContactID21;
    @FXML public TableColumn tableContactID2;
    @FXML public TableColumn tableUserID2;
    @FXML public Button addApToDelete;
    public void deleteAppointment(ActionEvent actionEvent) {
    }
    @FXML public Button deleteAppointment;
    
    
}
