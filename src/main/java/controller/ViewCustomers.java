package controller;

import applicationObject.Country;
import applicationObject.Customer;
import applicationObject.guiObject.SchedulingApplicationPrompt;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import controller.trash.subViewController.Home;
import dataAccessObject.CountryDAO;
import dataAccessObject.CustomerDAO;
import dataAccessObject.FirstLvlDivisionDAO;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Controller class for managing customer-related functionality.
 * This class provides methods and controls for interacting with customer data in the application.
 */
public class ViewCustomers {

    /**
     * The main stage for the application. Used for page refresh.
     */
    public static Stage mainStage; //This is for page refresh. May not work with tabs
    //CUSTOMER PRIMARY TABLE\\;
    @FXML public TableView<applicationObject.Customer> CRTable;
    @FXML public TableColumn<?, ?> CRTableId;
    @FXML public TableColumn<?, ?> CRTableName;
    @FXML public TableColumn<?, ?> CRTableAddress;
    @FXML public TableColumn<?, ?> CRTablePostalCode;
    @FXML public TableColumn<?, ?> CRTablePhone;
    @FXML public TableColumn<?, ?> CRTableCountry;
    @FXML public TableColumn<?, ?> CRTableState;
    @FXML public Button CRSelectCustomerButton;
    @FXML public Button CRCancel;
    @FXML public Button CRAddCustomer;
    //CUSTOMER EDITOR\\
    @FXML public TextField CRNameField;
    @FXML public TextField CRIdField;
    @FXML public TextField CRPhoneNumberField;
    @FXML public TextField CRAddressField;
    @FXML public ComboBox<String> CRCountryDropDown; //Todo [] create a functioning dropdown
    @FXML public ComboBox<String> CRFirstLvlDivDropDown; //Todo [] same ^
    @FXML public TextField CRPostalCodeField;
    @FXML public Button CRSaveEditedCustomer;
    //TABS FOR EDITOR\\
    @FXML public Tab editCustomerTab;
    @FXML public Tab addCustomerTab;
    @FXML public Tab deleteCustomerTab;

    //CUSTOMER ADDER\\
    @FXML public TextField CRNameField1;
    @FXML public TextField CRIdField1;
    @FXML public TextField CRPhoneNumberField1;
    @FXML public TextField CRAddressField1;
    @FXML public ComboBox<String> CRCountryDropDown1;
    @FXML public ComboBox<String> CRFirstLvlDivDropDown1;
    @FXML public TextField CRPostalCodeField1;

    //CUSTOMER DELETER\\
    @FXML public TableView<applicationObject.Customer> CRDeleteTable;
    @FXML public TableColumn<?, ?> CRTableId1;
    @FXML public TableColumn<?, ?> CRTableName1;
    @FXML public TableColumn<?, ?> CRTableAddress1;
    @FXML public TableColumn<?, ?> CRTablePostalCode1;
    @FXML public TableColumn<?, ?> CRTablePhone1;
    @FXML public TableColumn<?, ?> CRTableCountry1;
    @FXML public TableColumn<?, ?> CRTableState1;
    public ObservableList<applicationObject.Customer> customersToDelete = FXCollections.observableArrayList();


    //TABLE METHODS\\
    /**
     * Handles the double-click event on the customer table.
     * Depending on the selected tab.
     * @param mouseEvent double-click event.
     * @throws SQLException If an SQL exception occurs during database operations.
     */
    public void handleTableDoubleClick(MouseEvent mouseEvent) throws SQLException {
        //TODO [c] Test Double Click - success
        if (mouseEvent.getClickCount() == 2) {
            selectCustomer();
        }
    }

