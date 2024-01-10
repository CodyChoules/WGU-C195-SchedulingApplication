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
//    private void loginMethod() throws IOException, InterruptedException  {
//
//        //Get login info from input.
//        String nameInput = CChoulesJTools.sanitizeInput( usernameInput.getText() );
//        String passInput = CChoulesJTools.sanitizeInput( passwordInput.getText() );
//
//        // TODO [c] solve login on no input error.
//        if (nameInput.isEmpty() || passInput.isEmpty()) {
//            CChoulesDevTools.println("Input Values Found Empty, login attempt abandoned.");
//            return;
//        }
//
//        JDBTools.openConnection();
//
//        boolean loginValidated = UserDAO.validateUserLogin(nameInput,passInput, Main.currentUser);
//
//        if (! loginValidated) {
//            CChoulesDevTools.println("Input on Password or Username not found. Login attempt abandoned");
//
//            //showIncorrectPasswordPopup
//            SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();
//            popup.loginFailedPopup();
//
//            CChoulesDevTools.println("SchedulingApplicationPrompt Now!");
//        }
//
//        if (loginValidated){ CChoulesDevTools.println("User input Validated, Logging in.");
//            main.Main.loadMainWindowAndSetUser(Main.currentUser);
//        }
//
//
//
//    }


    private void loginMethod() throws IOException, InterruptedException  {
        // Get login info from input.
        String nameInput = CChoulesJTools.sanitizeInput(usernameInput.getText());
        String passInput = CChoulesJTools.sanitizeInput(passwordInput.getText());

        // TODO [c] solve login on no input error.
        if (nameInput.isEmpty() || passInput.isEmpty()) {
            CChoulesDevTools.println("Input Values Found Empty, login attempt abandoned.");
            return;
        }

        JDBTools.openConnection();

        boolean loginValidated = UserDAO.validateUserLogin(nameInput, passInput, Main.currentUser);

        // Log login attempt
        logLoginAttempt(nameInput, loginValidated);

        if (!loginValidated) {
            CChoulesDevTools.println("Input on Password or Username not found. Login attempt abandoned");

            // showIncorrectPasswordPopup
            SchedulingApplicationPrompt popup = new SchedulingApplicationPrompt();
            popup.loginFailedPopup();

            CChoulesDevTools.println("SchedulingApplicationPrompt Now!");
        }

        if (loginValidated) {
            CChoulesDevTools.println("User input Validated, Logging in.");
            main.Main.loadMainWindowAndSetUser(Main.currentUser);
        }
    }

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
    //  \\End of Test//





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

