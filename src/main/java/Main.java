import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {


    public void start(Stage primaryStage) throws Exception {

        //Set Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/test.fxml")));

        primaryStage.setTitle("Main Menu");
        Scene scene = new Scene(root, 1000, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("Stage & Scene Set");

    }

    public static void main(String[] args) {launch();}
}
