package controller;

import applicationObject.Country;
import applicationObject.Customer;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
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

import static dataAccessObject.CustomerDAO.updateCustomer;

public class Customers {
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
    private Customer updatingCustomer;


    //TABLE METHODS\\
    public void handleTableDoubleClick(MouseEvent mouseEvent) throws SQLException {
        //TODO [c] Test Double Click - success
        if (mouseEvent.getClickCount() == 2) {
            selectCustomer();
        }
    }

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


    @FXML public void CRClearDeleteTable(ActionEvent actionEvent) {
        customersToDelete.removeAll();
        CRDeleteTable.setItems(customersToDelete);
    }

    @FXML public void CRSaveCustomer(ActionEvent actionEvent) throws SQLException {
        if (CRNameField.getText().isEmpty() ||
                CRPhoneNumberField.getText().isEmpty() ||
                CRAddressField.getText().isEmpty() ||
                CRCountryDropDown.getValue().isEmpty() ||
                CRFirstLvlDivDropDown.getValue().isEmpty() ||
                CRPostalCodeField.getText().isEmpty())
        {
            //TODO [] put alert to fill here
            return;
        }

        int updatingDivisionId = 1;
        LocalDateTime nullCreateDateToBeCreatedByDB = LocalDateTime.now();

        updatingDivisionId = FirstLvlDivisionDAO.getFLD_IdByName(CRFirstLvlDivDropDown.getValue());

        updatingCustomer = new Customer(
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
    @FXML public void CRAddCustomer(ActionEvent actionEvent) throws SQLException {
        //Note 1 indicated that this object is in the add tab
        if (CRNameField1.getText().isEmpty() ||
                CRPhoneNumberField1.getText().isEmpty() ||
                CRAddressField1.getText().isEmpty() ||
                CRCountryDropDown1.getValue().isEmpty() ||
                CRFirstLvlDivDropDown1.getValue().isEmpty() ||
                CRPostalCodeField1.getText().isEmpty())
        {
            //TODO [] put alert to fill here
            return;
        }

        int addDivisionId = FirstLvlDivisionDAO.getFLD_IdByName(CRFirstLvlDivDropDown1.getValue());;

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

    @FXML public void CRSelectCustomerButton(ActionEvent actionEvent) {
    }

    @FXML public void CRCancel(ActionEvent actionEvent) {
    }

    @FXML public void CRDelete(ActionEvent actionEvent) {
    }


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
        FXMLLoader loader = new FXMLLoader(Customers.class.getResource("/views/customers.fxml"));


        Parent root = loader.load();

        Customers mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);



        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        mainStage = stage;
    }

    public void initialize() throws SQLException{

        tableLoadFromDB();
        initialComboBoxLoadFromDB();
        deleteTableInitialize();

    }

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
