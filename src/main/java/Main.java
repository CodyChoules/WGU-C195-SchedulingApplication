import devTools.DevToolC;
import appSettings.styleSetter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {


    public void start(Stage primaryStage) throws Exception {
        DevToolC.toolsOn();

        //Set Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/loginMenu.fxml")));

        primaryStage.setTitle("Main Menu");

        // TODO [] Test to decorate stage(remove if no solution found){Good chance to use git to add this feature TODO}
            // this is active in branch "title-bar-feature-trial"
        Scene scene = new Scene(root,530,315);

        DevToolC.setWindowCenteredOnCursor(scene,primaryStage);

        primaryStage.setScene(scene);

        primaryStage.show();

//        styleSetter.darkModeOn();
        styleSetter.applyStyleToScene(scene);
        //DevToolC.applyDevStyleToScene(scene);

        DevToolC.println("Stage & Scene Set");


    }

    public static void main(String[] args) {launch();}
}
