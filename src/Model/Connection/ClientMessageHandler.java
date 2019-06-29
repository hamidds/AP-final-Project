package Model.Connection;

import Controller.LogedInUser;
import Model.Messages.Message;
import Model.User;

public class ClientMessageHandler {
    public static String handle(Message message, User user) {
        String respond = "";
        switch (message.getMessageType()) {
            case Connect:
                respond = message.getSender() + " connected";
                break;
            case Disconnect:
                respond = message.getSender() + " disconnected";
                break;
            case Text:
                user.addConversation(message.getMail());
                System.out.println(user.getConversations());
//                LogedInUser.setLoggedInUser(user);
                respond = message.getMail().getSender() + ":" + message.getMail().getText();
                break;
            case Error:
                respond = message.getReceiver() + " doesn't exist";
                break;
        }
        return respond;
    }
}
