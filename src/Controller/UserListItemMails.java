package Controller;

import Model.Messages.Mail;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class UserListItemMails extends ListCell<Mail> {

    @Override
    protected void updateItem(Mail mail, boolean b) {
        super.updateItem(mail, b);
        if (mail != null){
            try {
                setGraphic(new UserListItemMailsController(mail).init());
            } catch (IOException e) {


            }
        }
    }
}
