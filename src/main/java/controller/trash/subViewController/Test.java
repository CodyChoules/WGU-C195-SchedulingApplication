package controller.trash.subViewController;

import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class Test extends Application {

    @FXML public Button theButton;
    public Label theButtonLabel;


    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOn();

        //Sets Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(Test.class.getResource("/views/trash/subTest.fxml")));
        primaryStage.setTitle("Main Menu");

        //TODO [Extra] test to decorate stage(remove if no solution found){Good chance to use git to add this feature}
        //CChoulesDevTools.redecorateStage(primaryStage);

        Scene scene = new Scene(root,530,315);

        CChoulesDevTools.setWindowCenteredOnCursor(scene,primaryStage);

        primaryStage.setScene(scene);

        primaryStage.show();

        CChoulesDevTools.applyDevStyleToScene(scene);

        CChoulesDevTools.println("Stage & Scene Set");

        //TODO [c] test DB connection using JBDTools -completed
        JDBTools.openConnection();
        CChoulesDevTools.println("Launching Arguments");
        JDBTools.closeConnection();

    }

    public static void main(String[] args) {

        launch();

    }
}
