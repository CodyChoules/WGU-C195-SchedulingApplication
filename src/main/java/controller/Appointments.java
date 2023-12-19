package controller;

import applicationObject.Appointment;
import applicationTools.CChoulesDevTools;
import dataAccessObject.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Appointments {

    // TOP BAR //
    @FXML public Button backToHomeFromAp;



    @FXML public void backToHomeAction(ActionEvent actionEvent) {
    }

    // TABLE //
     public TableView<applicationObject.Appointment> primaryTableAppointments;
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
    @FXML public ComboBox<String> apStartTimeUpdate;
    @FXML public DatePicker apEndDateUpdate;
    @FXML public ComboBox<String> apEndTimeUpdate;
    @FXML public TextField apCustomerIdUpdate;
    @FXML public ComboBox<String> apContactUpdate;
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


    //SCENE INITIALIZATION//
//    public void initialize() throws SQLException {
//
//Appointments
//    }
    public void initialize() throws SQLException{

        ObservableList<applicationObject.Appointment> appointmentList = AppointmentDAO.getAllAppointments();

        //TODO [] After you complete the applicationObject & DAO for Client Name include access to it here
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
        apCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        //ToDo include Contact ID to Name
        apContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        primaryTableAppointments.setItems(appointmentList);
    }


    //FXML LOADER METHOD//
    public static void loadThisFXML(Stage stage) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        CChoulesDevTools.println("Loading HomeMenu.fxml");

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
