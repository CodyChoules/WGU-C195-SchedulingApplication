package applicationTools;

import applicationObject.ApplicationObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

/**
 * Utility class for common tasks in java.
 */
public class GeneralTools {

    /**
     * Capitalizes the first letter of the given string.
     * @param input The input string.
     * @return The input string with the first letter capitalized.
     */
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input; //return input as is if it's null or empty
        }

        //capitalize the first letter and concatenate with the rest of the string
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * Capitalizes the first letter of the given string and adds spaces before each subsequent capital letter.
     * @param input The input string.
     * @return The formatted string with spaces.
     */
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

    /**
     * Populates a ComboBox with unique names from a list of ApplicationObjects.
     * @param comboBox The ComboBox to be populated.
     * @param list     The list of ApplicationObjects.
     */
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
    /**
     * Populates a ComboBox with unique names from a list of ApplicationObjects
     * This overloads to add an option for none or all for search purposes.
     * @param comboBox           The ComboBox to be populated.
     * @param list               The list of ApplicationObjects.
     * @param allValueOption     The additional option to include in the ComboBox.
     */
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

