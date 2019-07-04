package Controller;

import Model.Messages.Mail;
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

public class UserListItemMailsController {
    private Mail mail;
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

    public UserListItemMailsController(Mail mail) throws IOException {
        this.mail = mail;
        new PageLoader().Load("../View/UserListItem.fxml", this);
    }

    public AnchorPane init() {
        if (mail.isInTrash() || isBlocked(mail.getSenderUser()))
            return null;
        if (mail.isBookMarked()) {
            bookMark.setVisible(true);
            unBookMark.setVisible(false);
        }
        if (mail.isRead()) {
            flag.setVisible(true);
            unflag.setVisible(false);
        }
        name.setText(mail.getSender());
        message.setText(mail.getText());
        dateTime.setText(mail.getTimeString());
        return root;
    }

    public void remove() {
        mail.setInTrash(true);
    }

    public void bookmark() {
        bookMark.setVisible(false);
        unBookMark.setVisible(true);
        mail.setBookMarked(false);
        Message MAIL_BOOKMARK = new Message(mail, MessageType.MailBookmark);
        MAIL_BOOKMARK.setReceiver(mail.getSubject());
        MAIL_BOOKMARK.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(MAIL_BOOKMARK);
    }


    public void read() {
        flag.setVisible(false);
        unflag.setVisible(true);
        mail.setRead(false);
    }


    public void unread() {
        unflag.setVisible(false);
        flag.setVisible(true);
        mail.setRead(true);
    }

    public void unBookMark() {
        unBookMark.setVisible(false);
        bookMark.setVisible(true);
        mail.setBookMarked(true);
        Message MAIL_BOOKMARK = new Message(mail, MessageType.MailBookmark);
        MAIL_BOOKMARK.setReceiver(mail.getSubject());
        MAIL_BOOKMARK.setUser(LoggedInUser.getLoggedInUser());
        LoggedInUser.getLoggedInUserConnection().sendRequest(MAIL_BOOKMARK);
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
