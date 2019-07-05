package Model.Connection;

import Controller.LoggedInUser;
import Model.Messages.Mail;
import Model.Messages.Message;
import Model.User;

import java.io.IOException;

public class ClientMessageHandler {
    public static String handle(Message message, User user) throws IOException {
        String respond = "";
        switch (message.getMessageType()) {
            case ChangePass:
            case ChangePro:
            case Sent:
                LoggedInUser.setLoggedInUser(message.getUser());
                break;
            case Connect:
                respond = message.getSender() + " connected";
                break;
            case Disconnect:
                respond = message.getSender() + " disconnected";
                break;
            case Text:
                user.addConversation(message.getMail());
                respond = message.getMail().getSender() + ":" + message.getMail().getText();
                break;
            case Error:
                respond = message.getReceiver() + " doesn't exist";
//                Mail Error = new Mail()
//                LoggedInUser.getLoggedInUser().addConversation();
                break;
        }
        return respond;
    }
}
