package Controller;

import Model.Messages.Conversation;
import Model.Messages.Mail;
import Model.Messages.MessageType;
import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {
    @FXML
    private ListView<Conversation> inbox1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User hamid = new User("hamid", "saffari", "h", "s");
        User Ali = new User("ali", "saffari", "a", "s");
        User reza = new User("reza", "saffari", "r", "s");
        Mail mail = new Mail(MessageType.Text, "hamid", "ali", "hii");
        Mail mail1 = new Mail(MessageType.Text, "hamid", "reza", "hii");
        Mail mail2 = new Mail(MessageType.Text, "ali", "reza", "hii");
        Mail mail3 = new Mail(MessageType.Text, "hamid", "ali", "hii");
        Conversation c1 = new Conversation(mail, hamid, Ali);
        Conversation c2 = new Conversation(mail1, hamid, reza);
        Conversation c3 = new Conversation(mail2, Ali, reza);
        List<Conversation> conversations = new ArrayList<>(Arrays.asList(c1, c2, c3));

        inbox1.setItems(FXCollections.observableArrayList(conversations));
        inbox1.setCellFactory(inbox1 -> new UserListItem());
    }

    public void compose(ActionEvent actionEvent) {

    }

    public void inbox(ActionEvent actionEvent) {

    }

    public void registerPanel(ActionEvent actionEvent) {

    }

    public void Exit(ActionEvent actionEvent) {

    }

    public void logout(ActionEvent actionEvent) {

    }
}
