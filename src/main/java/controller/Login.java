package controller;

import applicationObject.User;
import applicationObject.guiObject.SchedulingApplicationPrompt;
import dataAccessObject.UserDAO;

import applicationTools.CChoulesDevTools;

import applicationTools.CChoulesJTools;
import applicationTools.JDBTools;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.Main;


import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;


public class Login implements Initializable {


    @FXML public Label loginTitle;
    @FXML public Label usernameLabel;
    @FXML public Label timeZoneDisplay;
    @FXML public Label timeZoneLabel;
    @FXML public Label languageLabel;
    @FXML public Label devAnnouncementTxt;
    @FXML public Label devAnnouncementTitle;
    @FXML public ComboBox<String> languageComboBox;
    @FXML private TextField usernameInput;
    @FXML public Label passwordLabel;
    @FXML private TextField passwordInput;
    @FXML public Label locationLabel;
    @FXML private TextField loginLocationField;
    @FXML public Button loginButton;
    @FXML public Label darkModeLabel;
    @FXML public Button darkModeButton; //FXML fx:id References
    @FXML public Button exitButton;


    Stage stage;


    //todo [c] transferred this method to PrimeWindow
    private void loginMethod() throws IOException, InterruptedException  {

        // TODO [c] create or find a method to sanitize user input remember to implement this into any account creation as well.
        // TODO [c] Get login info from input.
        String nameInput = CChoulesJTools.sanitizeInput( usernameInput.getText() );
        String passInput = CChoulesJTools.sanitizeInput( passwordInput.getText() );

        // TODO [c] solve login on no input error.
        if (nameInput.isEmpty() || passInput.isEmpty()) {
            CChoulesDevTools.println("Input Values Found Empty, login attempt abandoned.");
            return;
        }

        JDBTools.openConnection();

        //Missing Skill,
        //Skill Found: DAO Use Case:
        // Need to create a Data Access Object(DAO).
        //DAOs are abstractions used to access persistent mechanisms
        //such as Data Bases. Focused often on CRUD (Create, Read,
        // Update, Delete) operations on data entities.

        // TODO [c] create a dataAccessObject package

        // TODO [c] create a DAO to access user date inside the SQL DB
        //Validates user input
        boolean loginValidated = UserDAO.validateUserLogin(nameInput,passInput, Main.currentUser);

        // TODO [c] create a incorrect password popup
        if (! loginValidated) {
            CChoulesDevTools.println("Input on Password or Username not found. Login attempt abandoned");
            // TODO [c] schedulingApplicationPopup is not firing -The version of java alerts was out of date in the example I was looking at.
            // TODO [c] create an class to store popups settings for this application -SchedulingApplicationPrompt
            //showIncorrectPasswordPopup();
            SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();
            popup.loginFailedPopup();

            CChoulesDevTools.println("SchedulingApplicationPrompt Now!");
        }

        if (loginValidated){ CChoulesDevTools.println("User input Validated, Logging in.");
            main.Main.loadMainWindowAndSetUser(Main.currentUser);
        }

//        //TODO [c] create home menu fxml
//        //TODO [c] create load home menu on validation logic //Committing
//        if (loginValidated) {
//
//
//            //Home.loadHomeFXML(stage, loginButton, getClass());
//
//            //TODO [Extra] was attempting to make a a clean method for loading scenes shown below, did not work properly but may revit the idea.
//            //Home.loadHomeFXML(loginButton, getClass());
//
//            CChoulesDevTools.println("Loading mainWindow.fxml");
//
//            //Note: this is incorrect I keep doing this bellow:
//            //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/controller/HomeMenu.fxml")));
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/mainWindow.fxml"));
//
//            Parent root = loader.load();
//
//            //TODO [Extra] Complete dark mode pass through from this scene to home
////            controller.trash.subViewController.Home mp = loader.getController();
////        //passing the css settings
////        mp.passCss(cssPath, darkModeOn);
//
//            stage = (Stage) loginButton.getScene().getWindow();
//            Scene scene = new Scene(root);
//
//            CChoulesDevTools.applyDevStyleToScene(scene);
//
//            stage.setScene(scene);
//            stage.show();
//        }

        //showIncorrectPasswordPopup();

    }

    public void loginClick(ActionEvent actionEvent) throws IOException, InterruptedException {
        System.out.println("Executing loginClick method: ");
        loginMethod();
    }

