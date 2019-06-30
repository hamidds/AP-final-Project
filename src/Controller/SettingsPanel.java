package Controller;

import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPanel implements Initializable {

    @FXML
    private PasswordField pe, ce;
    @FXML
    private FontIcon editIcon, check;
    @FXML
    private TextField fne, lne, phn;
    @FXML
    private DatePicker bde;
    @FXML
    private Label fnl, lnl, phnl, bdl, profile;
    @FXML
    public AnchorPane editablePane, unEditablePane;

    private User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        userUpdate();
    }

    private void userUpdate() {
        loggedInUser = LogedInUser.getLoggedInUser();
        fillData();
    }


    private void fillData() {
        fnl.setText(loggedInUser.getFirstName());
        lnl.setText(loggedInUser.getLastName());
        phnl.setText(loggedInUser.getPhoneNumber());
        bdl.setText(loggedInUser.getBirthDate());
    }

    public void editOff(MouseEvent mouseEvent) {
        editIcon.setVisible(false);
    }

    public void editOn(MouseEvent mouseEvent) {
        editIcon.setVisible(true);
    }

    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Compose - Panel.fxml");

    }

    public void logout(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");

    }

    public void editPro(MouseEvent mouseEvent) {
        unEditablePane.setVisible(false);
        editablePane.setVisible(true);
    }

    public void sentBox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Sent Mails.fxml");
    }

    public void Setting(ActionEvent actionEvent) {

    }

    public void checkBtn(MouseEvent mouseEvent) {
        Message changeUser = new Message(MessageType.ChangePro,
                fne.getText().isEmpty() ? loggedInUser.getFirstName() : fne.getText(),
                lne.getText().isEmpty() ? loggedInUser.getLastName() : lne.getText(),
                phn.getText().isEmpty() ? loggedInUser.getPhoneNumber() : fne.getText());
        changeUser.setUser(loggedInUser);
        LogedInUser.getLoggedInUserConnection().sendRequest(changeUser);
        editablePane.setVisible(false);
        unEditablePane.setVisible(true);
        userUpdate();
    }

    public void passChange(MouseEvent mouseEvent) throws IOException {
        if (pe.getText().equals(ce.getText())) {
            Message changePass = new Message(MessageType.ChangePass, pe.getText(), "", "");
            changePass.setUser(loggedInUser);
            LogedInUser.getLoggedInUserConnection().sendRequest(changePass);
            userUpdate();
        }
    }

}
