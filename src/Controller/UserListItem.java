package Controller;

import Model.Messages.Conversation;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class UserListItem extends ListCell<Conversation> {

    @Override
    protected void updateItem(Conversation conversation, boolean b) {
        super.updateItem(conversation, b);
        if (conversation != null){
            try {
                setGraphic(new UserListItemController(conversation).init());
            } catch (IOException e) {


            }
        }
    }
}
