import devTools.DevToolC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TestMain extends Application {


    public void start(Stage primaryStage) throws Exception {
        DevToolC.toolsOn();

        //Set Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Test.fxml")));

        primaryStage.setTitle("Main Menu");

        DevToolC.redecorateStage(primaryStage);

        Scene scene = new Scene(root,530,315);


        DevToolC.setWindowCenteredOnCursor(scene,primaryStage);

        primaryStage.setScene(scene);


        primaryStage.show();

        DevToolC.applyDevStyleToScene(scene);

        DevToolC.println("Stage & Scene Set");


    }

    public static void main(String[] args) {launch();}
}
