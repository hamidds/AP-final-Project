package Controller;

import Model.Messages.Mail;
import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class ComposePanel implements Initializable {

    private final int MAX_FILE_SIZE = 1000000000;
    @FXML
    private TextField recepient, subject, mailText;
    @FXML
    private FontIcon attach;
    @FXML
    private Label sent;
    @FXML
    private Button send;

    private File attached;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void send(ActionEvent actionEvent) throws IOException {
        User loggedInUser = LoggedInUser.getLoggedInUser();
        Mail newMail = new Mail(LocalDateTime.now(), loggedInUser.getUsername(), recepient.getText(), subject.getText(), mailText.getText());
        newMail.setSenderUser(loggedInUser);
        if (attached != null) {
            if (attached.length() < MAX_FILE_SIZE) {
                newMail.setAttachedFile(attached);
                newMail.setAttached(fileTOByteArray(attached));
            } else {
                Alert limitedSize = new Alert(Alert.AlertType.INFORMATION, "Maximum file size reached!");
                limitedSize.showAndWait();
                attached = null;
            }
        }
        Message newMessage = new Message(newMail, MessageType.Text);
        LoggedInUser.getLoggedInUserConnection().sendRequest(newMessage);
        sent.setVisible(true);
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
        LoggedInUser.getLoggedInUserConnection().sendRequest(new Message(MessageType.Disconnect, null, null, null));
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Settings - Panel.fxml");
    }

    public void attachFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        attached = fileChooser.showOpenDialog(null);
    }

    /**
     * converts a file to an byte[]
     *
     * @param file the file which we want to convert it to byte[]
     * @return the converted file which is an byte[]
     * @throws IOException
     */
    private byte[] fileTOByteArray(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        return bytesArray;
    }


}
