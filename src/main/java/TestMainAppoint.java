import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import controller.Appointments;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class TestMainAppoint extends Application {

    public Stage mainStage = new Stage();

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
        mainStage = primaryStage;

        try {
            JDBTools.openConnection();
            Appointments.loadThisFXML(mainStage);
        } catch (IOException e) {
            CChoulesDevTools.println(e.toString());
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {launch();}
}
