package devTools;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Developer tools authored by Cody Choules, commonly used tools for ease of use during development that can be easily turned off for non-development environments.
 */
public class DevToolC {

    /**
     * Boolean to turn off or on DevTool functions
     */
    private static boolean devToolsOn = false;

    /**
     * Sets the window centered on the cursor.
     *
     * @param scene The JavaFX Scene object
     * @param primaryStage The JavaFX Stage object
     */
    public static void setWindowCenteredOnCursor(Scene scene, Stage primaryStage) {
        if (devToolsOn) {
            double mouseX = java.awt.MouseInfo.getPointerInfo().getLocation().x;
            double mouseY = java.awt.MouseInfo.getPointerInfo().getLocation().y;
            int sceneWidth = scene.widthProperty().intValue();
            int halfWidth = sceneWidth / 2;
            primaryStage.setX(mouseX - halfWidth);
            primaryStage.setY(mouseY - 10);
            System.out.print("Centered stage on mouse with: " + sceneWidth + " / 2 = " + halfWidth + " ... \n");
        }
    }

    /**
     * Turns on the developer tools.
     */
    public static void toolsOn() {
        devToolsOn = true;
    }

    /**
     * Turns off the developer tools.
     */
    public static void toolsOff() {
        devToolsOn = false;
    }

    /**
     * gets state of developer tools.
     */
    public static boolean toolsState() {
        return devToolsOn;
    }

    /**
     * Prints the given string to the console, but only if developer tools are enabled.
     *
     * @param string The string to be printed.
     */
    public static void println(String string) {
        if (devToolsOn) {
            System.out.println(string);
        }
    }

    public static void applyDevStyleToScene(Scene scene){
        scene.getStylesheets().add("https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css");
    }

    //Test for undecorated stage
    public static void redecorateStage(Stage primaryStage){
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: green;");

        ToolBar toolBar = new ToolBar();

        int height = 25;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);
//        toolBar.getItems().add(new WindowButtons());

        borderPane.setTop(toolBar);

        primaryStage.setScene(new Scene(borderPane, 300, 250));
        primaryStage.show();
    }
}
