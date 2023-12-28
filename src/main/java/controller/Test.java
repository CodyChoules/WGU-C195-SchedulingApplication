package controller;

import applicationObject.guiObject.Searcher;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Test extends Application {
    public void start(Stage stage) {
        HBox root = new HBox();

        ComboBox<String> cb = new ComboBox<String>();
        cb.setEditable(true);

        // Create a list with some dummy values.
        ObservableList<String> items = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine", "Ten");

        //vvvvvvvvvvvvv
//        // Create a FilteredList wrapping the ObservableList.
//        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);
//
//        // Add a nameListener to the textProperty of the combobox editor.
//        cb.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
//            final TextField editor = cb.getEditor();
//            final String selected = cb.getSelectionModel().getSelectedItem();
//
//            // This needs run on the GUI thread to avoid the error described
//            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
//            Platform.runLater(() -> {
//                // If the no item in the list is selected or the selected item
//                // isn't equal to the current input, we refilter the list.
//                if (selected == null || !selected.equals(editor.getText())) {
//                    filteredItems.setPredicate(item -> item.toUpperCase().startsWith(newValue.toUpperCase()));
//                    cb.show(); // Show the dropdown
//                }
//            });
//        });
//
//        // Close the popup when Enter is pressed
//        cb.getEditor().setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                cb.hide();
//            }
//        });
//
//        cb.setItems(filteredItems);
        //^^^^^^^^
        Searcher.nameListener(cb,items);

        root.getChildren().add(cb);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