    /**
     * Performs actions based on the selected tab when a customer is selected in the table.
     * Updates fields or adds customers to be deleted based on the selected tab.
     */
    private void selectCustomer() {
        //If tab update is open
        if (editCustomerTab.isSelected()) {

            //TODO [Extra] Create an update ap table method and implement it here like you did for delete table
            applicationObject.Customer selectedObj =CRTable.getSelectionModel().getSelectedItem();
            if (selectedObj == null) {

                ////get the DAO info for the corresponding object values (We have not set up otherDAO
                return;
            }

            //TODO [] Add first level db access to the customer obj


            //TODO [c] fill update fields on selection of appointment
            CRIdField.setText(String.valueOf(selectedObj.getCustomerId()));
            CRNameField.setText(selectedObj.getCustomerName());
            CRAddressField.setText(selectedObj.getCustomerAddress());
            CRPostalCodeField.setText(selectedObj.getCustomerPostalCode());
            CRPhoneNumberField.setText(selectedObj.getCustomerPhoneNumber());
            CRCountryDropDown.setValue(selectedObj.getCustomerCountryName());
            CRFirstLvlDivDropDown.setValue(selectedObj.getCustomerDivisionName());
        }

        if (addCustomerTab.isSelected()) {

            applicationObject.Customer selectedObj = CRTable.getSelectionModel().getSelectedItem();
            if (selectedObj == null) {
                return;
            }

            //These are for the adder copied from editor
            CRIdField1.setText("Autogenerated on Add");
            CRNameField1.setText(selectedObj.getCustomerName());
            CRAddressField1.setText(selectedObj.getCustomerAddress());
            CRPostalCodeField1.setText(selectedObj.getCustomerPostalCode());
            CRPhoneNumberField1.setText(selectedObj.getCustomerPhoneNumber());
            CRCountryDropDown1.setValue(selectedObj.getCustomerCountryName());
            CRFirstLvlDivDropDown1.setValue(selectedObj.getCustomerDivisionName());
        }

        if (deleteCustomerTab.isSelected()){
            //adds customer to be deleted from the main table to the delete table
            customerDeleterAddRemoveUsing(CRTable);
        }
    }

    /**
     * Adds or removes a customer from the deletion list based on the selection in the table.
     * @param tableView The TableView containing customer data.
     */
    private void customerDeleterAddRemoveUsing(TableView<applicationObject.Customer> tableView) {
        applicationObject.Customer selectedObj = tableView.getSelectionModel().getSelectedItem();
        if (selectedObj == null) {
            return;
        }
        if (customersToDelete.contains(selectedObj)){
            customersToDelete.remove(selectedObj);
            return;
        }
        customersToDelete.add(selectedObj);
        CRDeleteTable.setItems(customersToDelete);
    }

    /**
     * Clears the list of customers to be deleted and updates the deletion table.
     * @param actionEvent The ActionEvent associated with the clear operation.
     */
    @FXML public void CRClearDeleteTable(ActionEvent actionEvent) {
        customersToDelete.clear();
        CRDeleteTable.setItems(customersToDelete);
    }

    /**
     * Handles the save action for updating an existing customer.
     * @param actionEvent The event triggered by the save action.
     * @throws SQLException If a SQL exception occurs during the database operation.
     */
    @FXML public void CRSaveCustomer(ActionEvent actionEvent) throws SQLException {
        if (CRNameField.getText().isBlank() ||
                CRPhoneNumberField.getText().isBlank() ||
                CRAddressField.getText().isBlank() ||
                CRCountryDropDown.getValue().isBlank() ||
                CRFirstLvlDivDropDown.getValue().isBlank() ||
                CRPostalCodeField.getText().isBlank())
        {
            String popupContent = " -Their must be a value in all Customer fields.";
            SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();
            popup.inappropriateInputPopup(popupContent);
            return;
        }

        int updatingDivisionId = 1;
        LocalDateTime nullCreateDateToBeCreatedByDB = LocalDateTime.now();

        updatingDivisionId = FirstLvlDivisionDAO.getFLD_IdByName(CRFirstLvlDivDropDown.getValue());

        Customer updatingCustomer = new Customer(
                Integer.parseInt(CRIdField.getText()),
                CRNameField.getText(),
                CRAddressField.getText(),
                CRPostalCodeField.getText(),
                CRPhoneNumberField.getText(),
                updatingDivisionId,
                CRFirstLvlDivDropDown.getValue(),
                CRCountryDropDown.getValue(),
                nullCreateDateToBeCreatedByDB
        );

        CustomerDAO.updateCustomer(updatingCustomer, JDBTools.getConnection());
        tableLoadFromDB();

    }

