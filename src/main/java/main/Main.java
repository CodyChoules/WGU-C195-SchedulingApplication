package main;

import applicationObject.User;
import applicationTools.CChoulesDevTools;
import applicationTools.DarkModeTool;
import applicationTools.JDBTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    //        scene.getStylesheets().add("https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css");

    String cssPathForDev = "https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css";

    public static DarkModeTool currentDarkMode = new DarkModeTool(false, "testCSS");
    public static Stage primeStage = null;
    public static User currentUser = new User (0, "UndefinedUserName", "NoNoNo");
    public static User loginKeeper = new User (0, "", "");

    //MESSAGE BUNDLE\\

    public static User loadMainWindowAndSetUser(User user) throws IOException {
        setCurrentUser(user);

        CChoulesDevTools.println("Loading mainWindow.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/windows/mainWindow.fxml"));

        Parent root = loader.load();

        // 1a // stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);

        CChoulesDevTools.applyDevStyleToScene(scene);

        primeStage.setScene(scene);
        primeStage.show();

        return currentUser;
    }

    public static void setCurrentUser(User user){
        currentUser = user;
    }


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
    
    public static void loadLogin() throws IOException {

        CChoulesDevTools.println("Loading Login.fxml");

        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/windows/login.fxml"));

        Parent root = loader.load();

        // 1a // stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);

        CChoulesDevTools.applyDevStyleToScene(scene);

        primeStage.setScene(scene);
        primeStage.show();


    }

    public static void loadLoginWithKeeper(String userName,String passWord) throws IOException {

        CChoulesDevTools.println("Loading Login.fxml");

        loginKeeper = new User (0, userName, passWord);
        //Note: this is incorrect I keep doing this bellow:
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/windows/login.fxml"));

        Parent root = loader.load();

        // 1a // stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);

        CChoulesDevTools.applyDevStyleToScene(scene);

        primeStage.setScene(scene);
        primeStage.show();

    }


    
    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOn();

        primeStage = primaryStage;

        //Sets Parent root of scene then title & size
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/windows/login.fxml")));
        primeStage.setTitle("main.Login Menu");

        //TODO [Extra] test to decorate stage(remove if no solution found){Good chance to use git to add this feature}
        //CChoulesDevTools.redecorateStage(primaryStage);

        Scene scene = new Scene(root);

        //CChoulesDevTools.setWindowCenteredOnCursor(scene,primaryStage);

        primeStage.setScene(scene);

        primeStage.show();

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
