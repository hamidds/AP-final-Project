package Controller;

import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BlockedUsers implements Initializable {
    private User user;

    @FXML
    private ListView<User> blockedUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = LoggedInUser.getLoggedInUser();
        List<User> bUsers = LoggedInUser.getLoggedInUser().getBlockedUsers();
        blockedUsers.setItems(FXCollections.observableArrayList(bUsers));
    }

    public void unSpam(ActionEvent actionEvent) {
        User unSpam = blockedUsers.getSelectionModel().getSelectedItem();
        Message unSpamMessage = new Message(MessageType.Unspam, user.getUsername(), unSpam.getUsername(), "");
        LoggedInUser.getLoggedInUserConnection().sendRequest(unSpamMessage);
    }

    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Compose - Panel.fxml");

    }

    public void logout(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");

    }

    public void sentBox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Sent Mails.fxml");
    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Settings - Panel.fxml");
    }
}
