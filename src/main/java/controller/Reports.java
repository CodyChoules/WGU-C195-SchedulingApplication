package controller;

import applicationObject.Appointment;
import applicationObject.Country;
import applicationObject.Customer;
import applicationTools.CChoulesDevTools;
import dataAccessObject.AppointmentDAO;
import dataAccessObject.CountryDAO;
import dataAccessObject.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Reports {

    private static Stage mainStage; //This is for page refresh. May not work with tabs


    //CUSTOMERS TAB\\
    public TableView<totalsReport> customersByCountryTable;
    public TableColumn<?, ?> customerByCountryName;
    public TableColumn<?, ?> customerByCountryNumber;
    public TableView<totalsReport> customersByContact;
    public TableColumn<?, ?> customersByContactName;
    public TableColumn<?, ?> customersByContactNumber;
    public TableView<totalsReport> customersGainedByMonth;
    public TableColumn<?, ?> customersGainedByMonthName;
    public TableColumn<?, ?> customersGainedByMonthNumber;
    //APPOINTMENT TOTALS TAB\\
    public Tab apTotalsTab;
    public TableView<totalsReport> AppointmentsByCountry;
    public TableColumn<?, ?> countryName;
    public TableColumn<?, ?> countryCounter;
    public TableView<totalsReport> apTotalsApType;
    public TableColumn<?, ?> apTotalsApTypeCol;
    public TableColumn<?, ?> apTotalsTypeTotalCol;
    public TableColumn<?, ?> apTotalsByMonth;
    public TableColumn<?, ?> apTotalsMonthTotal;

    public Reports() throws SQLException {
    }


    //create a class to make object totals report

    public static class totalsReport {
        public String byWhat;
        public int howMany;

        totalsReport(String byWhat, int howMany) {
            this.byWhat = byWhat;
            this.howMany = howMany;
        }

        //Setter & Getter for byWhat
        public String getByWhat() { return byWhat; }
        public void setByWhat(String byWhat) { this.byWhat = byWhat; }

        //Getter & Setter for howMany
        public int getHowMany() { return howMany; }
        public void setHowMany(int howMany) { this.howMany = howMany; }

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
        loadTotalsReportTable(customersByCountryList(), customersByCountryTable, customerByCountryName, customerByCountryNumber);
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

    public void loadTotalsReportTable(ObservableList<totalsReport> totalsList, TableView<totalsReport> theTable, TableColumn<?, ?> byWhat,TableColumn<?, ?> howMany) {

        //setting the values of the tables
        byWhat.setCellValueFactory(new PropertyValueFactory<>("byWhat"));
        howMany.setCellValueFactory(new PropertyValueFactory<>("howMany"));

        theTable.setItems(totalsList); //Set the data to the table
    }

    public ObservableList<totalsReport> customersByCountryList() throws SQLException {
        //use this to load the customersByCountryTable

        //List of totals to be returned after totals are added
        ObservableList<totalsReport> reportList = FXCollections.observableArrayList();

        //List of Customers to count from
        ObservableList<Customer> allCustomers = CustomerDAO.getAllCustomers();

        //List of Countries to count for
        ObservableList<Country> allCountries = CountryDAO.getAllCountries();

        //Creating a report for each country and counting the corresponding customers by comparing countryId
        for (Country country : allCountries) {
            int numberOfCustomers = 0;

            //Loop through all customers and count the ones with matching countryId
            for (Customer customer : allCustomers) {
                //TODO [Extra] this is using name when ID would be faster
                if (customer.getCustomerCountryName().equals(country.getCountryName())) {
                    numberOfCustomers++;
                }
            }

            // Create a totalsReport object and add it to the reportList
            totalsReport report = new totalsReport(country.getCountryName(), numberOfCustomers);
            reportList.add(report);
        }


        return reportList;
    }

    //public void loadAp










//    public interface Reportable {
//        String getName();
//        int getCount();
//    }
//
//    public class TotalsReport implements Reportable {
//        private String name;
//        private int count;
//
//        public TotalsReport(String name, int count) {
//            this.name = name;
//            this.count = count;
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public int getCount() {
//            return count;
//        }
//    }
//
//    public class ReportGenerator<T extends Reportable> {
//
//        public ObservableList<T> generateReport(ObservableList<T> allItems) {
//            ObservableList<T> reportList = FXCollections.observableArrayList();
//
//            for (T currentItem : allItems) {
//                // Perform your counting logic here based on the properties of the items
//                // For example: currentItem.getName(), currentItem.getCount()
//            }
//
//            return reportList;
//        }
//    }
//
//    ObservableList<Customer> allCustomers = CustomerDAO.getAllCustomers();
//    ObservableList<Country> allCountries = CountryDAO.getAllCountries();
//
//    ReportGenerator<TotalsReport> customerReportGenerator = new ReportGenerator<>();
//    ObservableList<TotalsReport> customerReport = customerReportGenerator.generateReport(allCustomers);
//
//    ReportGenerator<TotalsReport> countryReportGenerator = new ReportGenerator<>();
//    ObservableList<TotalsReport> countryReport = countryReportGenerator.generateReport(allCountries);



}
