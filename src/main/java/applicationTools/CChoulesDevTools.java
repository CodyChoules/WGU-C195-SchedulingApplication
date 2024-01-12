package applicationTools;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Developer tools authored by Cody Choules, commonly used tools for ease of use during development that can be easily turned off for non-development environments.
 */
public class CChoulesDevTools {

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
            CChoulesDevTools.println("Centered stage on mouse with: " + sceneWidth + " / 2 = " + halfWidth + " ... \n");
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
            CChoulesDevTools.println(string);
        }
    }

    private static boolean devStyleOn = false;

    /**
     * Checks if development style is currently enabled.
     * Dev style is for ease of use while developing.
     * @return True if development style is enabled; false otherwise.
     */
    public boolean isDevStyleOn() { return devStyleOn; }
    /**
     * enables or disables development style.
     * Dev style is for ease of use while developing
     * @param devStyle True to enable development style; false to disable.
     */
    public void setDevStyleOn(boolean devStyle) { devStyleOn = devStyle; }

    /**
     * Applies the development style to the given JavaFX scene if development style is enabled and dev tools are on.
     * Currently set to a popular git resource for dark mode java fx.
     * @param scene The JavaFX scene to which the development style will be applied.
     */
    public static void applyDevStyleToScene(Scene scene) {
        if (devStyleOn) {
            if (devToolsOn) {
                scene.getStylesheets().add("https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css");
            }
        }
    }

    //Test for undecorated stage
    /**
     * Redecorates the provided JavaFX stage by removing window decorations and adding a custom-styled BorderPane.
     * Testing not working.
     * @param primaryStage The primary JavaFX stage to be redecorated.
     */
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
