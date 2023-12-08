package devTools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class titleBarTest {

//    @FXML
//    private AnchorPane anchorPane; // Assuming you have an AnchorPane in your FXML file

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

