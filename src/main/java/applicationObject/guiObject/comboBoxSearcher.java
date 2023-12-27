package applicationObject.guiObject;

import applicationTools.CChoulesDevTools;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class comboBoxSearcher {

    //TODO [] organize this code
    public static void nameListener(ComboBox<String> comboBox, ObservableList<String> items){
        //TODO [cu] create a listener to add to all combo boxes for application GUI
        comboBox.setEditable(true);
        comboBox.setVisibleRowCount(5);
        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);
        FilteredList<String> unFilteredItems = new FilteredList<String>(items, p -> false);
        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final String selected = comboBox.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> item.toUpperCase().startsWith(newValue.toUpperCase()));
                    unFilteredItems.setPredicate(item -> item.toUpperCase().startsWith(newValue.toUpperCase()));
                    comboBox.setItems(filteredItems);
                    comboBox.show();
                    //TODO [l] bug trying to get dropdown to show all options
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

        // Close the popup when Enter is pressed
        comboBox.getEditor().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                comboBox.hide();
            }
        });



        comboBox.setItems(filteredItems);

    }

    public static void timeListener(ComboBox<String> comboBox, ObservableList<String> items){

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
