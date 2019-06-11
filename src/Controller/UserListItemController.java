package Controller;

import Model.Messages.Conversation;
import Model.PageLoader;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class UserListItemController {
    private Conversation conversation;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView profile;
    @FXML
    private Label name, message;
    @FXML
    private FontIcon bookMark, unBookMark, flag, unflag;

    public UserListItemController(Conversation conversation) throws IOException {
        this.conversation = conversation;
        new PageLoader().Load("../View/UserListItem.fxml", this);
    }

    public AnchorPane init() {
        if (conversation.isInTrash())
            return null;
        if (conversation.isBookMarked()) {
            bookMark.setVisible(true);
            unBookMark.setVisible(false);
        }
        if (conversation.isRead()) {
            flag.setVisible(true);
            unflag.setVisible(false);
        }
        name.setText(conversation.getSender().toString());
        message.setText(conversation.getLastMail().getMessageText());
        return root;
    }

    public void remove() {
        conversation.setInTrash(true);
    }

    public void bookmark() {
        bookMark.setVisible(false);
        unBookMark.setVisible(true);
        conversation.setBookMarked(false);
    }


    public void read() {
        flag.setVisible(false);
        unflag.setVisible(true);
        conversation.setRead(false);
    }


    public void unread() {
        unflag.setVisible(false);
        flag.setVisible(true);
        conversation.setRead(true);
    }

    public void unBookMark() {
        unBookMark.setVisible(false);
        bookMark.setVisible(true);
        conversation.setBookMarked(true);
    }

}
