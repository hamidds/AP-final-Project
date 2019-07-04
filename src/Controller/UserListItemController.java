package Controller;

import Model.Messages.Conversation;
import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;

public class UserListItemController {
    private Conversation conversation;
    @FXML
    private AnchorPane root;
    @FXML
    private Pane icons;
    @FXML
    private ImageView profile;
    @FXML
    private Label name, message, dateTime;
    @FXML
    private FontIcon bookMark, unBookMark, flag, unflag;

    public UserListItemController(Conversation conversation) throws IOException {
        this.conversation = conversation;
        new PageLoader().Load("../View/UserListItem.fxml", this);
    }

    public AnchorPane init() {
        if (conversation.isInTrash() || isBlocked(conversation.getSender()))
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
        message.setText(conversation.getLastMail().getText());
        dateTime.setText(conversation.getLastMail().getTimeString());
        return root;
    }

    public void remove() {
        conversation.setInTrash(true);
        Message CONVERSATION_DELETE = new Message(MessageType.ConversationDelete, conversation.getSubject(), "", "");
        CONVERSATION_DELETE.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(CONVERSATION_DELETE);
    }

    public void bookmark() {
        bookMark.setVisible(false);
        unBookMark.setVisible(true);
        conversation.setBookMarked(false);
        Message CONVERSATION_BOOKMARK = new Message(MessageType.ConversationBookmark, conversation.getSubject(), "", "");
        CONVERSATION_BOOKMARK.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(CONVERSATION_BOOKMARK);
    }


    public void read() {
        flag.setVisible(false);
        unflag.setVisible(true);
        conversation.setRead(false);
        Message CONVERSATION_READ = new Message(MessageType.ConversationRead, conversation.getSubject(), "", "");
        CONVERSATION_READ.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(CONVERSATION_READ);
    }


    public void unread() {
        unflag.setVisible(false);
        flag.setVisible(true);
        conversation.setRead(true);
        Message CONVERSATION_READ = new Message(MessageType.ConversationRead, conversation.getSubject(), "", "");
        CONVERSATION_READ.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(CONVERSATION_READ);
    }

    public void unBookMark() {
        unBookMark.setVisible(false);
        bookMark.setVisible(true);
        conversation.setBookMarked(true);
        Message CONVERSATION_BOOKMARK = new Message(MessageType.ConversationBookmark, conversation.getSubject(), "", "");
        CONVERSATION_BOOKMARK.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(CONVERSATION_BOOKMARK);
    }

    public void mouseOn(MouseEvent mouseEvent) {
        icons.setVisible(true);
        dateTime.setVisible(false);
    }

    public void mouseOff(MouseEvent mouseEvent) {
        icons.setVisible(false);
        dateTime.setVisible(true);
    }

    private boolean isBlocked(User user) {
        List<User> blockedUsers = LoggedInUser.getLoggedInUser().getBlockedUsers();
        if (blockedUsers.size() == 0)
            return false;
        for (User blockedUser : blockedUsers) {
            if (user.equals(blockedUser))
                return true;
        }
        return false;
    }
}
