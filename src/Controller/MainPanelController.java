package Controller;

import Model.Messages.Conversation;
import Model.Messages.Mail;
import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {
    private List<Conversation> conversations;

    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User loggedInUser;

    @FXML
    private AnchorPane opend;
    @FXML
    private ListView<Conversation> inbox1;
    @FXML
    private Label text, name;
    @FXML
    private HBox h;
    @FXML
    private TextField searchText;
    @FXML
    private ImageView mainPanelAvatar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        User hamid = new User("hamid", "saffari", "h", "s");
//        User Ali = new User("amin", "shafie", "a", "s");
//        User reza = new User("reza", "saffari", "r", "s");
//        Mail mail = new Mail(LocalDateTime.now(), "hamid", "reza", "hii", "hii");
//        Mail mail1 = new Mail(LocalDateTime.now(), "hamid", "reza", "hii", "hiii");
//        Mail mail2 = new Mail(LocalDateTime.now(), "ali", "reza", "hii", "hii");
//        Mail mail3 = new Mail(LocalDateTime.now(), "hamid", "ali", "hii", "hii");
//        Conversation c1 = new Conversation(mail, hamid, Ali);
//        Conversation c2 = new Conversation(mail1, hamid, reza);
//        Conversation c3 = new Conversation(mail2, Ali, reza);
//        conversations = new ArrayList<>(Arrays.asList(c1, c2, c3));




        loggedInUser = LogedInUser.getLoggedInUser();
        conversations = loggedInUser.getConversations();
        mainPanelAvatar.setClip(new Circle(60, 60, 60));
        mainPanelAvatar.setImage(new Image(loggedInUser.getProfileImage().toURI().toString()));
        inbox1.setCellFactory(inbox1 -> new UserListItem());
        inbox1.setItems(FXCollections.observableArrayList(conversations));
    }

    private void connectToServer() throws IOException {
        client = new Socket("localhost", 8888);
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

    public void logout(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void refresh(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        connectToServer();
        Message refreshMessage = new Message(MessageType.Refresh, "", "", "");
        refreshMessage.setUser(loggedInUser);
        out.writeObject(refreshMessage);
        LogedInUser.setLoggedInUser((User) in.readObject());
        loggedInUser = LogedInUser.getLoggedInUser();
        conversations = loggedInUser.getConversations();
        inbox1.setCellFactory(inbox1 -> new UserListItem());
        inbox1.setItems(FXCollections.observableArrayList(conversations));
    }

    public void search(ActionEvent actionEvent) {
        if (searchText.getText().isEmpty())
            return;
        List<Conversation> searched = new ArrayList<>();
        for (Conversation conversation : conversations) {
            if (conversation.getReceiver().equals(new User(null, null, searchText.getText(), null)) || conversation.getSender().equals(new User(null, null, searchText.getText(), null)))
                searched.add(conversation);
        }
        inbox1.setItems(FXCollections.observableArrayList(searched));
    }

    public void open(ActionEvent actionEvent) {
        if (inbox1.getSelectionModel().isEmpty())
            return;
        Conversation conversation = inbox1.getSelectionModel().getSelectedItem();
        inbox1.setVisible(false);
        name.setText(conversation.getSender().toString() + "<" + conversation.getSender().getGmailAddress() + ">");
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), opend);
        transition.setToX(-600);
        transition.playFromStart();
    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Settings - Panel.fxml");
    }

    public void sentBox(ActionEvent actionEvent) {

    }
}