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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MailController implements Initializable {

    @FXML
    private ImageView profileImage, mainPanelAvatar;
    @FXML
    private Label name, mailDate;
    @FXML
    private TextField reText, text;
    @FXML
    private TextField foText;
    @FXML
    private FontIcon saveAttached;
    @FXML
    private AnchorPane rePane, foPane, more;

    private Mail OpenedMail;
    private Conversation OpenedConversation;
    private byte[] bytes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OpenedMail = currentMail.getMail();
        OpenedConversation = currentMail.getConversation();
        name.setText(OpenedMail.getSenderUser().toString() + "<" + OpenedMail.getSenderUser().getGmailAddress() + ">");
        text.setText(OpenedMail.getText());
        mailDate.setText(OpenedMail.getTimeString());
        bytes = currentMail.getMail().getAttached();
        if (bytes == null)
            saveAttached.setVisible(false);
        mainPanelAvatar.setClip(new Circle(60, 60, 60));
        mainPanelAvatar.setImage(LoggedInUser.getLoggedInUserImage());
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
        Message block = new Message(MessageType.Spam, LoggedInUser.getLoggedInUser().getUsername(), OpenedMail.getReceiver(), "");
        LoggedInUser.getLoggedInUserConnection().sendRequest(block);
    }

    public void delete(ActionEvent actionEvent) {
        OpenedMail.setInTrash(true);
        Message MAIL_DELETE = new Message(OpenedMail, MessageType.MailDelete);
        MAIL_DELETE.setReceiver(OpenedConversation.getSubject());
        MAIL_DELETE.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(MAIL_DELETE);
    }

    public void bookmark(ActionEvent actionEvent) {
        OpenedMail.setBookMarked(!OpenedMail.isBookMarked());
        Message MAIL_BOOKMARK = new Message(OpenedMail, MessageType.MailBookmark);
        MAIL_BOOKMARK.setReceiver(OpenedConversation.getSubject());
        MAIL_BOOKMARK.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(MAIL_BOOKMARK);
    }

    public void read(ActionEvent actionEvent) {
        OpenedMail.setRead(!OpenedMail.isRead());
        Message MAIL_READ = new Message(OpenedMail, MessageType.MailRead);
        MAIL_READ.setReceiver(OpenedConversation.getSubject());
        MAIL_READ.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(MAIL_READ);
    }

    public void reply(ActionEvent actionEvent) {
        rePane.setVisible(!rePane.isVisible());
    }

    public void forward(ActionEvent actionEvent) {
        foPane.setVisible(!foPane.isVisible());
    }

    public void replySend(ActionEvent actionEvent) {
        User loggedInUser = LoggedInUser.getLoggedInUser();
        Mail newMail = new Mail(LocalDateTime.now(), loggedInUser.getUsername(), OpenedMail.getReceiver(), OpenedMail.getSubject(), reText.getText());
        newMail.setSenderUser(loggedInUser);
        Message newMessage = new Message(newMail, MessageType.Reply);
        LoggedInUser.getLoggedInUserConnection().sendRequest(newMessage);
    }

    public void forwardSend(ActionEvent actionEvent) {
        User loggedInUser = LoggedInUser.getLoggedInUser();
        Mail newMail = new Mail(LocalDateTime.now(), loggedInUser.getUsername(), foText.getText(), OpenedMail.getSubject(), OpenedMail.getText());
        newMail.setSenderUser(loggedInUser);
        Message newMessage = new Message(newMail, MessageType.Forward);
        LoggedInUser.getLoggedInUserConnection().sendRequest(newMessage);
    }

    public void SaveFile(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        //Show save file dialog
        FileChooser.ExtensionFilter a = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(a);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveToSystem(this.bytes, file);
        }
    }

    private void saveToSystem(byte[] bytes, File file) throws IOException {
        // Initialize a pointer
        // in file using OutputStream
        OutputStream os = new FileOutputStream(file);
        // Starts writing the bytes in it
        os.write(bytes);
        // Close the file
        os.close();

    }

    public void showMore(MouseEvent mouseEvent) {
        more.setVisible(!more.isVisible());
    }
}
