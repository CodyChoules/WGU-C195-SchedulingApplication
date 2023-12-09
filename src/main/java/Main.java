import devTools.DevToolC;
import devTools.JDBTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {


    public void start(Stage primaryStage) throws Exception {
        DevToolC.toolsOn();

        //Sets Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/loginMenu.fxml")));
        primaryStage.setTitle("Main Menu");

        //TODO [Extra] test to decorate stage(remove if no solution found){Good chance to use git to add this feature}
        //DevToolC.redecorateStage(primaryStage);

        Scene scene = new Scene(root,530,315);

        DevToolC.setWindowCenteredOnCursor(scene,primaryStage);

        primaryStage.setScene(scene);

        primaryStage.show();

        DevToolC.applyDevStyleToScene(scene);

        DevToolC.println("Stage & Scene Set");

        //TODO [c] test DB connection using JBDTools -completed
        JDBTools.openConnection();
        DevToolC.println("Launching Arguments");
        JDBTools.closeConnection();

    }

    public static void main(String[] args) {

        launch();

    }
}
