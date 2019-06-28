package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class SettingsPanel {

    @FXML
    private FontIcon editIcon;
    @FXML
    private TextField fne,lne,phn;
    @FXML
    private DatePicker bde ;
    @FXML
    private Label fnl,lnl,phnl,bdl,profile ;

    public void editOff(MouseEvent mouseEvent) {
        editIcon.setVisible(false);
    }

    public void editOn(MouseEvent mouseEvent) {
        editIcon.setVisible(true);
    }

    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void compose(ActionEvent actionEvent) {

    }

    public void registerPanel(ActionEvent actionEvent) {

    }

    public void Exit(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {

    }

}
