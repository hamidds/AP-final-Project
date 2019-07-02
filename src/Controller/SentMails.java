package Controller;

import Model.Main;
import Model.Messages.Conversation;
import Model.Messages.Mail;
import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SentMails implements Initializable {

    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User loggedInUser;
    private List<Mail> mails = new ArrayList<>();

    @FXML
    private ListView<Mail> sentMails;
    @FXML
    private TextField searchText;
    @FXML
    private ImageView mainPanelAvatar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInUser = LogedInUser.getLoggedInUser();
        mails = loggedInUser.getSent();
        mainPanelAvatar.setClip(new Circle(60, 60, 60));
        mainPanelAvatar.setImage(new Image(loggedInUser.getProfileImage().toURI().toString()));
        sentMails.setCellFactory(inbox1 -> new UserListItemMails());
        sentMails.setItems(FXCollections.observableArrayList(mails));
    }

    private void connectToServer() throws IOException {
        client = new Socket(IPSetter.getIP(), 8888);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        System.out.println("connected");
    }

    private void disconnectFromServer() throws IOException {
        out.writeObject(new Message(MessageType.Disconnect, "", "", ""));
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Compose - Panel.fxml");
    }

    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void sentBox(ActionEvent actionEvent) {

    }

    public void refresh(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        connectToServer();
        Message refreshMessage = new Message(MessageType.Refresh, "", "", "");
        refreshMessage.setUser(loggedInUser);
        out.writeObject(refreshMessage);
        LogedInUser.setLoggedInUser((User) in.readObject());
        loggedInUser = LogedInUser.getLoggedInUser();
        mails = loggedInUser.getSent();
        sentMails.setCellFactory(inbox1 -> new UserListItemMails());
        sentMails.setItems(FXCollections.observableArrayList(mails));
        disconnectFromServer();
    }

    public void search(ActionEvent actionEvent) {
        if (searchText.getText().isEmpty())
            return;
        List<Mail> searched = new ArrayList<>();
        for (Mail mail : mails) {
            if (mail.getReceiverUser().equals(new User(null, null, searchText.getText(), null)) || mail.getSenderUser().equals(new User(null, null, searchText.getText(), null)))
                searched.add(mail);
        }
        sentMails.setItems(FXCollections.observableArrayList(searched));
    }

    public void open(ActionEvent actionEvent) {

    }

    public void logout(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Settings - Panel.fxml");
    }

}
