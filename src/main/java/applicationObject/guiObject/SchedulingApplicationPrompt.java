package applicationObject.guiObject;

import applicationTools.CChoulesDevTools;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
//Done

/**
 * A class that provides various methods for displaying custom prompts using JavaFX popups & prompts
 * This must be instantiated as a new SchedulingApplicationPrompt
 */
public class SchedulingApplicationPrompt {
    // create a alert
    Alert prompt = new Alert(Alert.AlertType.NONE);

    /**
     * Displays a login failed popup.
     * For when the user fails login
     */
    public void loginFailedPopup(){
        prompt.setAlertType(Alert.AlertType.WARNING);
        prompt.setHeaderText(Main.curMessBun.getString("LoginFailed"));
        prompt.setContentText(Main.curMessBun.getString("IncorrectPasswordPrompt"));
        prompt.setTitle(Main.curMessBun.getString("LoginFailed"));
        CChoulesDevTools.println(prompt.getTitle().toString());

        prompt.show();
    }

    /**
     * Displays a warning popup for appointments illegally scheduled
     *
     * @param content lists illegal appointments
     */
    public void inappropriateAppointmentTimesPopup(String content){
        prompt.setAlertType(Alert.AlertType.WARNING);
        prompt.setHeaderText("Inappropriate appointment time.");
        prompt.setContentText(content);
        prompt.setTitle("Inappropriate appointment time");
        CChoulesDevTools.println(prompt.getTitle());

        prompt.show();
    }

    /**
     * Displays a warning popup for upcoming appointments.
     *
     * @param content lists appointments
     */
    public void upcomingApPopup(String content){
        prompt.setAlertType(Alert.AlertType.WARNING);
        prompt.setHeaderText("Upcoming Appointments.");
        prompt.setContentText(content);
        prompt.setTitle("Upcoming Appointments.");
        CChoulesDevTools.println(prompt.getTitle());

        prompt.show();
    }

    /**
     * Displays a confirmation popup for deleting items with the given content.
     * @param content Additional content for the confirmation popup.
     * @return True if the user confirms the action; false otherwise.
     */
    public boolean deleteConformationPrompt(String content){

        prompt.setAlertType(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("Delete Conformation");
        prompt.setContentText(content);
        prompt.setTitle("Delete Conformation");
        CChoulesDevTools.println(prompt.getTitle());

        prompt.showAndWait();

        if (prompt.getResult() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Displays a login failed popup with the ability to change the style using CSS INCOMPLETE.
     *
     * @param cssPath Path to the CSS file for styling the alert.
     * TODO [Extra] create a new login prompt with the ability to change style with the chosen settings.
     */
    public void loginFailedPopup2(String cssPath) {

        prompt.getDialogPane().getStylesheets().add(cssPath);

        StageStyle stageStyle = StageStyle.UNDECORATED;
        Stage alertStage = (Stage) prompt.getDialogPane().getScene().getWindow();
        alertStage.initStyle(stageStyle);

        prompt.setHeaderText(Main.curMessBun.getString("LoginFailed"));
        prompt.setContentText(Main.curMessBun.getString("IncorrectPasswordPrompt"));
        prompt.setTitle(Main.curMessBun.getString("LoginFailed"));

        prompt.show();
        //Note It may be wise to
    }
}
