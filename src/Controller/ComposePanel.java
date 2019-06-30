package Controller;

import Model.Messages.Mail;
import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class ComposePanel implements Initializable {
    @FXML
    private TextField recepient, subject, mailText;
    @FXML
    private Button send;


    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Compose - Panel.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Register - Panel.fxml");

    }

    public void send(ActionEvent actionEvent) {
        User loggedInUser = LogedInUser.getLoggedInUser();
        Mail newMail = new Mail(LocalDateTime.now(), loggedInUser.getUsername(), recepient.getText(), subject.getText(), mailText.getText());
        newMail.setSenderUser(loggedInUser);
        Message newMessage = new Message(newMail, MessageType.Text);
        LogedInUser.getLoggedInUserConnection().sendRequest(newMessage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void sentBox(ActionEvent actionEvent) {

    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Settings - Panel.fxml");
    }
}