    public void loginEnter(KeyEvent keyEvent) throws IOException, InterruptedException {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            CChoulesDevTools.println("Enter key pressed. Preforming Login Action.");

            loginMethod();

        }
    }

    public void handleDarkButtonClick(ActionEvent actionEvent) throws IOException {
        CChoulesDevTools.println("Executing handleDarkButtonClick");

        //sets dark-mode object value in main to opposite of current value
        Main.currentDarkMode.setDarkModeOn(!Main.currentDarkMode.isDarkModeOn());

        darkModeButton.setText(Main.currentDarkMode.getDarkModeStateAsString());

        Main.loadLoginWithKeeper(usernameInput.getText(), passwordInput.getText());

    }

    public void changeLangComboSelect(ActionEvent actionEvent) {
        setCurrentLangAndReload(languageComboBox);
    }

    public void exitClick(ActionEvent actionEvent) {
        CChoulesDevTools.println("Executing exitClick");
        Platform.exit();
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        resourceBundle = Main.curMessBun;

        loadLocationField();
        setLoginLang(resourceBundle, url);
        darkModeButton.setText(Main.currentDarkMode.getDarkModeStateAsString());

        usernameInput.setText(Main.loginKeeper.getUserName());
        passwordInput.setText(Main.loginKeeper.getUserPassword());
        if (CChoulesDevTools.toolsState()){
            usernameInput.setText("test");
            passwordInput.setText("test");
        }
        loadLanguagesComboBox();
        Main.loginKeeper = new User(0, "","");
    }

    public void loadLocationField() {

        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);

        ZoneId zone = ZoneId.systemDefault();

    //TODO [] clean
        if (Locale.getDefault().getDisplayCountry().equals("United States")) {
            //loginLocationField.setText(Locale.getDefault().getDisplayCountry() + " of " + zone.getId());
            loginLocationField.setText(zone.getId());
        } else {
            loginLocationField.setText(zone.getId());
        }
        timeZoneDisplay.setText(zone.getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }

    public void loadLanguagesComboBox() {

        //Reference//Main.curMessBun = ResourceBundle.getBundle("MessagesBundle", Main.frLocale);

        languageComboBox.setValue(Main.defaultMessages.getLocale().getDisplayLanguage());

        ResourceBundle defaultMessages = Main.curMessBun;

        //Clear existing items in the combo box//Was a bug on reload
        languageComboBox.getItems().clear();

        //Using the languages set in Main add the values to the comboBox
        for ( String locale : Main.supportedMessageBundles) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(defaultMessages.getBaseBundleName(), Locale.forLanguageTag(locale));
                String displayName = bundle.getLocale().getDisplayLanguage();
                languageComboBox.getItems().add(displayName);
            } catch (Exception e) {
                //Ignore
            }
        }

        //Set the default value to the current display language
        languageComboBox.setValue(defaultMessages.getLocale().getDisplayLanguage());

    }
    public void setCurrentLangAndReload(ComboBox<String> langSelector) {
        String languageName = langSelector.getValue();

        for ( String lang : Main.supportedMessageBundles) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(Main.defaultMessages.getBaseBundleName(), Locale.forLanguageTag(lang));
                String displayName = bundle.getLocale().getDisplayLanguage();
                if (displayName.equals(languageName)){
                    Locale locale = new Locale(lang);
                    CChoulesDevTools.println("Reloading With Lang: " + locale.getDisplayLanguage());
                    Main.curMessBun = ResourceBundle.getBundle("MessagesBundle", locale);
                    Main.loadLoginWithKeeper(usernameInput.getText(), passwordInput.getText());
                    return;
                }
                languageComboBox.getItems().add(displayName);
            } catch (Exception e) {
                //Ignore exception
            }

        }
    }

    public void setLoginLang(ResourceBundle resourceBundle, URL url) {

        //defaultMessages = ResourceBundle.getBundle("MessagesBundle");
        //Make Note: future projects : should have ui type before name for readability
        //TODO [] Incomplete message bundles marked with : resourceBundle.getString("InCom")

        resourceBundle = Main.curMessBun;

        loginTitle.setText(resourceBundle.getString("Login") + ":");

        usernameLabel.setText(resourceBundle.getString("Username") + ":");
        passwordLabel.setText(resourceBundle.getString("Password") + ":");
        locationLabel.setText(resourceBundle.getString("Location") + ":");
        timeZoneLabel.setText(resourceBundle.getString("TimeZone") + ":");

        darkModeLabel.setText(resourceBundle.getString("DarkMode") + ":");
        languageLabel.setText(resourceBundle.getString("Language") + ":");

        loginButton.setText(resourceBundle.getString("Login"));
        exitButton.setText(resourceBundle.getString("Exit"));
        exitButton.setText(resourceBundle.getString("Exit"));

        devAnnouncementTxt.setText(resourceBundle.getString("LoginAnnouncementBody"));
        devAnnouncementTitle.setText(resourceBundle.getString("LoginAnnouncementTitle"));

    }


}

