package controller;

import applicationObject.Appointment;
import applicationObject.Contact;
import applicationObject.Customer;
import applicationTools.CChoulesDevTools;
import dataAccessObject.ContactDAO;
import dataAccessObject.CustomerDAO;
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
import java.util.stream.Collectors;

public class Customers {
    public static Stage mainStage; //This is for page refresh. May not work with tabs
    //CUSTOMER PRIMARY TABLE\\;
    @FXML public TableView<applicationObject.Customer> CRTable;
    @FXML public TableColumn<?, ?> CRTableId;
    @FXML public TableColumn<?, ?> CRTableName;
    @FXML public TableColumn<?, ?> CRTableAddress;
    @FXML public TableColumn<?, ?> CRTablePostalCode;
    @FXML public TableColumn<?, ?> CRTablePhone;
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
    @FXML public ComboBox<String> CRStateDropDown; //Todo [] same ^
    @FXML public TextField CRPostalCodeField;
    //TABS FOR EDITOR\\
    @FXML public Tab editCustomerTab;
    @FXML public Tab addCustomerTab;
    @FXML public Tab deleteCustomerTab;
    //CUSTOMER ADDER\\

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
            Customer selectedObj =CRTable.getSelectionModel().getSelectedItem();
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
            //CRCountryDropDown.setValue(selectedObj.());
            CRStateDropDown.setValue(selectedObj.getCustomerDivisionName());
        }
    }

    public void CRSaveCustomer(ActionEvent actionEvent) {
    }

    public void CRSelectCustomerButton(ActionEvent actionEvent) {
    }

    public void CRCancel(ActionEvent actionEvent) {
    }

    public void CRDelete(ActionEvent actionEvent) {
    }

    public void CRAddCustomer(ActionEvent actionEvent) {
    }

    public void CRCountryDropDown(ActionEvent actionEvent) {
    }

    //FXML LOADER METHOD//
    public static void loadThisFXML(Stage stage) throws IOException {

        Home.testMethod();
        //Home.loadHomeFXML(loginButton, getClass());

        CChoulesDevTools.println("Loading customers.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(controller.Customers.class.getResource("/views/customers.fxml"));


        Parent root = loader.load();

        controller.Customers mp = loader.getController();

//        //passing the css settings
//        mp.passCss(cssPath, darkModeOn);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        mainStage = stage;
    }

    public void initialize() throws SQLException{

        tableLoadFromDB();

    }

    public void tableLoadFromDB() throws SQLException {
        ObservableList<applicationObject.Customer> customerList = CustomerDAO.getAllCustomers();

        CRTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CRTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CRTableAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        CRTablePostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        CRTablePhone.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        CRTableState.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));

        CRTable.setItems(customerList);//In the middle of setting up customers table
    }
}