    /**
     * Handles the add action for creating a new customer.
     * @param actionEvent The event triggered by the add action.
     * @throws SQLException If a SQL exception occurs during the database operation.
     */
    @FXML public void CRAddCustomer(ActionEvent actionEvent) throws SQLException {
        //Note 1 indicated that this object is in the add tab
        if (CRNameField1.getText().isBlank() ||
                CRPhoneNumberField1.getText().isBlank() ||
                CRAddressField1.getText().isBlank() ||
                CRCountryDropDown1.getValue().isBlank() ||
                CRFirstLvlDivDropDown1.getValue().isBlank() ||
                CRPostalCodeField1.getText().isBlank()) {

                String popupContent = " -Their must be a value in all Customer fields.";
                SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();
                popup.inappropriateInputPopup(popupContent);

            return;
        }

        int addDivisionId = FirstLvlDivisionDAO.getFLD_IdByName(CRFirstLvlDivDropDown1.getValue());
        ;

        Customer addingCustomer = new Customer(
                0,
                CRNameField1.getText(),
                CRAddressField1.getText(),
                CRPostalCodeField1.getText(),
                CRPhoneNumberField1.getText(),
                addDivisionId,
                CRFirstLvlDivDropDown1.getValue(),
                CRCountryDropDown1.getValue(),
                LocalDateTime.now()
        );



        CChoulesDevTools.println("adding new customer: \n" +
                addingCustomer.getCustomerName() + "\n" +
                addingCustomer.getCreateDate() + "\n" +
                addingCustomer.getCustomerId() + "\n" +
                Timestamp.valueOf(addingCustomer.getCreateDate())
        );



        CustomerDAO.addCustomer(addingCustomer, JDBTools.getConnection());
        tableLoadFromDB();

    }

    /**
     * Deletes the customers listed in the delete table with a confirmation prompt.
     * @throws SQLException If a SQL exception occurs during the database operation.
     */
    public void deleteCustomersWithAlert() throws SQLException {

        SchedulingApplicationPrompt prompt = new SchedulingApplicationPrompt();

        //Appointment_ID and type

        String promptContent = "Are you sure you would like to delete the following customers:" +
                "\nName : " +
                "ID : " +
                "Country ";

        for (Customer appointment:customersToDelete){
            promptContent = promptContent +
                    "\n" +
                    appointment.getCustomerName() +
                    " : " +
                    appointment.getCustomerId() +
                    " : " +
                    appointment.getCustomerCountryName();
        }

        if (prompt.deleteConformationPrompt(promptContent)){
                for (Customer customer : customersToDelete){
                    CustomerDAO.deleteCustomer(customer.getCustomerId(), JDBTools.getConnection());

                }
            }

        customersToDelete.clear();
        CRDeleteTable.setItems(customersToDelete);
        tableLoadFromDB();
    }

    /**
     * Handles the action to delete the listed customers with a confirmation prompt.
     * @param actionEvent The event triggered by the delete action.
     * @throws SQLException If a SQL exception occurs during the database operation.
     */
    public void CRDeleteListedCustomers(ActionEvent actionEvent) throws SQLException {
        deleteCustomersWithAlert();
    }

    /**
     * Handles the action when the Select Customer button is clicked.
     * @param actionEvent The event triggered by the button click.
     */
    @FXML public void CRSelectCustomerButton(ActionEvent actionEvent) {
    }

    /**
     * Handles the action when the Cancel button is clicked.
     * @param actionEvent The event triggered by the button click.
     */
    @FXML public void CRCancel(ActionEvent actionEvent) {
    }

    /**
     * Handles the action when the Delete button is clicked.
     * @param actionEvent The event triggered by the button click.
     */
    @FXML public void CRDelete(ActionEvent actionEvent) {
    }

    /**
     * Handles the action when the  Country DropDown is entered.
     * @param actionEvent The event triggered by the dropdown enter action.
     * @throws SQLException If a SQL exception occurs.
     */
    @FXML public void CRCountryDropDownEnter(ActionEvent actionEvent) throws SQLException {

        CRFirstLvlDivDropDown.setItems(setFLD_ByCountry());
//        Searcher.nameListener(CRFirstLvlDivDropDown, setFLD_ByCountry());
    }

