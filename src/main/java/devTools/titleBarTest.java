package devTools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.KeyStore;

public class titleBarTest {

    private static boolean customTitleBarON = true;

    /**
     * Turns on the customTitleBar
     */
    public static void setCustomTitleBarON(){
        customTitleBarON = true;
    }
    /**
     * Turns off the customTitleBar
     */
    public static void setCustomTitleBarOFF(){
        customTitleBarON = false;
    }

    /*
    TODO Create 2 methods to remove/add the title default bar,  []  -name undecorateStage & redecorateStage
    -this will have to be done in ether the
    TODO add custom title bar that is set to a dif color,       []  -name addCustomBar & removeCustomBar
    -note I am having trouble touching the current anchor pain
    TODO add button methods to min/maximize & close application,[]

    TODO create a menu option to add/remove custom tittle bar,  []
     */

//    @FXML
//    private AnchorPane anchorPane; // Assuming you have an AnchorPane in your FXML file

    //TO REMOVE DEFAULT TITLE BAR//
    public static void redecorateStage(Stage primaryStage){

        if (customTitleBarON) {
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



    public void createTitleBar(AnchorPane anchorPane) {
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.TOP_RIGHT);
        titleBar.setPrefHeight(25.0);
        titleBar.setPrefWidth(530.0);
        titleBar.setStyle("-fx-background-color: #303139;");

        Button minimizeButton = createButton("_", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                titleBarTest.this.minimize(event);
            }
        });
        Button maximizeRestoreButton = createButton("□", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                titleBarTest.this.maximizeRestore(event);
            }
        });
        Button closeButton = createButton("X", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                titleBarTest.this.close(event);
            }
        });

        titleBar.getChildren().addAll(minimizeButton, maximizeRestoreButton, closeButton);

        // Assuming you have an AnchorPane in your FXML file to add the titleBar
        AnchorPane.setRightAnchor(titleBar, 0.0);
        anchorPane.getChildren().add(titleBar);
    }

    private Button createButton(String text, EventHandler<ActionEvent> actionHandler) {
        Button button = new Button(text);
        button.setOnAction(actionHandler);
        button.getStyleClass().add("title-bar-button");
        return button;
    }

    private void minimize(ActionEvent event) {
        System.out.println("Minimize button clicked.");
        // Your minimize logic
    }

    private void maximizeRestore(ActionEvent event) {
        System.out.println("Maximize/Restore button clicked.");
        // Your maximize/restore logic
    }

    private void close(ActionEvent event) {
        System.out.println("Close button clicked.");
        // Your close logic
    }
}

