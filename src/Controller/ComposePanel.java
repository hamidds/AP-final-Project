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
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class ComposePanel implements Initializable {

    @FXML
    private TextField recepient, subject, mailText;
    @FXML
    private FontIcon attach;
    @FXML
    private Button send;

    private File attached;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void send(ActionEvent actionEvent) {
        User loggedInUser = LogedInUser.getLoggedInUser();
        Mail newMail = new Mail(LocalDateTime.now(), loggedInUser.getUsername(), recepient.getText(), subject.getText(), mailText.getText());
        newMail.setSenderUser(loggedInUser);
        if (attached != null)
            newMail.setAttachedFile(attached);
        Message newMessage = new Message(newMail, MessageType.Text);
        LogedInUser.getLoggedInUserConnection().sendRequest(newMessage);
    }

    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Compose - Panel.fxml");
    }


    public void sentBox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Sent Mails.fxml");

    }

    public void logout(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Settings - Panel.fxml");
    }

    public void attachFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        attached = fileChooser.showOpenDialog(null);
    }

    public void replyInit(String username) {
        recepient.setText(username);
        recepient.setEditable(false);
    }

}
