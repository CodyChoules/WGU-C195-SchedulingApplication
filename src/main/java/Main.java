import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    // Missing Skill:
    // Skill Found: Java Community Naming Conventions:
    // In Java, the convention is to use PascalCase (also known as CamelCase
    // w/ an uppercase C in Camel as opposed to camelCase for lower case first)
    // for class names. This means that the first letter of each word in the
    // class name is capitalized INCLUDING THE FIRST WORD. {Note: This is why
    // String, a class, is capitalized and other data types are not.} Conversely,
    // variable names & method names usually use camelCase meaning they use a
    // lowercase for letter for the first word then uppercase for the following.
    // TODO [c] updated the naming convention throughout the project.
    
    
    
    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOn();

        //Sets Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/login.fxml")));
        primaryStage.setTitle("Main Menu");

        //TODO [Extra] test to decorate stage(remove if no solution found){Good chance to use git to add this feature}
        //CChoulesDevTools.redecorateStage(primaryStage);

        Scene scene = new Scene(root);

        //CChoulesDevTools.setWindowCenteredOnCursor(scene,primaryStage);

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
