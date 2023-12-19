import applicationTools.CChoulesDevTools;
import controller.Appointments;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TestMain extends Application {


    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOn();

//        //Set Parent root of scene then title & size
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/windows/mainWindow.fxml")));
//
//        primaryStage.setTitle("Main Menu");
//
//        CChoulesDevTools.redecorateStage(primaryStage);
//
//        Scene scene = new Scene(root,530,315);
//
//
//        CChoulesDevTools.setWindowCenteredOnCursor(scene,primaryStage);
//
//        primaryStage.setScene(scene);
//
//
//        primaryStage.show();
//
//        CChoulesDevTools.applyDevStyleToScene(scene);
//
//        CChoulesDevTools.println("Stage & Scene Set");


        Appointments.loadThisFXML(primaryStage);




    }

    public static void main(String[] args) {launch();}
}
