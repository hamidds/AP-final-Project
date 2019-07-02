package Controller;

import Model.Messages.Conversation;
import Model.Messages.Mail;
import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MailController implements Initializable {

    @FXML
    private ImageView profileImage;
    @FXML
    private Label text, name, mailDate;
    @FXML
    private TextArea reText;
    @FXML
    private TextField foText;
    @FXML
    private AnchorPane rePane, foPane;

    private Mail OpenedMail;
    private Conversation OpenedConversation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OpenedMail = currentMail.getMail();
        OpenedConversation = currentMail.getConversation();
        name.setText(OpenedMail.getSenderUser().toString() + "<" + OpenedMail.getSenderUser().getGmailAddress() + ">");
        text.setText(OpenedMail.getText());
        mailDate.setText(OpenedMail.getTimeString());
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Compose - Panel.fxml");
    }

    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void sentBox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Sent Mails.fxml");
    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Settings - Panel.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void spam(ActionEvent actionEvent) {

    }

    public void delete(ActionEvent actionEvent) {
        OpenedMail.setInTrash(true);
        Message MAIL_DELETE = new Message(OpenedMail, MessageType.MailDelete);
        MAIL_DELETE.setReceiver(OpenedConversation.getSubject());
        MAIL_DELETE.setUser(LogedInUser.getLoggedInUser());
        LogedInUser.getLoggedInUserConnection().sendRequest(MAIL_DELETE);
    }

    public void bookmark(ActionEvent actionEvent) {
        OpenedMail.setBookMarked(!OpenedMail.isBookMarked());
        Message MAIL_BOOKMARK = new Message(OpenedMail, MessageType.MailBookmark);
        MAIL_BOOKMARK.setReceiver(OpenedConversation.getSubject());
        MAIL_BOOKMARK.setUser(LogedInUser.getLoggedInUser());
        LogedInUser.getLoggedInUserConnection().sendRequest(MAIL_BOOKMARK);
    }

    public void read(ActionEvent actionEvent) {
        OpenedMail.setRead(!OpenedMail.isRead());
        Message MAIL_READ = new Message(OpenedMail, MessageType.MailBookmark);
        MAIL_READ.setReceiver(OpenedConversation.getSubject());
        MAIL_READ.setUser(LogedInUser.getLoggedInUser());
        LogedInUser.getLoggedInUserConnection().sendRequest(MAIL_READ);
    }

    public void reply(ActionEvent actionEvent) {
        rePane.setVisible(!rePane.isVisible());
    }

    public void forward(ActionEvent actionEvent) {
        foPane.setVisible(!foPane.isVisible());
    }

    public void replySend(ActionEvent actionEvent) {
        User loggedInUser = LogedInUser.getLoggedInUser();
        Mail newMail = new Mail(LocalDateTime.now(), loggedInUser.getUsername(), OpenedMail.getReceiver(), OpenedMail.getSubject(), reText.getText());
        newMail.setSenderUser(loggedInUser);
        Message newMessage = new Message(newMail, MessageType.Reply);
        LogedInUser.getLoggedInUserConnection().sendRequest(newMessage);
    }

    public void forwardSend(ActionEvent actionEvent) {
        User loggedInUser = LogedInUser.getLoggedInUser();
        Mail newMail = new Mail(LocalDateTime.now(), loggedInUser.getUsername(), foText.getText(), OpenedMail.getSubject(), OpenedMail.getText());
        newMail.setSenderUser(loggedInUser);
        Message newMessage = new Message(newMail, MessageType.Forward);
        LogedInUser.getLoggedInUserConnection().sendRequest(newMessage);
    }
}
