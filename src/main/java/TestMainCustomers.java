import applicationObject.User;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import controller.CustomerController;
import javafx.application.Application;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class TestMainCustomers extends Application {

    public Stage mainStage = new Stage();

    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOn();

        Main.currentUser = new User(1, "Test", "NoNoNo");


        mainStage = primaryStage;

        try {
            JDBTools.openConnection();
            CustomerController.loadThisFXML(mainStage);
        } catch (IOException e) {
            CChoulesDevTools.println(e.toString());
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {launch();}
}
