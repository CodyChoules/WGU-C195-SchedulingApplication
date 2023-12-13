package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;

public class Test {

    @FXML public AnchorPane anchorPane;
    @FXML public TabPane tabPane;



    @FXML
    private void handleButtonClick(ActionEvent event) {
        handleActionToOpenTabWithFxml(event, getClass().getResource("/views/subViews/subTest.fxml"), tabPane);
    }

    public void handleActionToOpenTabWithFxml(ActionEvent event, URL fxmlUrl, TabPane tabPane){
        try {
            System.out.println("Button Clicked!");

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            AnchorPane subTestContent = loader.load();

            Tab tab = new Tab();
            tab.setText("This is a new tab");
            tab.setContent(subTestContent);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleActionToOpenTabWithFxml(ActionEvent event, URL fxmlUrl ){
        try {
            System.out.println("Button Clicked!");

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            AnchorPane subTestContent = loader.load();

            Tab tab = new Tab();
            tab.setText("This is a new tab");
            tab.setContent(subTestContent);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
