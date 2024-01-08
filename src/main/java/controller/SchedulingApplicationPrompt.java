package controller;

import applicationTools.CChoulesDevTools;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

public class SchedulingApplicationPrompt {
    // create a alert
    Alert prompt = new Alert(Alert.AlertType.NONE);

    public void loginFailedPopup(){
        prompt.setAlertType(Alert.AlertType.WARNING);
        prompt.setHeaderText(Main.curMessBun.getString("LoginFailed"));
        prompt.setContentText(Main.curMessBun.getString("IncorrectPasswordPrompt"));
        prompt.setTitle(Main.curMessBun.getString("LoginFailed"));
        CChoulesDevTools.println(prompt.getTitle().toString());

        prompt.show();
    }

    // TODO [Extra] create an new login prompt with the ability to change style with the chosen settings.
    public void loginFailedPopup2(String cssPath) {
        //Setting the alert style using CSS
        prompt.getDialogPane().getStylesheets().add(cssPath);

        //We can customize the appearance of the alert further if needed
        //For example, set the stage style to UNDECORATED
        StageStyle stageStyle = StageStyle.UNDECORATED;
        Stage alertStage = (Stage) prompt.getDialogPane().getScene().getWindow();
        alertStage.initStyle(stageStyle);


        prompt.setHeaderText(Main.curMessBun.getString("LoginFailed"));
        prompt.setContentText(Main.curMessBun.getString("IncorrectPasswordPrompt"));
        prompt.setTitle(Main.curMessBun.getString("LoginFailed"));

        //Show the alert
        prompt.show();
    }
}
