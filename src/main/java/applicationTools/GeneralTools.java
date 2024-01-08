package applicationTools;

import applicationObject.ApplicationObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

public class GeneralTools {


    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return input as is if it's null or empty
        }

        // Capitalize the first letter and concatenate with the rest of the string
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String capitalizeAndAddSpaces(String input) {
        if (input == null || input.isEmpty()) {
            return input; //Return input as is if it's null or empty
        }
        StringBuilder result = new StringBuilder();
        result.append(input.substring(0, 1).toUpperCase());

        for (int i = 1; i < input.length(); i++){
            //Iterate over the characters starting from the second character
            char currentChar = input.charAt(i);
            //Add a space before each capital letter
            if (Character.isUpperCase(currentChar)){result.append(" ");}
            result.append(currentChar);
        }
        return result.toString();
    }

    public static void populateComboBoxWithObsNameSet(ComboBox<String> comboBox, ObservableList<ApplicationObject> list){
        ObservableList<String> listOfNames = FXCollections.observableArrayList();
        for (ApplicationObject apObj: list){
            String name = apObj.getName();
            if (listOfNames.contains(name)){
                continue;
            }
            listOfNames.add(name);
        }

        comboBox.setItems(listOfNames);
    }
    public static void populateComboBoxWithObsNameSet(ComboBox<String> comboBox, ObservableList<ApplicationObject> list, String allValueOption){
        ObservableList<String> listOfNames = FXCollections.observableArrayList();

        listOfNames.add(allValueOption);

        for (ApplicationObject apObj: list){
            String name = apObj.getName();
            if (listOfNames.contains(name)){
                continue;
            }
            listOfNames.add(name);
        }

        comboBox.setItems(listOfNames);
    }

}

