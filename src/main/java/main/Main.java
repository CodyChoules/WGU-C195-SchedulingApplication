package main;

import applicationObject.User;
import applicationTools.CChoulesDevTools;
import applicationTools.DarkModeTool;
import applicationTools.GeneralTools;
import applicationTools.JDBTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Main extends Application {

    //Stage\\
    String cssPathForDev =
            "https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css";

    public static DarkModeTool currentDarkMode =
            new DarkModeTool(false, "dark_mode");
    public static Stage primeStage = null;
    public static void applyCSS(Scene scene){
        CChoulesDevTools.println("setting style sheets");
        if (currentDarkMode.isDarkModeOn()) {
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/cStyleSheets/" + currentDarkMode.getCssResource() + ".css")).toString());
        } else {
            scene.getStylesheets().clear();
        }
    }
    public static void openSceneAsWin(String fxName) throws IOException {
        CChoulesDevTools.println("Loading " + fxName + ".fxml from Main");
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(
                "/views/" + fxName + ".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primeStage.setTitle("Scheduler - User : " +
                currentUser.getName() + " : User ID :" +
                currentUser.getId());
        applyCSS(scene);
        //CChoulesDevTools.applyDevStyleToScene(scene);
        primeStage.setScene(scene);
        primeStage.show(); }
    public static void openSceneAsTabAndGo(String fxName, TabPane tabPane) throws Exception {
        String fxmlToDisplay = GeneralTools.capitalizeAndAddSpaces(fxName);
        Tab newTab = createTab(fxmlToDisplay, fxName);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }
    private static Tab createTab(String tabTitle, String fxName) throws Exception {
        Tab tab = new Tab(tabTitle);

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(
                "/views/" + fxName + ".fxml"));
        Parent root = loader.load();

        // Set the loaded content to the tab
        tab.setContent(root);
        tab.isClosable();

        return tab;
    }

    //USER\\
    public static final User undefinedUser = new User (0, "UndefinedUserName", "NoNoNo");
    public static User currentUser = undefinedUser;
    public static User loginKeeper = new User (0, "", "");
    public static void setCurrentUser(User user){
        currentUser = user;
    }

    //MESSAGE BUNDLE\\
    Locale defaultLocale = Locale.getDefault();
    Locale enLocale = new Locale("en"); //For manual Language Switching TODO []
    public static Locale frLocale = new Locale("fr"); //^
    public static ResourceBundle defaultMessages = ResourceBundle.getBundle("MessagesBundle");
    public static ResourceBundle curMessBun /*CurrentMessagesBundle*/ = defaultMessages;
    public static ArrayList<String> supportedMessageBundles = new ArrayList<String>(Arrays.asList("en", "fr"));


    //FXML LOADERS\\
    public static void loadMainWindowAndSetUser(User user) throws IOException {
        setCurrentUser(user);
        openSceneAsWin("primeWindow"); }
    public static void loadMainWindow() throws IOException {
        CChoulesDevTools.println("Loading mainWindow.fxml");
        openSceneAsWin("mainWindow");
    }

    public static void loadAppointmentsAsWin() throws IOException {
        openSceneAsWin("appointments"); }
    public static void loadAppointmentsAsTab(TabPane tabPane) throws Exception {
        openSceneAsTabAndGo("appointments", tabPane);
    }

    public static void loadCustomersAsWin() throws IOException {
        openSceneAsWin("customers"); }
    public static void loadCustomersAsTab(TabPane tabPane) throws Exception {
        openSceneAsTabAndGo("customers", tabPane);
    }

    public static void loadReportsAsWin() throws IOException {
        openSceneAsWin("reports"); }
    public static void loadReportsAsTab(TabPane tabPane) throws Exception {
        openSceneAsTabAndGo("reports", tabPane);
    }

    public static void loadLogin() throws IOException {

        CChoulesDevTools.println("Loading Login.fxml");

        openSceneAsWin("login");

    }
    public static void loadLoginWithKeeper(String userName,String passWord) throws IOException {

        CChoulesDevTools.println("Loading Login.fxml");
        loginKeeper = new User (0, userName, passWord);
        openSceneAsWin("login");

    }

    //MAIN START APPLICATION\\
    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOn();

        primeStage = primaryStage;

        openSceneAsWin("login");
//        //Sets Parent root of scene then title & size
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/login.fxml")));
//        primeStage.setTitle("main.Login Menu");
//
//        //TODO [Extra] test to decorate stage(remove if no solution found){Good chance to use git to add this feature}
//        //CChoulesDevTools.redecorateStage(primaryStage);
//
//        Scene scene = new Scene(root);
//
//        //CChoulesDevTools.setWindowCenteredOnCursor(scene,primaryStage);
//
//        primeStage.setScene(scene);
//
//        primeStage.show();
//
//        CChoulesDevTools.applyDevStyleToScene(scene);
//
//        CChoulesDevTools.println("Stage & Scene Set");

        //TODO [c] test DB connection using JBDTools -completed
        JDBTools.openConnection();
        CChoulesDevTools.println("Launching Arguments");
        JDBTools.closeConnection();

    }

    public static void main(String[] args) {

        launch();

    }
}
