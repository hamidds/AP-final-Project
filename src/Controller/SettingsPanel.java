package Controller;

import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private ImageView mainPanelAvatar;
    @FXML
    public AnchorPane editablePane, unEditablePane;

    private User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userUpdate();
        mainPanelAvatar.setClip(new Circle(80, 72, 70));
        mainPanelAvatar.setImage(LoggedInUser.getLoggedInUserImage());
    }

    private void userUpdate() {
        loggedInUser = LoggedInUser.getLoggedInUser();
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
        LoggedInUser.getLoggedInUserConnection().sendRequest(new Message(MessageType.Disconnect, null, null, null));
        new PageLoader().Load("../View/SignIn - Panel.fxml");

    }

    public void sentBox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Sent Mails.fxml");
    }

    public void editPro(MouseEvent mouseEvent) {
        unEditablePane.setVisible(false);
        editablePane.setVisible(true);
    }

    public void Setting(ActionEvent actionEvent) {

    }

    public void checkBtn(MouseEvent mouseEvent) {
        Message changeUser = new Message(MessageType.ChangePro,
                fne.getText().isEmpty() ? loggedInUser.getFirstName() : fne.getText(),
                lne.getText().isEmpty() ? loggedInUser.getLastName() : lne.getText(),
                phnl.getText().isEmpty() ? loggedInUser.getPhoneNumber() : phnl.getText());
        changeUser.setUser(loggedInUser);
        LoggedInUser.getLoggedInUserConnection().sendRequest(changeUser);
        userUpdate();
        editablePane.setVisible(false);
        unEditablePane.setVisible(true);
    }

    public void passChange(MouseEvent mouseEvent) throws IOException {
        if (passwordChecker()) {
            if (pe.getText().equals(ce.getText())) {
                Message changePass = new Message(MessageType.ChangePass, pe.getText(), "", "");
                changePass.setUser(loggedInUser);
                LoggedInUser.getLoggedInUserConnection().sendRequest(changePass);
                userUpdate();
                Alert ChangedPass = new Alert(Alert.AlertType.INFORMATION, "Your Password has been changed!");
                ChangedPass.showAndWait();
            }
        } else {
            Alert invalidPass = new Alert(Alert.AlertType.INFORMATION, "Please use 8 or more characters with a mix of letters, numbers & symbols");
            invalidPass.showAndWait();
        }
    }

    private boolean passwordChecker() {
        String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(pe.getText());
        return matcher.matches();
    }

    public void showBlocked(MouseEvent mouseEvent) throws IOException {
        new PageLoader().Load("../View/BlockedUsers.fxml");
    }
}
