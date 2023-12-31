package controller;

import applicationObject.Appointment;
import applicationTools.CChoulesDevTools;
import dataAccessObject.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Reports {

    private static Stage mainStage; //This is for page refresh. May not work with tabs

    //create a class to make object totals report

    public static class totalsReport {
        public String byWhat;
        public int howMany;

        totalsReport(String byWhat, int howMany) {
            this.byWhat = byWhat;
            this.howMany = howMany;
        }
    }

    /*TODO [] Create
        Init
        combos
        update on combo select
     */

    public Button backToHomeFromRp;
    public TableView<applicationObject.Appointment> allApTable;
    public TableColumn<?, ?> apTitleRp;
    public TableColumn<?, ?> apTypeRp;
    public TableColumn<?, ?> apIdRp;
    public TableColumn<?, ?> apLocationRp;
    public TableColumn<?, ?> apDescriptionRp;
    public TableColumn<?, ?> apStartRp;
    public TableColumn<?, ?> apEndRp;
    public TableColumn<?, ?> apCustomerIdRp;
    public TableColumn<?, ?> apContactRp;
    public TableColumn<?, ?> apContactIdRp;
    public ComboBox<String> contactScheduleContactBox;
    public ComboBox<String> apHashTagSelection;
    public Tab rpCustomerByCountry;
    public TableView<applicationObject.Customer> customerByCountry;
    public TableColumn<?, ?> countryName;
    public TableColumn<?, ?> countryCounter;
    public Tab apTotalsTab;
    public TableView<totalsReport> apTotalsApType;
    public TableColumn<?, ?> apTotalsApTypeCol;
    public TableColumn<?, ?> apTotalsTypeTotalCol;
    public TableColumn<?, ?> apTotalsByMonth;
    public TableColumn<?, ?> apTotalsMonthTotal;

    public void backToHomeAction(ActionEvent actionEvent) {
    }

    public void apDataByContact(ActionEvent actionEvent) {
    }

    public void addTagToSearch(ActionEvent actionEvent) {
    }

    public void customerByCountry(Event event) {
    }

    public void apTotalsTab(Event event) {
    }



    //BACK END METHODS\\

    //FXML LOADER METHOD//
    public static void loadThisFXML(Stage stage) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        CChoulesDevTools.println("Loading Reports.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(controller.Reports.class.getResource("/views/Reports.fxml"));


        Parent root = loader.load();

        controller.Reports mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        mainStage = stage;
    }



    public void initialize() throws SQLException {
        //Call populate Tables
        loadApByContactTable();

        //Call populate Combos


    }

    public void loadApByContactTable() throws SQLException {
        ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointments();

        apIdRp.setCellValueFactory(new PropertyValueFactory<>("apId"));
        apTitleRp.setCellValueFactory(new PropertyValueFactory<>("apTitle"));
        apTypeRp.setCellValueFactory(new PropertyValueFactory<>("apType"));
        apLocationRp.setCellValueFactory(new PropertyValueFactory<>("apLocation"));
        apDescriptionRp.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
        apStartRp.setCellValueFactory(new PropertyValueFactory<>("apStart")); //TODO [l] Create a getApStartClean method to clean start and end values to table format.
        apEndRp.setCellValueFactory(new PropertyValueFactory<>("apEnd"));
        apCustomerIdRp.setCellValueFactory(new PropertyValueFactory<>("apCustomerId"));
        apContactRp.setCellValueFactory(new PropertyValueFactory<>("apContactName"));
        apContactIdRp.setCellValueFactory(new PropertyValueFactory<>("apContactId"));

        allApTable.setItems(appointmentList); //Set the data to the table
    }

    //public void loadAp



}
