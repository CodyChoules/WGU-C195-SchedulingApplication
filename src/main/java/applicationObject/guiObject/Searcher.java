package applicationObject.guiObject;

import applicationObject.Appointment;
import applicationObject.Country;
import applicationTools.CChoulesDevTools;
import com.mysql.cj.xdevapi.Table;
import dataAccessObject.AppointmentDAO;
import dataAccessObject.CountryDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The `Searcher` class provides methods for handling search functionality in the scheduling application's GUI.
 */
public class Searcher {

    /**
     * Adds a listener to a ComboBox for handling name filtering and displaying suggestions in the dropdown.
     *
     * @param comboBox The ComboBox to which the listener is added.
     * @param items    The list of items to be filtered.
     */
    public static void nameListener(ComboBox<String> comboBox, ObservableList<String> items){
        //TODO [c] create a listener to add to all combo boxes for application GUI

        //Fundamental JavaFX Bug Fix, comboBoxViewSkin does not properly work. Removing all key events using this seemed to work, Am not using as a lambda example\\
        ComboBoxListViewSkin<String> comboBoxListViewSkin = new ComboBoxListViewSkin<>(comboBox);
        comboBoxListViewSkin.getPopupContent().addEventFilter(KeyEvent.ANY, (event) -> {
            if( event.getCode() == KeyCode.SPACE ) {
                event.consume();
            }
        });
        comboBox.setSkin(comboBoxListViewSkin);
        //TODO [Extra] Take note of java 

        comboBox.setEditable(true);
        comboBox.setVisibleRowCount(5);
        FilteredList<String> filteredList = new FilteredList<String>(items, p -> true);
        //FilteredList<String> unFilteredItems = new FilteredList<String>(items, p -> false);

        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final String selected = comboBox.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredList.setPredicate(item -> item.toUpperCase().startsWith(newValue.toUpperCase()));
                    //unFilteredItems.setPredicate(item -> item.toUpperCase().startsWith(newValue.toUpperCase()));
                    comboBox.setItems(filteredList);
                    comboBox.show();
                    //TODO [l] bug trying to get dropdown to show all options (only shows one)
                }
            });
        });

        //To check if the combo box is selected so that the drop down shows when focused.
        comboBox.getEditor().focusedProperty().addListener( (obs) -> {
            if (comboBox.focusedProperty().getValue()) {
                comboBox.show();
            } else {
                comboBox.hide();
            }
                }
        );

        comboBox.setItems(filteredList);


    }

    //

    public static void exclusiveFDLOutputListUsingNameListener(ComboBox<String> comboBox, ObservableList<String> fdlItems, ComboBox<String> parentComboBoxCountry){


        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final String selectedCountry = parentComboBoxCountry.getSelectionModel().getSelectedItem();
            try {
                final Country country = CountryDAO.getCountryByName(selectedCountry);
                Platform.runLater(() -> {
                    if (selectedCountry == null || !selectedCountry.equals(editor.getText())) {
                        final ObservableList<String> filteredList = country.getChildFDLNames();
                        comboBox.setItems(filteredList);
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        nameListener(comboBox, comboBox.getItems());

        //Question [] it would be nice to have the ability to input types and classes raw into methods so that it can be used with compatible. How do i do this without using if else on value input or direct code injection/manipulation(like set value factory)
        //we need this to limit the FLD list for combo boxes so that the selection of a country only allows for corresponding FLDs


    }

    public static void updateFromSearch(TextField searchBar,
                                TableView<Appointment> resultTable)
            throws SQLException {
        CChoulesDevTools.println("Searching in " + resultTable.getId());

        String searchFieldValue = searchBar.getText();
        //Creating a new list to display & replacing the table items with selected items
        ObservableList<Appointment> displayedAp = lookupAppointmentNameId(searchFieldValue);
        resultTable.setItems(displayedAp);

    }

    public static void updateFromComboBox(String contact,
    TableView<Appointment> resultTable) throws SQLException {
        CChoulesDevTools.println("Searching in " +
                resultTable.getId() +
                " for contact " +
                contact);

        ObservableList<Appointment> displayedAp = lookupAppointmentByContact(contact);

        resultTable.setItems(displayedAp);
    }

    private static ObservableList<Appointment> lookupAppointmentNameId (String partialName) throws SQLException {
        ObservableList<Appointment> foundAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        partialName = partialName.toLowerCase();
        if (partialName.length() == 0){CChoulesDevTools.println("Search empty, Displaying " + "All AppointmentController");
            return allAppointments;
        }
        Integer partialInteger = null;
        try {
            partialInteger = Integer.parseInt(partialName);
            CChoulesDevTools.println("Was able to parse integer for search = " +partialInteger);
        } catch (NumberFormatException e) {
            CChoulesDevTools.println("Unable to parse integer for search on: " + partialName);
        }
        for (Appointment ap: allAppointments) {
            Integer ID = ap.getApId();
            if (ap.getApTitle().toLowerCase().contains(partialName)
                    || ID.equals(partialInteger)) {
                foundAppointments.add(ap);
            }
        }
        if (foundAppointments.size() == 0){CChoulesDevTools.println("No ID or Name found for " +
                partialName);}
        return foundAppointments;
    }
    private static ObservableList<Appointment> lookupAppointmentByContact (String partialName) throws SQLException {
        ObservableList<Appointment> foundAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        partialName = partialName.toLowerCase();
        if (partialName.length() == 0){CChoulesDevTools.println("Selection empty, Displaying " + "All AppointmentController");
            return allAppointments;
        }
        Integer partialInteger = null;

        for (Appointment ap: allAppointments) {
            Integer ID = ap.getApId();
            if (ap.getApContactName().toLowerCase().contains(partialName)) {
                foundAppointments.add(ap);
            }
        }
        if (foundAppointments.size() == 0){CChoulesDevTools.println("No contact found for " +
                partialName);}
        return foundAppointments;
    }

    public static ObservableList<String> timeIntervalsList() {
        ObservableList<String> intervalsList = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(0, 0);
        for (int i = 0; i < 96; i++) {
            intervalsList.add(startTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            startTime = startTime.plusMinutes(15);
        }
        CChoulesDevTools.println(intervalsList.toString());
        return intervalsList;
    }



}