    //FXML LOADER METHOD//
    public static void loadThisFXML(Stage stage) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        CChoulesDevTools.println("Loading customers.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(ViewCustomers.class.getResource("/views/viewCustomers.fxml"));


        Parent root = loader.load();

        ViewCustomers mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);



        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        mainStage = stage;
    }

    /**
     * Initializes the customer controller.
     * @throws SQLException If a SQL exception occurs during initialization.
     */
    public void initialize() throws SQLException{

        tableLoadFromDB();
        initialComboBoxLoadFromDB();
        deleteTableInitialize();

    }

    /**
     * Sets the available First-Level Divisions based on the selected country.
     * @return An observable list of FLD names available for the selected country.
     * @throws SQLException If a database access error occurs.
     */
    public ObservableList<String> setFLD_ByCountry() throws SQLException {

        //Get Country by name returns NO COUNTRY FOUND ON ID 0
        Country country = CountryDAO.getCountryByName(CRCountryDropDown.getValue());
        CChoulesDevTools.println(CRCountryDropDown.getValue());
        ObservableList<String> fdlAvailable = FXCollections.observableArrayList();



        if (country.getCountryId() == 0) {
            CChoulesDevTools.println("No country found");
            CChoulesDevTools.println("complete fdlAvailable");
            FirstLvlDivisionDAO.getFLD_Names();
        } else {


            CChoulesDevTools.println(country.getCountryName());
            CChoulesDevTools.println("limiting fdlAvailable");
            fdlAvailable = country.getChildFDLNames();
        }

        CChoulesDevTools.println("fdlAvailable" + fdlAvailable.toString());
        return fdlAvailable;
    }

    /**
     * Initializes the combo boxes in the GUI by loading data from the database.
     * Also sets up a listener to dynamically update FLD options based on the selected country.
     * @throws SQLException If a SQL exception occurs.
     */
    public void initialComboBoxLoadFromDB() throws SQLException {

        CRCountryDropDown.setItems(CountryDAO.getCountryNames());
        CRFirstLvlDivDropDown.setItems(FirstLvlDivisionDAO.getFLD_Names());
        //TODO [Extra] implement searcher while still limiting FLD_Names to Countries DAO 

//        Searcher.nameListener(CRFirstLvlDivDropDown, FirstLvlDivisionDAO.getFLD_Names());

//        CRCountryDropDown.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
//
//            Country country;
//            try {
//
//
//                //Get Country by name returns NO COUNTRY FOUND ON ID 0
//                country = CountryDAO.getCountryByName(CRCountryDropDown.getValue());
//                CChoulesDevTools.println(CRCountryDropDown.getValue());
//
//                ObservableList<String> fdlAvailable = FXCollections.observableArrayList();
//                if (country.getCountryId() == 0) {
//                    CChoulesDevTools.println("No country found");
//                    CChoulesDevTools.println("complete fdlAvailable");
//                    FirstLvlDivisionDAO.getFLD_Names();
//                } else {
//
//
//                    CChoulesDevTools.println(country.getCountryName());
//                    CChoulesDevTools.println("limiting fdlAvailable");
//                    fdlAvailable = country.getChildFDLNames();
//                }
//                Searcher.nameListener(CRFirstLvlDivDropDown, fdlAvailable);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });


    }




    /**
     * Loads customer data from the database and populates the customer table in the GUI.
     * @throws SQLException If a SQL exception occurs.
     */
    public void tableLoadFromDB() throws SQLException {
        ObservableList<applicationObject.Customer> customerList = CustomerDAO.getAllCustomers();

        CRTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CRTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CRTableAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        CRTablePostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        CRTablePhone.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        CRTableCountry.setCellValueFactory(new PropertyValueFactory<>("customerCountryName"));
        CRTableState.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));

        CRTable.setItems(customerList);//In the middle of setting up customers table
    }


    /**
     * Initializes the columns for the delete table in the Customer GUI.
     * @throws SQLException If a SQL exception occurs.
     */
    public void deleteTableInitialize() throws SQLException {

        CRTableId1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CRTableName1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CRTableAddress1.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        CRTablePostalCode1.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        CRTablePhone1.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        CRTableCountry1.setCellValueFactory(new PropertyValueFactory<>("customerCountryName"));
        CRTableState1.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));

    }



}
