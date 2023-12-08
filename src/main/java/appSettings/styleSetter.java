package appSettings;

import devTools.DevToolC;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.print.DocFlavor;
import java.net.URL;
import java.util.Objects;

/**
 * Style setter for managing css styles across scenes. This is needed because StyleManager is not currently supported
 * & was causing problems.
 */
public class styleSetter {

    //TODO [] Alter Java docs to match this class

    /**
     * Boolean to turn off or on DarkMode
     */
    private static boolean darkModeOn = true;

    /**
     * Turns on the developer tools.
     */
    public static void darkModeOn() {
        darkModeOn = true;
    }

    /**
     * Turns off the developer tools.
     */
    public static void darkModeOff() {
        darkModeOn = false;
    }

    /**
     * gets state of developer tools.
     */
    public static boolean darkModeBool() {
        return darkModeOn;
    }

    /**
     * Prints the given string to the console, but only if developer tools are enabled.
     *
     */
    public static String stringDarkModeStatus() {
        if (darkModeOn) {
            return "Dark Mode On";
        } else {
            return "Dark Mode Off";
        }
    }

    private static String webDarkModeCSS = "https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css";
    //public static URL darkCssURL = styleSetter.getResource("style/dark.css");
    //public String darkCssPathString = darkCssUrl.toString;
    private static String darkCssPathStr = styleSetter.class.getResource("/styles/dark.css").toExternalForm();

    private static String chosenCssPath = darkCssPathStr;

    //TODO [] test applyStyleToScene in main[c] & controller[], -

    public static void applyStyleToScene(Scene scene) {
        if (darkModeOn) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(chosenCssPath);
            DevToolC.println("StyleSheets Added");

        } else {
            scene.getStylesheets().clear();
            DevToolC.println("StyleSheets removed");
        }
    }

}
