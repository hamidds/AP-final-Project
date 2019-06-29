package Controller;

import Model.Messages.Conversation;
import Model.Messages.Mail;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {
    private List<Conversation> conversations;

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
        User hamid = new User("hamid", "saffari", "h", "s");
        User Ali = new User("amin", "shafie", "a", "s");
        User reza = new User("reza", "saffari", "r", "s");
        Mail mail = new Mail(LocalDateTime.now(), "hamid", "reza", "hii", "hii");
        Mail mail1 = new Mail(LocalDateTime.now(), "hamid", "reza", "hii", "hiii");
        Mail mail2 = new Mail(LocalDateTime.now(), "ali", "reza", "hii", "hii");
        Mail mail3 = new Mail(LocalDateTime.now(), "hamid", "ali", "hii", "hii");
        Conversation c1 = new Conversation(mail, hamid, Ali);
        Conversation c2 = new Conversation(mail1, hamid, reza);
        Conversation c3 = new Conversation(mail2, Ali, reza);
//        conversations = new ArrayList<>(Arrays.asList(c1, c2, c3));
        User loggedInUser = LogedInUser.getLoggedInUser();
        conversations = loggedInUser.getConversations();
        System.out.println(conversations.size());
        mainPanelAvatar.setClip(new Circle(60,60,60));
        mainPanelAvatar.setImage(new Image(loggedInUser.getProfileImage().toURI().toString()));
        inbox1.setCellFactory(inbox1 -> new UserListItem());
        inbox1.setItems(FXCollections.observableArrayList(conversations));
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Compose - Panel.fxml");
    }

    public void inbox(ActionEvent actionEvent) {

    }

    public void registerPanel(ActionEvent actionEvent) {

    }

    public void Exit(ActionEvent actionEvent) {

    }

    public void logout(ActionEvent actionEvent) {

    }

    public void refresh(ActionEvent actionEvent) {

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

}