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

    /**
     * Applies CSS styles to the given scene based on the current dark mode.
     * @param scene The scene to apply styles to.
     */
    public static void applyCSS(Scene scene){
        CChoulesDevTools.println("setting style sheets");
        if (currentDarkMode.isDarkModeOn()) {
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/cStyleSheets/" + currentDarkMode.getCssResource() + ".css")).toString());
        } else {
            scene.getStylesheets().clear();
        }
    }
    /**
     * Opens a new scene as a window.
     * @param fxName The name of the FXML file to load.
     * @throws IOException If an I/O error occurs during FXML loading.
     */
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
    /**
     * Opens a new scene as a tab and switches to it.
     * @param fxName   The name of the FXML file to load.
     * @param tabPane  The TabPane to add the new tab to.
     * @throws Exception If an exception occurs during FXML loading or tab creation.
     */
    public static void openSceneAsTabAndGo(String fxName, TabPane tabPane) throws Exception {
        String fxmlToDisplay = GeneralTools.capitalizeAndAddSpaces(fxName);
        Tab newTab = createTab(fxmlToDisplay, fxName, tabPane);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }
    /**
     * Opens a new scene as a tab.
     * @param fxName   The name of the FXML file to load.
     * @param tabPane  The TabPane to add the new tab to.
     * @throws Exception If an exception occurs during FXML loading or tab creation.
     */
    private static Tab createTab(String tabTitle, String fxName, TabPane tabPane) throws Exception {
        Tab tab = new Tab(tabTitle);

        //Load the FXML file
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(
                "/views/" + fxName + ".fxml"));
        Parent root = loader.load();

        //Set the loaded content to the tab
        tab.setContent(root);
        tab.isClosable();
        //TabPane tabPane = tab.getTabPane();
        //Set event handler for tab selection

        tab.selectedProperty().addListener(
                (observable, wasSelected, isSelected) -> {
                    if (isSelected) {
                        //Refresh the content of the selected tab
                        try {
                            refreshTab(tab, fxName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        return tab;
    }

    /**
     * Reloads a given tab. Currently done on switching tabs as well.
     * @param tab The Tab.
     * @param fxName The name of the FXML file to load.
     * @throws IOException If an I/O error occurs during FXML loading.
     */
    private static void refreshTab(Tab tab, String fxName) throws IOException {
        CChoulesDevTools.println("Refreshing content for tab: " + tab.getText());
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(
                "/views/" + fxName + ".fxml"));
        Parent root = loader.load();
        tab.setContent(root);

        //TODO  [Extra] This defeats The purpose of tabs as It refreshes The user input as well. I am running out of time and this is out of scope but we should alter this so that it calls a refresh method in the tab controller to refresh only the data from the dataBase in things like combo boxes and tables.
    }

    //USER\\
    /**
     * Placeholder user with undefined values.
     */
    public static final User undefinedUser = new User (0, "UndefinedUserName", "NoNoNo");
    /**
     * Current logged-in user.
     */
    public static User currentUser = undefinedUser;
    /**
     * User used to keep track of login information on login refresh.
     */
    public static User loginKeeper = new User (0, "", "");
    /**
     * Sets the current user.
     * @param user The user to set.
     */
    public static void setCurrentUser(User user){
        currentUser = user;
    }

    //MESSAGE BUNDLE\\
    Locale defaultLocale = Locale.getDefault();
    Locale enLocale = new Locale("en"); //For manual Language Switching
    public static Locale frLocale = new Locale("fr"); //^
    public static ResourceBundle defaultMessages = ResourceBundle.getBundle("MessagesBundle");
    /**
     * Current message bundle being use during login.
     */
    public static ResourceBundle curMessBun /*CurrentMessagesBundle*/ = defaultMessages;
    public static ArrayList<String> supportedMessageBundles = new ArrayList<String>(Arrays.asList("en", "fr"));


    //FXML LOADERS\\
    // FXML LOADERS \\
    /**
     * Loads the main window and sets the current user.
     * @param user The user to set as the current user.
     * @throws IOException If an I/O error occurs during FXML loading.
     */
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
        openSceneAsTabAndGo("viewAppointments", tabPane);
    }

    public static void loadCustomersAsWin() throws IOException {
        openSceneAsWin("customers"); }
    /**
     * Loads the appointments tab.
     * @param tabPane The TabPane to add the new tab to.
     * @throws Exception If an exception occurs during FXML loading or tab creation.
     */
    public static void loadCustomersAsTab(TabPane tabPane) throws Exception {
        openSceneAsTabAndGo("viewCustomers", tabPane);
    }

    public static void loadReportsAsWin() throws IOException {
        openSceneAsWin("reports"); }
    /**
     * Loads the Reports tab.
     * @param tabPane The TabPane to add the new tab to.
     * @throws Exception If an exception occurs during FXML loading or tab creation.
     */
    public static void loadReportsAsTab(TabPane tabPane) throws Exception {
        openSceneAsTabAndGo("viewReports", tabPane);
    }

    /**
     * Loads the login window.
     * @throws IOException If an I/O error occurs during FXML loading.
     */
    public static void loadLogin() throws IOException {

        CChoulesDevTools.println("Loading LoginWindow.fxml");

        openSceneAsWin("LoginWindow");

    }
    /**
     * Loads the login window with pre-set username and password.
     * Designed for refreshing language or style.
     * @param userName The login keeper for login.
     * @param passWord The login keeper password for login.
     * @throws IOException If an I/O error occurs during FXML loading.
     */
    public static void loadLoginWithKeeper(String userName,String passWord) throws IOException {

        CChoulesDevTools.println("Loading LoginWindow.fxml");
        loginKeeper = new User (0, userName, passWord);
        openSceneAsWin("LoginWindow");

    }

    //MAIN START APPLICATION\\
    public void start(Stage primaryStage) throws Exception {
        CChoulesDevTools.toolsOff();

        String rootFolder = System.getProperty("user.dir");
        CChoulesDevTools.println("Root Folder: " + rootFolder);

        primeStage = primaryStage;

        openSceneAsWin("loginWindow");


        //TODO [c] test DB connection using JBDTools -completed
        JDBTools.openConnection();
        CChoulesDevTools.println("Launching Arguments");
        JDBTools.closeConnection();

    }

    public static void main(String[] args) {

        launch();

    }



}
