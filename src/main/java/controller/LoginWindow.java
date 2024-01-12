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


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * LoginWindow Controller for controlling the login window and authorizing user input.
 */
public class LoginWindow implements Initializable {


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
    /**
     * Handles the login process. Retrieves login information from input fields, sanitizes the input,
     * and performs the necessary actions for login. If either the username or password is empty, the login
     * attempt is abandoned, and an appropriate message is logged.
     * @throws IOException           If an I/O error occurs.
     * @throws InterruptedException  If the thread is interrupted.
     */
    private void loginMethod() throws IOException, InterruptedException  {
        // Get login info from input.
        String nameInput = CChoulesJTools.sanitizeInput(usernameInput.getText());
        String passInput = CChoulesJTools.sanitizeInput(passwordInput.getText());
        SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();

        // TODO [c] solve login on no input error.
        if (nameInput.isEmpty() || passInput.isEmpty()) {
            CChoulesDevTools.println("Input Values Found Empty, login attempt abandoned.");
            popup.loginFailedPopup();
            return;
        }

        JDBTools.openConnection();

        boolean loginValidated = UserDAO.validateUserLogin(nameInput, passInput, Main.currentUser);

        // Log login attempt
        logLoginAttempt(nameInput, loginValidated);

        if (!loginValidated) {
            CChoulesDevTools.println("Input on Password or Username not found. LoginWindow attempt abandoned");

            // showIncorrectPasswordPopup

            popup.loginFailedPopup();

            CChoulesDevTools.println("SchedulingApplicationPrompt Now!");
        }

        if (loginValidated) {
            CChoulesDevTools.println("User input Validated, Logging in.");
            main.Main.loadMainWindowAndSetUser(Main.currentUser);
        }
    }

    /**
     * Logs a login attempt, including the username, date-time information (both UTC and user's time zone),
     * and whether the login was successful or not. The log entry is appended to the "login_activity.txt" file.
     * @param username      The username associated with the login attempt.
     * @param loginSuccess  A boolean indicating whether the login attempt was successful (true) or not (false).
     */
    private void logLoginAttempt(String username, boolean loginSuccess) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true))) {
            //Get current date and time in UTC
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime nowZ = ZonedDateTime.now();
            ZonedDateTime utcDateTime = nowZ.withZoneSameInstant(ZoneId.of("UTC"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("(VV): yyyy-MM-dd HH:mm:ss");
            String formattedDateTimeUTC = utcDateTime.format(formatter);
            String formattedDateTime = nowZ.format(formatter);

            //Get user's time zone (you may adjust this based on your user's time zone handling)
            ZoneId userTimeZone = ZoneId.systemDefault();

            //Log the login attempt
            String logEntry = String.format("Username: %s, Date-Time %s %s, Attempt's Time Zone: %s, Success: %s%n",
                    username, formattedDateTimeUTC, formattedDateTime, userTimeZone, loginSuccess);
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    /**
     * Handles the action when the login button is clicked. Invokes the loginMethod to process the login attempt.
     * @param actionEvent          The action event triggered by clicking the login button.
     * @throws IOException          If an I/O exception occurs while processing the login.
     * @throws InterruptedException If the login process is interrupted.
     */
    public void loginClick(ActionEvent actionEvent) throws IOException, InterruptedException {
        CChoulesDevTools.println("Executing loginClick method: ");
        loginMethod();
    }

    /**
     * Handles the action when the Enter key is pressed. Initiates the login process by calling loginMethod.
     * @param keyEvent             The key event triggered by pressing the Enter key.
     * @throws IOException          If an I/O exception occurs while processing the login.
     * @throws InterruptedException If the login process is interrupted.
     */
    public void loginEnter(KeyEvent keyEvent) throws IOException, InterruptedException {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            CChoulesDevTools.println("Enter key pressed. Preforming LoginWindow Action.");

            loginMethod();

        }
    }

    /**
     * Handles the action when the dark mode button is clicked.
     * Toggles the dark mode setting, updates the button text,
     * and reloads the login scene.
     * @param actionEvent The action event triggered by clicking the dark mode button.
     * @throws IOException If an I/O exception occurs while reloading the login scene.
     */
    public void handleDarkButtonClick(ActionEvent actionEvent) throws IOException {
        CChoulesDevTools.println("Executing handleDarkButtonClick");

        //sets dark-mode object value in main to opposite of current value
        Main.currentDarkMode.setDarkModeOn(!Main.currentDarkMode.isDarkModeOn());

        darkModeButton.setText(Main.currentDarkMode.getDarkModeStateAsString());

        Main.loadLoginWithKeeper(usernameInput.getText(), passwordInput.getText());

    }


    /**
     * Handles the action when a language is selected from the language combo box.
     * Updates the current language setting & reloads the login scene.
     * @param actionEvent The action event triggered by selecting a language from the combo box.
     */
    public void changeLangComboSelect(ActionEvent actionEvent) {
        setCurrentLangAndReload(languageComboBox);
    }

    /**
     * Handles the action when the exit button is clicked. Exits the application.
     * @param actionEvent The action event triggered by clicking the exit button.
     */
    public void exitClick(ActionEvent actionEvent) {
        CChoulesDevTools.println("Executing exitClick");
        Platform.exit();
        System.exit(0);
    }


    /**
     * Initializes the login scene with default values.
     * Sets the resource bundle, loads the location field, sets the login
     * language, updates the dark mode button text,
     * & pre-fills the username and password inputs if in developer mode.
     * Also resets the login keeper.
     * @param url The URL to initialize the controller. Unused in this context.
     * @param resourceBundle The resource bundle containing localized strings.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        resourceBundle = Main.curMessBun;

        loadLocationField();
        setLoginLang(resourceBundle, url);
        darkModeButton.setText(Main.currentDarkMode.getDarkModeStateAsString());

        usernameInput.setText(Main.loginKeeper.getUserName());
        passwordInput.setText(Main.loginKeeper.getUserPassword());

        //To make testing faster
        if (CChoulesDevTools.toolsState()){
            usernameInput.setText("test");
            passwordInput.setText("test");
        }
        loadLanguagesComboBox();
        Main.loginKeeper = new User(0, "","");
    }

    /**
     * Loads the location field with information about the user's current locale and time zone.
     */
    public void loadLocationField() {

        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);

        ZoneId zone = ZoneId.systemDefault();

        loginLocationField.setText(zone.getId());
        timeZoneDisplay.setText(zone.getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }

    /**
     * Loads available languages into the language combo box.
     */
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
    /**
     * Sets the current language based on the selected value in the
     * language selector combo box and reloads the login scene
     * with the updated language.
     * @param langSelector The combo box containing language options.
     */
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

    /**
     * Sets the login scene language based on the provided in the current messages bundle.
     * t updates labels, buttons, and other UI elements
     * with the corresponding strings.
     */
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

